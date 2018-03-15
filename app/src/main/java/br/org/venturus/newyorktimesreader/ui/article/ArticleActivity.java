package br.org.venturus.newyorktimesreader.ui.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.org.venturus.newyorktimesreader.R;
import br.org.venturus.newyorktimesreader.business.validator.ArticleValidator;
import br.org.venturus.newyorktimesreader.entity.enm.ArticleSection;
import br.org.venturus.newyorktimesreader.entity.to.ArticleTo;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.OperationListener;
import br.org.venturus.newyorktimesreader.infra.factory.ManagerFactory;
import br.org.venturus.newyorktimesreader.infra.network.ApiError;
import br.org.venturus.newyorktimesreader.infra.utils.StringUtils;
import br.org.venturus.newyorktimesreader.manager.ArticleManager;
import br.org.venturus.newyorktimesreader.ui.EndlessScrollListener;
import br.org.venturus.newyorktimesreader.ui.OnDisplayFeedback;
import br.org.venturus.newyorktimesreader.ui.OnTextChanged;
import br.org.venturus.newyorktimesreader.ui.ViewUtils;
import br.org.venturus.newyorktimesreader.ui.article.ArticleAdapter.ArticleClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_edittext)
    EditText searchEditText;

    private ArticlesTo articles;

    private LinearLayoutManager linearLayoutManager;

    private int recyclerViewPage;
    private int searchPage;
    private int numberOfItemsToLoad;
    private boolean isLoading;

    private ArticleManager articleManager;

    private ArticleClickListener articleClickListener =
            (article, v) -> ViewUtils.launchChromeCustomTab(ArticleActivity.this, article.getUrl());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ButterKnife.bind(this);

        setup();
        initViews();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupSeachListener();
    }

    private void setup() {
        articleManager = ManagerFactory.createArticleManager();

        numberOfItemsToLoad = getResources().getInteger(R.integer.number_of_articles_to_load);
    }

    private void initViews() {
        ViewUtils.hideKeyboard(getWindow());
        setSupportActionBar(toolbar);

        linearLayoutManager = new LinearLayoutManager(ArticleActivity.this);

        recyclerView.setLayoutManager(linearLayoutManager);

        initArticles();
    }

    private void setupSeachListener() {
        searchEditText.addTextChangedListener(new OnTextChanged() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPage = 0;

                if (StringUtils.isEmpty(s)) return;

                articleManager.cancelOperations();
                articleManager.searchArticles(s, searchPage++, new PopulateArticlesListener() {
                    @Override
                    public void onSuccess(ArticlesTo articles) {
                        recyclerView.setAdapter(new ArticleAdapter(ArticleActivity.this, articleClickListener, articles, emptyView));
                        recyclerView.clearOnScrollListeners();
                        recyclerView.addOnScrollListener(createSearchScroll(s));
                    }
                });
            }
        });
    }

    private void initArticles() {
        String section = getString(R.string.default_most_viewed_section);
        articleManager.loadMostViewed(ArticleSection.getByValue(section), new PopulateArticlesListener() {
            @Override
            public void onSuccess(ArticlesTo articles) {
                ArticleActivity.this.articles = articles;

                ArticlesTo items = new ArticlesTo();
                items.addAll(articles.subList(recyclerViewPage, numberOfItemsToLoad));

                recyclerView.setAdapter(new ArticleAdapter(ArticleActivity.this, articleClickListener, items, emptyView));
                recyclerView.clearOnScrollListeners();
                recyclerView.addOnScrollListener(createMostViewedScroll());
            }
        });
    }

    private OnScrollListener createMostViewedScroll() {
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

                ArticlesTo items = getItemsToLoad(articles, start, end);
                refreshView(recyclerView,
                        new ArticleAdapter(ArticleActivity.this, articleClickListener, items, emptyView),
                        firstVisibleItemPosition);
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        };
    }

    private OnScrollListener createSearchScroll(CharSequence search) {
        return new EndlessScrollListener(numberOfItemsToLoad, linearLayoutManager) {
            @Override
            public void onScrolledToEnd(int firstVisibleItemPosition) {
                articleManager.searchArticles(search, searchPage++, new PopulateArticlesListener() {
                    @Override
                    public void onSuccess(ArticlesTo articles) {
                        ArticlesTo items = getItemsToLoad(articles);
                        refreshView(recyclerView,
                                new ArticleAdapter(ArticleActivity.this, articleClickListener, items, emptyView),
                                firstVisibleItemPosition);
                    }
                });
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        };
    }

    private ArticlesTo getItemsToLoad(ArticlesTo articles, int start, int end) {
        displayFeedback();

        ArticlesTo result = new ArticlesTo();
        ArticlesTo oldArticles = ((ArticleAdapter) recyclerView.getAdapter()).getArticles();
        List<ArticleTo> newArticles = articles.subList(start, end);

        result.addAll(oldArticles.getArticles());
        result.addAll(newArticles);

        return result;
    }

    private ArticlesTo getItemsToLoad(ArticlesTo articles) {
        displayFeedback();
        ArticlesTo result = new ArticlesTo();

        if (articles == null) {
            return  result;
        }

        ArticlesTo oldArticles = ((ArticleAdapter) recyclerView.getAdapter()).getArticles();

        result.addAll(oldArticles.getArticles());
        result.addAll(articles.getArticles());

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

    private abstract class PopulateArticlesListener extends OperationListener<ArticlesTo> {

        @Override
        public void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            isLoading = true;
        }

        @Override
        public abstract void onSuccess(ArticlesTo articles);

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
            isLoading = false;
        }
    }
}
