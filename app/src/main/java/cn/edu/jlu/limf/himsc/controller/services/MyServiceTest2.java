package cn.edu.jlu.limf.himsc.controller.services;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import cn.edu.jlu.limf.himsc.MyApplication.WholeDataApplication;
import cn.edu.jlu.limf.himsc.models.HealthRecordBean;
import cn.edu.jlu.limf.himsc.models.TestBean;
import cn.edu.jlu.limf.himsc.utils.networking.OkHttpControl;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-6 下午5:08
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
public class MyServiceTest2 extends Service {
    private DateFormat date;
    private String conn_status="";
    private WholeDataApplication wholeDataApplication;
    private int MEDIUM=2;
    private SQLiteDatabase db;
    private OkHttpControl oKHttpControl;
    static String TAG = "Merlin";

    public String getUserAccountID() {
        return UserAccountID;
    }

    public void setUserAccountID(String userAccountID) {
        UserAccountID = userAccountID;
    }

    private String UserAccountID="";
    private HealthRecordBean healthRecordBean;
    public MyServiceTest2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        oKHttpControl=new OkHttpControl();
//        setUserAccountID("220305194609150666");
        wholeDataApplication=(WholeDataApplication)getApplication();
        setUserAccountID(wholeDataApplication.getUserId());
        db=LitePal.getDatabase();
        healthRecordBean=new HealthRecordBean();
        healthRecordBean.setUserAccountId(UserAccountID);
        healthRecordBean.setDate("17-05-06");
        healthRecordBean.setStartTime("17:11");
        healthRecordBean.setEndTime("17:13");
        healthRecordBean.setMinutes("2");
        healthRecordBean.setMax("94");
        healthRecordBean.setMin("30");
        healthRecordBean.setCaloriesOut("2.3246");
        healthRecordBean.setTypeName("Out of Range");
        healthRecordBean.save();
        final Gson gson = new Gson();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json_post_info = gson.toJson(healthRecordBean);
                    conn_status=oKHttpControl.post("http://merlin-lee.com:8080/himsc/ReceiveUserRecord",json_post_info);
                    Log.d(TAG, "onCreate: "+conn_status);
                } catch (IOException e) {
                    Log.d(TAG, "onCreate: POST出现问题"+" "+conn_status);
                    Log.e(TAG, e.toString());
                }
            }
        }).start();
    }
}
