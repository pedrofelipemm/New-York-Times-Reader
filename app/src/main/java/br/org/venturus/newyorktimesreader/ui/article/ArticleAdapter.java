package br.org.venturus.newyorktimesreader.ui.article;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import br.org.venturus.newyorktimesreader.R;
import br.org.venturus.newyorktimesreader.entity.to.ArticleTo;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.GlideApp;
import br.org.venturus.newyorktimesreader.infra.utils.DateUtils;
import br.org.venturus.newyorktimesreader.infra.utils.StringUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private static final String INVALID_IMAGE = "-1";

    private Context context;
    private LayoutInflater inflater;

    private ArticlesTo articles;

    private ArticleClickListener itemClickListener;

    interface ArticleClickListener {
        void onItemClick(ArticleTo article, View v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @BindView(R.id.article_title_textview)
        TextView titleTextView;

        @BindView(R.id.article_publish_date)
        TextView publishDateTextView;

        @BindView(R.id.article_snippet_textview)
        TextView snippetTextView;

        @BindView(R.id.article_picture_imageview)
        ImageView articlePictureImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            root = view;
        }
    }

    ArticleAdapter(Context context, ArticleClickListener itemClickListener, ArticlesTo articles, View emptyView) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);

        this.articles = articles;
        this.itemClickListener = itemClickListener;

        emptyView.setVisibility(articles == null || articles.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.article_cardview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleTo article = articles.get(position);

        String date = DateUtils.format(article.getPublishDate());

        holder.titleTextView.setText(article.getTitle());
        holder.publishDateTextView.setText(StringUtils.isEmpty(date) ? context.getString(R.string.data_desconhecida) : date);
        holder.snippetTextView.setText(article.getSnippet());

        GlideApp.with(context)
                .load(TextUtils.isEmpty(article.getPictureUrl()) ? INVALID_IMAGE : article.getPictureUrl())
                .apply(new RequestOptions().error(R.drawable.no_image))
                .into(holder.articlePictureImageView);

        holder.root.setOnClickListener(v -> itemClickListener.onItemClick(article, v));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public ArticlesTo getArticles() {
        return articles;
    }
}
