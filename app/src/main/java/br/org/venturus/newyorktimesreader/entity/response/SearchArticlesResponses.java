package br.org.venturus.newyorktimesreader.entity.response;

public class SearchArticlesResponses {

    private SearchResponse response = new SearchResponse();

    @SuppressWarnings({"unused", "WeakerAccess"})
    public SearchResponse getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return SearchArticlesResponses.class.getSimpleName() + "{" +
                "response=" + response +
                '}';
    }
}
