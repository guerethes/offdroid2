package br.com.guerethes.offdroid.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils extends Activity {

    private static boolean onLine;

    public static boolean isOnline(Context context) {
        return isConnected(context);
    }

    private static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isOnline() {
        return onLine;
    }

    public static void setOnline(boolean ativo) {
        onLine = ativo;
    }

}