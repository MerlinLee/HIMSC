package cn.edu.jlu.limf.himsc.views;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.jlu.limf.himsc.MyApplication.WholeDataApplication;
import cn.edu.jlu.limf.himsc.R;
import cn.edu.jlu.limf.himsc.controller.Receivers.UploadDataService;
import cn.edu.jlu.limf.himsc.controller.services.BluetoothController.BluetoothMainController;

import static android.R.attr.duration;
import static android.R.attr.theme;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-5 下午1:34
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-6-4 上午8:16
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
 *@修改版本：version 2.0
*/
public class ControllerViewActivity extends AppCompatActivity implements View.OnClickListener{
    private WholeDataApplication wholeDataApplication;
    private String DoubleClickCode = "1";
    private Intent intent_front;
    private int btn_open_timer_connect_server_click_sum;
    private Button btn_ble_service;
    private Button btn_upload_controller;
    private final Timer timer = new Timer();
    private TimerTask task;
    private static final int REQUECT_CODE_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wholeDataApplication=(WholeDataApplication)getApplication();
        setContentView(R.layout.activity_controller_view);
        Toast.makeText(this,"当前用户身份证号： "+wholeDataApplication.getUserId(),Toast.LENGTH_LONG).show();
        btn_upload_controller=(Button)findViewById(R.id.btn_open_timer_connect_server);
        btn_ble_service=(Button)findViewById(R.id.blt_btn_open);
        btn_ble_service.setOnClickListener(this);
        btn_upload_controller.setOnClickListener(this);
        btn_open_timer_connect_server_click_sum=1;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.blt_btn_open:
//                if(DoubleClickCode.equals("1")){
//                    intent_front = new Intent(ControllerViewActivity.this, BluetoothMainController.class);
//                    startService(intent_front);
//                    DoubleClickCode="2";
//                }else if(DoubleClickCode.equals("2")){
//                    stopService(intent_front);
//                    DoubleClickCode="1";
//                }
//                if (ContextCompat.checkSelfPermission(ControllerViewActivity.this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(ControllerViewActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
//                } else {
//                    // 原来的敏感操作代码：发短信或者收短信

//                }
//                intent_front = new Intent(ControllerViewActivity.this, SmallBluetoothDeviceActivity.class);
//                startActivity(intent_front);
                MPermissions.requestPermissions(ControllerViewActivity.this, REQUECT_CODE_SMS, Manifest.permission.SEND_SMS);
//                if(DoubleClickCode.equals("1")){
//                    try {
//                        intent_front = new Intent(ControllerViewActivity.this, SmallWindowsActivity.class);
//                        startActivity(intent_front);
//                    }catch (Exception e){
//                        Log.d("Merlin", e.toString());
//                    }
//                    DoubleClickCode="2";
//                }else {
//                    DoubleClickCode="1";
//                }
                break;
            case R.id.btn_open_timer_connect_server:

                if(btn_open_timer_connect_server_click_sum==1){
                    init_timing();
                    open_timing();
                    Toast.makeText(this," 定时器已开始 ",Toast.LENGTH_LONG).show();
                }
                if(btn_open_timer_connect_server_click_sum==2){
                    timer.cancel();
                    Toast.makeText(this," 定时器已停止 ",Toast.LENGTH_LONG).show();
                    btn_open_timer_connect_server_click_sum=1;
                }else {
                    btn_open_timer_connect_server_click_sum=2;
                }
                break;
            default:
                break;
        }
    }

    public String init_timing(){
        try {
            task = new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
            return "SUCCESS";
        }catch (Exception e){
            return e.toString();
        }
    }

    public void open_timing(){
        timer.schedule(task, 60000, 60000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 定时上传老年人健康数据
            super.handleMessage(msg);
            Log.d("Merlin", "handleMessage: 定时器开启服务");
            Intent intent = new Intent(ControllerViewActivity.this, UploadDataService.class);
            startService(intent);
        }
    };

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // 原来的敏感操作代码：发短信或者收短信
//                    intent_front = new Intent(ControllerViewActivity.this, SmallBluetoothDeviceActivity.class);
//                    startActivity(intent_front);
//                } else {
//                    Toast.makeText(this," 拒绝授权 ",Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_SMS)
    public void requestSdcardSuccess()
    {
        Toast.makeText(this, "GRANT ACCESS SMS!", Toast.LENGTH_SHORT).show();
        intent_front = new Intent(ControllerViewActivity.this, SmallBluetoothDeviceActivity.class);
        startActivity(intent_front);
    }

    @PermissionDenied(REQUECT_CODE_SMS)
    public void requestSdcardFailed()
    {
        Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }
}
