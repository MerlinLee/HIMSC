package cn.edu.jlu.limf.himsc.controller.Receivers;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.google.gson.Gson;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import cn.edu.jlu.limf.himsc.MyApplication.WholeDataApplication;
import cn.edu.jlu.limf.himsc.models.HealthRecordBean;
import cn.edu.jlu.limf.himsc.utils.networking.OkHttpControl;

public class UploadDataService extends Service {
    private SQLiteDatabase db;
    private OkHttpControl okHttpControl;
    private static String TAG="Merlin";
    private WholeDataApplication wholeDataApplication;

    public UploadDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wholeDataApplication=(WholeDataApplication)getApplication();
        db= LitePal.getDatabase();
        Log.d(TAG, "onCreate: "+"上传服务正在运行");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        List<HealthRecordBean> list_upload = DataSupport.findAll(HealthRecordBean.class);
        if(list_upload.size()>0){
            final String json_list = new Gson().toJson(list_upload).toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpControl=new OkHttpControl();
                    String status="ERROR";
                    try {
                        status=okHttpControl.post(wholeDataApplication.getURL()+"/himsc/uploadData",json_list);
                        Log.d(TAG, "run: "+status);
                    } catch (IOException e) {
                        Log.d(TAG, "run: "+e.toString());
                    }finally {
                        if (status.equals("SUCCESS")){
                            DataSupport.deleteAll(HealthRecordBean.class);
                        }
                        stopSelf();
                    }
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
