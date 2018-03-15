package br.org.venturus.newyorktimesreader.business;

import br.org.venturus.newyorktimesreader.business.mapper.ArticlesMapper;
import br.org.venturus.newyorktimesreader.business.validator.ArticleValidator;
import br.org.venturus.newyorktimesreader.entity.enm.ArticleSection;
import br.org.venturus.newyorktimesreader.entity.response.MostViewedResponses;
import br.org.venturus.newyorktimesreader.entity.response.SearchArticlesResponses;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.OperationResult;
import br.org.venturus.newyorktimesreader.infra.factory.ApiErrorFactory;
import br.org.venturus.newyorktimesreader.infra.network.ApiError;
import br.org.venturus.newyorktimesreader.infra.network.ApiResponse;
import br.org.venturus.newyorktimesreader.repository.api.NYTApi;
import timber.log.Timber;

public class ArticleBusiness {

    private static final String DEFAULT_PERIOD = "7";

    private NYTApi nytApi;
    private ArticleValidator articleValidator;

    public ArticleBusiness(NYTApi nytApi, ArticleValidator articleValidator) {
        this.nytApi = nytApi;
        this.articleValidator = articleValidator;
    }

    public OperationResult<ArticlesTo> loadMostViewed(ArticleSection section) {
        Timber.i("[BEGIN] loadMostViewed - %s", section);

        OperationResult<ArticlesTo> result = new OperationResult<>();

        result.setValidationCodes(articleValidator.validateMostViewed(section));
        if (!result.getValidationCodes().isEmpty()) {
            result.setError(ApiErrorFactory.createValidationError());

            Timber.i("[END] loadMostViewed - %s", result);
            return result;
        }

        ApiResponse<MostViewedResponses> response = nytApi.fetchMostViewed(section.getValue(), DEFAULT_PERIOD);

        if (response.isSuccessful()) {
            result.setResult(ArticlesMapper.toArticlesTo(response.getResult()));
        } else {
            ApiError error = response.getError();
            Timber.e(error.toString());

            result.setError(error);
        }

        Timber.i("[END] loadMostViewed - %s", result);
        return result;
    }

    public OperationResult<ArticlesTo> searchArticles(CharSequence query, int page) {
        Timber.i("[BEGIN] searchArticles - %s %s", query, page);

        OperationResult<ArticlesTo> result = new OperationResult<>();

        result.setValidationCodes(articleValidator.validateSearchArticles(query, page));
        if (!result.getValidationCodes().isEmpty()) {
            result.setError(ApiErrorFactory.createValidationError());

            Timber.i("[END] searchArticles - %s", result);
            return result;
        }

        ApiResponse<SearchArticlesResponses> response = nytApi.searchArticles(String.valueOf(query), String.valueOf(page));

        if (response.isSuccessful()) {
            result.setResult(ArticlesMapper.toArticlesTo(response.getResult()));
        } else {
            ApiError error = response.getError();
            Timber.e(error.toString());

            result.setError(error);
        }

        Timber.i("[END] searchArticles - %s", result);
        return result;
    }

}
