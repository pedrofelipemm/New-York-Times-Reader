package br.org.venturus.newyorktimesreader.entity.response;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

    private List<DocResponse> docs = new ArrayList<>();

    @SuppressWarnings({"unused", "WeakerAccess"})
    public List<DocResponse> getDocs() {
        return new ArrayList<>(docs);
    }

    @Override
    public String toString() {
        return SearchResponse.class.getSimpleName() + "{" +
                "docs=" + docs +
                '}';
    }
}
