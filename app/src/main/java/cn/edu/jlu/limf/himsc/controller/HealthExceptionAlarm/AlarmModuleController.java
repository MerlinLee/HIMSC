package cn.edu.jlu.limf.himsc.controller.HealthExceptionAlarm;

/**
 * Created by merlin on 17-5-11.
 */

import android.Manifest;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zhy.m.permission.MPermissions;

import org.litepal.LitePal;

import cn.edu.jlu.limf.himsc.models.HealthRecordBean;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-11 下午12:30
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
public class AlarmModuleController {
    private static String TAG = "Merlin";
    private HealthRecordBean healthRecordBean;
    private HealthAlarm healthAlarm;
    private boolean isAlarm;
    private SQLiteDatabase db;

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public AlarmModuleController(HealthRecordBean healthRecordBean) {
        this.healthRecordBean = healthRecordBean;
        db= LitePal.getDatabase();
    }

    public void isException(){
        HeartRateRange heartRateRange = new HeartRateRange();
        String isException = heartRateRange.isException(healthRecordBean.getMin(),healthRecordBean.getMax());
        if(isException.equals("ERROR")){
            //启动报警机制

            healthAlarm = new HealthAlarm("18689659563");
            healthAlarm.sendSMS_status("需要急救！心跳异常！");
            try {
                //数据也应当存入
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: " + "线程内数据存入数据库中");
                        healthRecordBean.save();
                    }
                }).start();
            }catch (Exception e){
                Log.d(TAG, "isException: "+e.toString());
            }
        }else if (isException.equals("NORMAL")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: "+"线程内数据存入数据库中");
                    healthRecordBean.save();
                }
            }).start();
        }
    }


}
