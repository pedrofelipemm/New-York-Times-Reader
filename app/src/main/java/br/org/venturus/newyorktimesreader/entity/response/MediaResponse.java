package br.org.venturus.newyorktimesreader.entity.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MediaResponse {

    @SerializedName("media-metadata")
    private List<MediaMetadata> mediaMetadata = new ArrayList<>();

    @SuppressWarnings({"unused", "WeakerAccess"})
    public List<MediaMetadata> getMediaMetadata() {
        return new ArrayList<>(mediaMetadata);
    }

    @Override
    public String toString() {
        return MediaResponse.class.getSimpleName() + "{" +
                "mediaMetaDatas=" + mediaMetadata +
                '}';
    }
}
