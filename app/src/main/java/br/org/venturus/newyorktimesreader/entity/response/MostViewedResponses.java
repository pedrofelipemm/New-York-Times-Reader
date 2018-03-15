package br.org.venturus.newyorktimesreader.entity.response;

import java.util.List;

public class MostViewedResponses {

    private List<MostViewedResponse> results;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public List<MostViewedResponse> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return MostViewedResponses.class.getSimpleName() + "{" +
                "results=" + results +
                '}';
    }
}
