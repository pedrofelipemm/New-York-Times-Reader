package br.org.venturus.newyorktimesreader.infra.network;

public final class NetworkConstants {

    public interface Error {
        String NO_CONNECTION_AVAILABLE_CODE = "7002";
        String NO_CONNECTION_AVAILABLE_DESC = "No Connection available";

        String PARSE_ERROR_CODE = "7003";
        String PARSE_ERROR_DESC = "Error while parsing data";

        String VALIDATION_CODE = "7004";
        String VALIDATION_DESC = "Business Validation Error. To be handled by validator.";

        String TIMEOUT_CODE = "7005";
        String TIMEOUT_DESC = "Timeout.";

        String INTERNAL_SERVER_ERROR_CODE = "500";
        String INTERNAL_SERVER_ERROR_DESC = "INTERNAL SERVER ERROR";

    }

    public interface Endpoints {
        String MOST_VIEWED = "mostpopular/v2/mostviewed/{section}/{time-period}";
        String SEARCH = "search/v2/articlesearch.json";
    }

    public interface BaseUrl {
        String DEV = "https://api.nytimes.com/svc/";
        String RELEASE = "https://api.nytimes.com/svc/";
        String PROD = "https://api.nytimes.com/svc/";
    }
}
