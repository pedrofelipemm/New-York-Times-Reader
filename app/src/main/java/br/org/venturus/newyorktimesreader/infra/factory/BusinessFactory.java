package br.org.venturus.newyorktimesreader.infra.factory;

import br.org.venturus.newyorktimesreader.business.ArticleBusiness;
import br.org.venturus.newyorktimesreader.business.validator.ArticleValidator;
import br.org.venturus.newyorktimesreader.repository.api.NYTApi;

public class BusinessFactory {

    private BusinessFactory(){}

    public static ArticleBusiness createArticleBusiness() {
        NYTApi nytApi = NetworkFactory.createNYTApi();

        return new ArticleBusiness(nytApi, new ArticleValidator());
    }

}
