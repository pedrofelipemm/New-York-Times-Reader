package br.org.venturus.newyorktimesreader.infra.network;

import android.content.Context;
import android.net.ConnectivityManager;

import br.org.venturus.newyorktimesreader.BuildConfig;
import br.org.venturus.newyorktimesreader.infra.Constants.Environment;
import br.org.venturus.newyorktimesreader.infra.network.NetworkConstants.BaseUrl;

public class NetworkUtils {

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static String getBaseUrl() {
        switch (BuildConfig.ENVIRONMENT) {
            case Environment.DEVELOPMENT: return BaseUrl.DEV;
            default: return BaseUrl.DEV;
        }
    }

}
