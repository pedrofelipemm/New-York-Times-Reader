package br.org.venturus.newyorktimesreader.entity.response;

public class MultimediaResponse {

    private String url;
    private String format;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getUrl() {
        return url;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return MultimediaResponse.class.getSimpleName() + "{" +
                "url='" + url + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
