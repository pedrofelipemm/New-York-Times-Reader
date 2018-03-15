package br.org.venturus.newyorktimesreader.entity.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocResponse {

    private HeadlineResponse headline = new HeadlineResponse();

    @SerializedName("web_url")
    private String url;

    @SerializedName("pub_date")
    private String publishedDate;

    private String snippet;

    @SerializedName("multimedia")
    private List<MultimediaResponse> multimedias;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getUrl() {
        return url;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public HeadlineResponse getHeadline() {
        return headline;
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
    public List<MultimediaResponse> getMultimedias() {
        return multimedias;
    }

    @Override
    public String toString() {
        return DocResponse.class.getSimpleName() + "{" +
                "headline=" + headline +
                ", url='" + url + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", snippet='" + snippet + '\'' +
                ", multimedias=" + multimedias +
                '}';
    }
}
