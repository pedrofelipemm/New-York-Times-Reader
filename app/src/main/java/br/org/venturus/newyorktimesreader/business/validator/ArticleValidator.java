package br.org.venturus.newyorktimesreader.business.validator;

import java.util.ArrayList;
import java.util.List;

import br.org.venturus.newyorktimesreader.entity.enm.ArticleSection;
import br.org.venturus.newyorktimesreader.infra.utils.StringUtils;

public class ArticleValidator {

    public static final String SECTION_VAZIO = "SECTION_VAZIO";
    public static final String QUERY_VAZIO = "QUERY_VAZIO";
    public static final String PAGE_INVALIDO = "PAGE_INVALIDO";

    public List<String> validateMostViewed(ArticleSection section) {
        List<String> validationCodes = new ArrayList<>();

        if (section == null) {
            validationCodes.add(SECTION_VAZIO);
        }

        return validationCodes;
    }

    public List<String> validateSearchArticles(CharSequence query, int page) {
        List<String> validationCodes = new ArrayList<>();

        if (StringUtils.isEmpty(query)) {
            validationCodes.add(QUERY_VAZIO);
        }

        if (page < 0 || page > 200) {
            validationCodes.add(PAGE_INVALIDO);
        }

        return validationCodes;
    }
}
