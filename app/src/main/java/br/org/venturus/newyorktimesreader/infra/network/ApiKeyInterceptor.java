package br.org.venturus.newyorktimesreader.infra.network;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private final String key;

    public ApiKeyInterceptor(String key) {
        this.key = key;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api-key", key)
                .build();

        return chain.proceed(original.newBuilder().url(url).build());
    }
}
