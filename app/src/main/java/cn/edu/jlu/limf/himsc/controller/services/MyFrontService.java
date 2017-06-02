package cn.edu.jlu.limf.himsc.controller.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import cn.edu.jlu.limf.himsc.R;
import cn.edu.jlu.limf.himsc.views.ControllerViewActivity;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-7 下午4:01
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
public class MyFrontService extends Service {
    public MyFrontService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, ControllerViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("蓝牙健康数据收集器")
                .setContentText("接收器正在运行")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.doctors_bag_96)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.doctors_bag_color))
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);
    }
}
