package org.take365.take365;

import android.app.Application;
import android.content.Context;

/**
 * Created by evgeniy on 08.02.16.
 */
public class Take365Application extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Take365Application.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Take365Application.context;
    }

}
