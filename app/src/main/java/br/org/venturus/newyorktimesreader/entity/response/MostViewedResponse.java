package br.org.venturus.newyorktimesreader.entity.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MostViewedResponse {

    private String url;

    private String title;

    @SerializedName("published_date")
    private String publishedDate;

    @SerializedName("abstract")
    private String snippet;

    private List<MediaResponse> media = new ArrayList<>();

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getUrl() {
        return url;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getTitle() {
        return title;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getPublishedDate() {
        return publishedDate;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getSnippet() {
        return snippet;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public List<MediaResponse> getMedia() {
        return new ArrayList<>(media);
    }

    @Override
    public String toString() {
        return MostViewedResponse.class.getSimpleName() + "{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", snippet='" + snippet + '\'' +
                ", media=" + media +
                '}';
    }
}
