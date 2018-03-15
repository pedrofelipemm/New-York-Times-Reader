package br.org.venturus.newyorktimesreader.entity.response;

import java.util.ArrayList;
import java.util.List;

public class MostViewedResponses {

    private List<MostViewedResponse> results = new ArrayList<>();

    @SuppressWarnings({"unused", "WeakerAccess"})
    public List<MostViewedResponse> getResults() {
        return new ArrayList<>(results);
    }

    @Override
    public String toString() {
        return MostViewedResponses.class.getSimpleName() + "{" +
                "results=" + results +
                '}';
    }
}
