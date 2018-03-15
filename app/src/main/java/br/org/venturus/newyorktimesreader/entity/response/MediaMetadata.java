package br.org.venturus.newyorktimesreader.entity.response;

public class MediaMetadata {

    private String format;
    private String url;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getFormat() {
        return format;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return MediaMetadata.class.getSimpleName() + "{" +
                "format='" + format + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
