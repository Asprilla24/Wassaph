package id.co.wassaph.aladhine.wassaph.manager;

import android.app.Application;
import android.content.Context;

import ai.api.android.AIConfiguration;
import io.realm.Realm;

/**
 * Created by aladhine on 01/11/17.
 */

public class AppController extends Application {

    private static Context context;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Init Realm
        Realm.init(this);
    }

    public static Context getContext(){
        return context;
    }
}
