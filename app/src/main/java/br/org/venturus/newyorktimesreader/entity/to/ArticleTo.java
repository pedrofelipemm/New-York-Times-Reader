package br.org.venturus.newyorktimesreader.entity.to;

import java.util.Date;

public class ArticleTo {

    private String url;
    private String title;
    private Date publishDate;
    private String snippet;
    private String pictureUrl;

    public ArticleTo(String url, String title, Date publishDate, String snippet, String pictureUrl) {
        this.url = url;
        this.title = title;
        this.publishDate = publishDate;
        this.snippet = snippet;
        this.pictureUrl = pictureUrl;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getUrl() {
        return url;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getTitle() {
        return title;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public Date getPublishDate() {
        return publishDate;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getSnippet() {
        return snippet;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public String toString() {
        return ArticleTo.class.getSimpleName() + "{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", snippet='" + snippet + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
