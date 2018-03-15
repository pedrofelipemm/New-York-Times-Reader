package br.org.venturus.newyorktimesreader.ui.article;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.org.venturus.newyorktimesreader.R;
import br.org.venturus.newyorktimesreader.business.ArticleBusiness.ArticleSection;
import br.org.venturus.newyorktimesreader.business.validator.ArticleValidator;
import br.org.venturus.newyorktimesreader.entity.to.ArticleTo;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.OperationListener;
import br.org.venturus.newyorktimesreader.infra.factory.ManagerFactory;
import br.org.venturus.newyorktimesreader.infra.network.ApiError;
import br.org.venturus.newyorktimesreader.infra.utils.DateUtils;
import br.org.venturus.newyorktimesreader.manager.ArticleManager;
import br.org.venturus.newyorktimesreader.ui.OnDisplayFeedback;
import br.org.venturus.newyorktimesreader.ui.EndlessScrollListener;
import br.org.venturus.newyorktimesreader.ui.article.ArticleAdapter.ArticleClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ArticleActivity extends AppCompatActivity {

    //TODO: polir UI
        // Exibir erro de maneira amigável
        // Utilizar Coordinator layout para esconder barra de busca
    //TODO: preference, choose most viewed section
    //TODO: ViewModel para não fazer chamada de rede quando houver mudanças de configurações.
    //TODO: mudar layout caso nao tenha imagem
    //TODO: Extrair styles comuns
    //TODO Test unitário / Android test

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArticlesTo articles;

    int numberOfItemsToLoad ;
    private int recyclerViewPage;

    private ArticleManager articleManager;

    private ArticleClickListener articleClickListener =
            (article, v) -> Toast.makeText(ArticleActivity.this,
                    DateUtils.format(article.getPublishDate()),
                    Toast.LENGTH_SHORT)
                    .show();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ButterKnife.bind(this);

        setup();
        initViews();
    }

    private void setup() {
        articleManager = ManagerFactory.createArticleManager();

        numberOfItemsToLoad = getResources().getInteger(R.integer.number_of_articles_to_load);
    }

    private void initViews() {
        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ArticleActivity.this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ArticleAdapter(ArticleActivity.this, articleClickListener, new ArticlesTo(), emptyView));

        String section = getString(R.string.default_most_viewed_section);
        articleManager.loadMostViewed(ArticleSection.getByValue(section), new OperationListener<ArticlesTo>() {

            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArticlesTo articles) {
                ArticleActivity.this.articles = articles;

                ArticlesTo items = new ArticlesTo();
                items.addAll(articles.subList(recyclerViewPage, numberOfItemsToLoad));

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new ArticleAdapter(ArticleActivity.this, articleClickListener, items, emptyView));
                recyclerView.addOnScrollListener(mostViewedScroll(linearLayoutManager));
            }

            @Override
            public void onValidation(List<String> codes) {
                Timber.e(codes.toString());
                for (String code : codes) {
                    switch (code) {
                        case ArticleValidator.SECTION_VAZIO:
                            Toast.makeText(ArticleActivity.this, getString(R.string.parametro_nao_informado, "section"),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(ArticleActivity.this, getString(R.string.erro_desconhecido),
                                    Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onError(ApiError error) {
                Timber.e(error.toString());
                Toast.makeText(ArticleActivity.this, getString(R.string.erro_desconhecido),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPostExecute() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private OnScrollListener mostViewedScroll(LinearLayoutManager linearLayoutManager) {
        return new EndlessScrollListener(numberOfItemsToLoad, linearLayoutManager) {
            @Override
            public void onScrolledToEnd(int firstVisibleItemPosition) {
                int start = ++recyclerViewPage * numberOfItemsToLoad;
                final boolean allItemsLoaded = start >= articles.size();

                if (allItemsLoaded) {
                    return;
                }

                int end = start + numberOfItemsToLoad;
                if (end > articles.size()) {
                    end = articles.size();
                }

                ArticlesTo items = getItemsToLoad(start, end);
                refreshView(recyclerView,
                        new ArticleAdapter(ArticleActivity.this, articleClickListener, items, emptyView),
                        firstVisibleItemPosition);
            }
        };
    }

    private ArticlesTo getItemsToLoad(int start, int end) {

        displayFeedback();

        ArticlesTo result = new ArticlesTo();
        ArticlesTo oldArticles = ((ArticleAdapter) recyclerView.getAdapter()).getArticles();
        List<ArticleTo> newArticles = articles.subList(start, end);

        result.addAll(oldArticles.getArticles());
        result.addAll(newArticles);

        return result;
    }

    private void displayFeedback() {
        new OnDisplayFeedback(new OperationListener() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute() {
                // Apenas para simular um delay pois a most_popular_api não possui paginação.
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Timber.e(e);
                }
                progressBar.setVisibility(View.GONE);
            }
        }).execute();
    }
}
