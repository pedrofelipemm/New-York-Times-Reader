package br.org.venturus.newyorktimesreader.entity.response;

public class MultimediaResponse {

    private static final String NYT_IMAGE_BASE_URL = "https://www.nytimes.com/";

    public static final String DEFAULT_SUBTYPE = "thumbnail";

    private String url;
    private String subtype;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getUrl() {
        return NYT_IMAGE_BASE_URL + url;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getSubtype() {
        return subtype;
    }

    @Override
    public String toString() {
        return MultimediaResponse.class.getSimpleName() + "{" +
                "url='" + url + '\'' +
                ", subtype='" + subtype + '\'' +
                '}';
    }
}
