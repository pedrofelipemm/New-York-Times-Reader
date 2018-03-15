package br.org.venturus.newyorktimesreader.business.mapper;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import br.org.venturus.newyorktimesreader.entity.response.DocResponse;
import br.org.venturus.newyorktimesreader.entity.response.MediaMetadata;
import br.org.venturus.newyorktimesreader.entity.response.MediaResponse;
import br.org.venturus.newyorktimesreader.entity.response.MostViewedResponse;
import br.org.venturus.newyorktimesreader.entity.response.MostViewedResponses;
import br.org.venturus.newyorktimesreader.entity.response.MultimediaResponse;
import br.org.venturus.newyorktimesreader.entity.response.SearchArticlesResponses;
import br.org.venturus.newyorktimesreader.entity.to.ArticleTo;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.utils.DateUtils;
import timber.log.Timber;

public class ArticlesMapper {

    public static ArticlesTo toArticlesTo(SearchArticlesResponses response) {
        ArticlesTo articles = new ArticlesTo();

        List<DocResponse> docs = response.getResponse().getDocs();
        for (DocResponse doc : docs) {

            Date publishDate = null;
            try {
                publishDate = DateUtils.parse(doc.getPublishedDate());
            } catch (ParseException e) {
                Timber.e(e);
            }

            articles.addArticle(new ArticleTo(
                    doc.getUrl(),
                    doc.getHeadline().getTitle(),
                    publishDate,
                    doc.getSnippet(),
                    extractDefaultSearchUrl(doc.getMultimedias())
            ));
        }

        return articles;
    }

    public static ArticlesTo toArticlesTo(MostViewedResponses response) {
        ArticlesTo articles = new ArticlesTo();

        for (MostViewedResponse item : response.getResults()) {

            Date publishDate = null;
            try {
                publishDate = DateUtils.parse(item.getPublishedDate());
            } catch (ParseException e) {
                Timber.e(e);
            }

            articles.addArticle(new ArticleTo(
                    item.getUrl(),
                    item.getTitle(),
                    publishDate,
                    item.getSnippet(),
                    extractDefaultMostViewedUrl(item.getMedia())
            ));
        }


        return articles;
    }

    private static String extractDefaultMostViewedUrl(List<MediaResponse> mediaResponses) {
        String url = "";

        for (MediaResponse media : mediaResponses) {
            for (MediaMetadata data : media.getMediaMetadata()) {
                if (MediaMetadata.DEFAULT_FORMAT.equals(data.getFormat())) {
                    url = data.getUrl();
                    break;
                }
            }
        }

        return url;
    }

    private static String extractDefaultSearchUrl(List<MultimediaResponse> multimedias) {
        String url = "";

        for (MultimediaResponse multimedia : multimedias) {
            if (MultimediaResponse.DEFAULT_SUBTYPE.equals(multimedia.getSubtype())) {
                url = multimedia.getUrl();
                break;
            }
        }

        return url;
    }
}
