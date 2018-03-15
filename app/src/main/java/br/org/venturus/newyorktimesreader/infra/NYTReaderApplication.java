package br.org.venturus.newyorktimesreader.infra;

import android.app.Application;
import android.content.Context;

import br.org.venturus.newyorktimesreader.BuildConfig;
import timber.log.Timber;

public class NYTReaderApplication extends Application {

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static Context provideApplicationContext() {
        return applicationContext;
    }

}
