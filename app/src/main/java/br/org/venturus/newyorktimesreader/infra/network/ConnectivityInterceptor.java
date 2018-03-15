package br.org.venturus.newyorktimesreader.infra.network;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import br.org.venturus.newyorktimesreader.infra.exception.NoConnectivityException;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    private Context context;

    public ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!NetworkUtils.isConnectionAvailable(context)) {
            throw new NoConnectivityException();
        }

        return chain.proceed(chain.request().newBuilder().build());
    }

}
