package br.org.venturus.newyorktimesreader.infra.factory;

import java.util.concurrent.TimeUnit;

import br.org.venturus.newyorktimesreader.BuildConfig;
import br.org.venturus.newyorktimesreader.infra.Constants.Environment;
import br.org.venturus.newyorktimesreader.infra.NYTReaderApplication;
import br.org.venturus.newyorktimesreader.infra.network.ApiKeyInterceptor;
import br.org.venturus.newyorktimesreader.infra.network.ApiResponseCallAdapterFactory;
import br.org.venturus.newyorktimesreader.infra.network.ConnectivityInterceptor;
import br.org.venturus.newyorktimesreader.infra.network.NetworkUtils;
import br.org.venturus.newyorktimesreader.repository.api.NYTApi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkFactory {

    //TODO do not commit api key, should be in gradle.properties

    private static final int DEFAULT_TIMEOUT = 180;

    private NetworkFactory(){}

    public static Retrofit createNewSicRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(NetworkUtils.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new ApiResponseCallAdapterFactory())
                .client(createHttpClient())
                .build();
    }

    public static NYTApi createNYTApi() {
        return createNewSicRetrofit().create(NYTApi.class);
    }

    public static OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new ConnectivityInterceptor(NYTReaderApplication.provideApplicationContext()))
                .addInterceptor(new ApiKeyInterceptor(BuildConfig.NYT_API_KEY))
                .addInterceptor(createLogginInterceptor())
                .build();
    }

    private static Interceptor createLogginInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        switch (BuildConfig.ENVIRONMENT) {
            case Environment.DEVELOPMENT: return loggingInterceptor.setLevel(Level.BODY);
            default: return loggingInterceptor.setLevel(Level.NONE);
        }
    }

}
