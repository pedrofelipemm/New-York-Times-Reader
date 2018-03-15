package br.org.venturus.newyorktimesreader.business.mapper;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import br.org.venturus.newyorktimesreader.entity.response.MediaMetadata;
import br.org.venturus.newyorktimesreader.entity.response.MediaResponse;
import br.org.venturus.newyorktimesreader.entity.response.MostViewedResponse;
import br.org.venturus.newyorktimesreader.entity.response.MostViewedResponses;
import br.org.venturus.newyorktimesreader.entity.to.ArticleTo;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.utils.DateUtils;
import timber.log.Timber;

public class ArticlesMapper {

    //TODO parametrizar qual url buscar

    private static final String DEFAULT_MOST_VIEWED_URL_FORMAT = "Standard Thumbnail";

    //TODO delete? private static final String DEFAULT_URL_FORMAT = "thumbLarge";
//    public static ArticlesTo toArticlesTo(MostViewedResponses response) {
//        ArticlesTo articles = new ArticlesTo();
//
//        if (response.getResults() == null) {
//            return articles;
//        }
//
//        for (MostViewedResponse story : response.getResults()) {
//
//            Date publishDate = null;
//            try {
//                publishDate = DateUtils.parse(story.getPublishedDate());
//            } catch (ParseException e) {
//                Timber.e(e);
//            }
//
//            articles.addArticle(new ArticleTo(
//                    story.getTitle(),
//                    publishDate,
//                    story.getSnippet(),
//                    extractDefaultUrl(story.getMultimedia())
//            ));
//        }
//
//
//        return articles;
//    }

    public static ArticlesTo toArticlesTo(MostViewedResponses response) {
        ArticlesTo articles = new ArticlesTo();

        if (response.getResults() == null) {
            return articles;
        }

        for (MostViewedResponse item : response.getResults()) {

            Date publishDate = null;
            try {
                publishDate = DateUtils.parse(item.getPublishedDate());
            } catch (ParseException e) {
                Timber.e(e);
            }

            articles.addArticle(new ArticleTo(
                    item.getTitle(),
                    publishDate,
                    item.getSnippet(),
                    extractDefaultMostViewedUrl(item.getMultimedia())
            ));
        }


        return articles;
    }

    //TODO suportar outras orientações e tamanhos de tela
    private static String extractDefaultMostViewedUrl(List<MediaResponse> mediaResponses) {
        String url = "";

        for (MediaResponse media : mediaResponses) {
            for (MediaMetadata data : media.getMediaMetadata()) {
                if (DEFAULT_MOST_VIEWED_URL_FORMAT.equals(data.getFormat())) {
                    url = data.getUrl();
                    break;
                }
            }
        }

        return url;
    }

}
