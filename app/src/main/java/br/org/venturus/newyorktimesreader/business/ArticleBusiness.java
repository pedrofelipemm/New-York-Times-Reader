package br.org.venturus.newyorktimesreader.business;

import br.org.venturus.newyorktimesreader.business.mapper.ArticlesMapper;
import br.org.venturus.newyorktimesreader.business.validator.ArticleValidator;
import br.org.venturus.newyorktimesreader.entity.response.MostViewedResponses;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.OperationResult;
import br.org.venturus.newyorktimesreader.infra.factory.ApiErrorFactory;
import br.org.venturus.newyorktimesreader.infra.network.ApiError;
import br.org.venturus.newyorktimesreader.infra.network.ApiResponse;
import br.org.venturus.newyorktimesreader.repository.api.NYTApi;
import timber.log.Timber;

public class ArticleBusiness {

    private static final String DEFAULT_PERIOD = "7";

    //TODO: article repository, offline first
    //TODO: handle no connection

    private NYTApi nytApi;
    private ArticleValidator articleValidator;

    public ArticleBusiness(NYTApi nytApi, ArticleValidator articleValidator) {
        this.nytApi = nytApi;
        this.articleValidator = articleValidator;
    }

    public OperationResult<ArticlesTo> loadMostViewed(ArticleSection section) {
        Timber.i("[BEGIN] loadMostViewed");

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

        Timber.i("[END] loadMostViewed");
        return result;
    }

    //TODO move it
    //TODO URL builder
    //TODO StrinfDef ?
    public enum ArticleSection {
        BUSINESS("business"),
        SCIENCE("science"),
        TECHNOLOGY("technology");

        private String value;

        ArticleSection(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static ArticleSection getByValue(String value) {
            ArticleSection articleSection = null;
            for (ArticleSection articleSectionItem : ArticleSection.class.getEnumConstants()) {
                if (articleSectionItem.getValue().equals(value)) {
                    articleSection = articleSectionItem;
                    break;
                }
            }
            return articleSection;
        }
    }
}
