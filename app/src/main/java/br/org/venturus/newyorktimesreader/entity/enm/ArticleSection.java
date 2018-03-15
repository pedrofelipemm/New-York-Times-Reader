package br.org.venturus.newyorktimesreader.entity.enm;

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