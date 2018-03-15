package br.org.venturus.newyorktimesreader.entity.to;

import java.util.ArrayList;
import java.util.List;

public class ArticlesTo {

    private List<ArticleTo> articles = new ArrayList<>();

    public void addArticle(ArticleTo articleTo) {
        articles.add(articleTo);
    }

    public boolean isEmpty() {
        return articles.isEmpty();
    }

    public ArticleTo get(int position) {
        return articles.get(position);
    }

    public int size() {
        return articles.size();
    }

    public void addAll(List<ArticleTo> articles) {
        this.articles.addAll(articles);
    }

    public List<ArticleTo> subList(int start, int end) {
        return articles.subList(start, end);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public List<ArticleTo> getArticles() {
        return new ArrayList<>(articles);
    }

    @Override
    public String toString() {
        return ArticlesTo.class.getSimpleName() + "{" +
                "articles=" + articles +
                '}';
    }
}
