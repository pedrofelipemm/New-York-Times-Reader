package br.org.venturus.newyorktimesreader.infra.network;

import java.io.IOException;
import java.lang.annotation.Annotation;

import br.org.venturus.newyorktimesreader.infra.factory.ApiErrorFactory;
import br.org.venturus.newyorktimesreader.infra.factory.NetworkFactory;
import br.org.venturus.newyorktimesreader.infra.network.ApiError.Tipo;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import timber.log.Timber;

class ApiUtils {

    private ApiUtils(){};

    static ApiError parseError(ResponseBody responseBody) {
        ApiError error;

        try {
            Converter<ResponseBody, ApiError> converter =
                    NetworkFactory.createNewSicRetrofit()
                            .responseBodyConverter(ApiError.class, new Annotation[0]);

            error = converter.convert(responseBody);

        } catch (IOException e) {
            Timber.e(e, "Error while parsing response");
            error = ApiErrorFactory.createParseError();
        } catch (Throwable t) {
            Timber.e(t);
            error = new ApiError(t.toString(), Tipo.ERROR);
        }

        return error;
    }
}
