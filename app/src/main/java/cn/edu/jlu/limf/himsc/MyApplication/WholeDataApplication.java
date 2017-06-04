package cn.edu.jlu.limf.himsc.MyApplication;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by merlin on 17-6-3.
 */

public class WholeDataApplication extends Application {
    private static Context context;
    private String userId;
    private static String URL = "http://49.140.92.170:8080";

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }

    public static Context getContext(){
        return context;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static String getURL() {
        return URL;
    }
}
