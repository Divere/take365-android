package org.take365.take365;

import android.app.Application;
import android.content.Context;

/**
 * Created by evgeniy on 08.02.16.
 */
public class Take365Application extends Application {

    private static Take365Application application;

    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getAppContext() {
        return application.getApplicationContext();
    }
}
;