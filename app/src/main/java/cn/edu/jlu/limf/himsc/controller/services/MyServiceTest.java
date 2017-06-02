package cn.edu.jlu.limf.himsc.controller.services;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.google.gson.Gson;

import org.litepal.LitePal;

import cn.edu.jlu.limf.himsc.models.HeartRateZones;
import cn.edu.jlu.limf.himsc.models.UserHealthRecordBean;
/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-6 下午1:17
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
public class MyServiceTest extends Service {
    private SQLiteDatabase db;
    static String TAG = "Merlin";
    public MyServiceTest() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        db=LitePal.getDatabase();
//        Log.d(TAG, "onCreate: LitePal初始化完成");
        String json;
        Gson gson = new Gson();
        UserHealthRecordBean userHealthRecordBean=new UserHealthRecordBean();
        HeartRateZones heartRateZones = new HeartRateZones();
        heartRateZones.setMinutes("00:02");
        heartRateZones.setCaloriesOut("2.3246");
        heartRateZones.setMax("90");
        heartRateZones.setMin("30");
        heartRateZones.setName("Out of Range");
        //
        userHealthRecordBean.setUserAccountID("220508195208090333");
        userHealthRecordBean.setDate("17-05-06");
        userHealthRecordBean.setStart_time("08:00");
        userHealthRecordBean.setEnd_time("08:02");
        heartRateZones.setUserAccountID(userHealthRecordBean.getUserAccountID());
        heartRateZones.setDate(userHealthRecordBean.getDate());
        heartRateZones.setStart_time(userHealthRecordBean.getStart_time());
        heartRateZones.setEnd_time(userHealthRecordBean.getEnd_time());
        userHealthRecordBean.setHeartRateZones(heartRateZones);
        json=gson.toJson(userHealthRecordBean);
        userHealthRecordBean.save();
        heartRateZones.save();
        Log.d(TAG, json);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
