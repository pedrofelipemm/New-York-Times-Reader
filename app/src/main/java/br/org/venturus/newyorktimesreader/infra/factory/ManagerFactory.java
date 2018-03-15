package br.org.venturus.newyorktimesreader.infra.factory;

import br.org.venturus.newyorktimesreader.manager.ArticleManager;

public class ManagerFactory {

    private ManagerFactory(){}

    public static ArticleManager createArticleManager() {
        return new ArticleManager();
    }

}
