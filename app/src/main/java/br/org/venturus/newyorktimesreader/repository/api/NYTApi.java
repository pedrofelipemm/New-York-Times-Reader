package br.org.venturus.newyorktimesreader.repository.api;

import br.org.venturus.newyorktimesreader.entity.response.MostViewedResponses;
import br.org.venturus.newyorktimesreader.entity.response.SearchArticlesResponses;
import br.org.venturus.newyorktimesreader.infra.network.ApiResponse;
import br.org.venturus.newyorktimesreader.infra.network.NetworkConstants.Endpoints;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTApi {

    @GET(Endpoints.MOST_VIEWED)
    ApiResponse<MostViewedResponses> fetchMostViewed(@Path("section") String section, @Path("time-period") String period);

    @GET(Endpoints.SEARCH)
    ApiResponse<SearchArticlesResponses> searchArticles(@Query("q") String q, @Query("page") String page);
}
