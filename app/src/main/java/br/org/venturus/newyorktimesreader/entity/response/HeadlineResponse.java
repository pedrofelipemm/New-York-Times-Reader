package br.org.venturus.newyorktimesreader.entity.response;

import com.google.gson.annotations.SerializedName;

public class HeadlineResponse {

    @SerializedName("main")
    private String title;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return HeadlineResponse.class.getSimpleName() + "{" +
                "title='" + title + '\'' +
                '}';
    }
}
