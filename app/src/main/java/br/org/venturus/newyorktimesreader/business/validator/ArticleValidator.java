package br.org.venturus.newyorktimesreader.business.validator;

import java.util.ArrayList;
import java.util.List;

import br.org.venturus.newyorktimesreader.business.ArticleBusiness.ArticleSection;

public class ArticleValidator {

    public static final String SECTION_VAZIO = "SECTION_VAZIO";

    public List<String> validateMostViewed(ArticleSection section) {
        List<String> validationCodes = new ArrayList<>();

        if (section == null) {
            validationCodes.add(SECTION_VAZIO);
        }

        return validationCodes;
    }
}
