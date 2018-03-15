package br.org.venturus.newyorktimesreader.ui;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsIntent.Builder;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import br.org.venturus.newyorktimesreader.R;
import timber.log.Timber;

public class ViewUtils {

    private ViewUtils() {}

    public static void hideKeyboard(Window window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void launchChromeCustomTab(Context context, String url) {
        CustomTabsIntent customTabsIntent = new Builder()
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build();

        if (customTabsIntent.intent.resolveActivity(context.getPackageManager()) != null) {
            try {
                customTabsIntent.launchUrl(context, Uri.parse(url));
            } catch (Exception e) {
                Timber.e(e);
            }
        } else {
            Timber.e(context.getString(R.string.unable_to_handle_actionview, url));
        }
    }

}
