package cn.edu.jlu.limf.himsc.views;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.edu.jlu.limf.himsc.R;
import cn.edu.jlu.limf.himsc.controller.services.BluetoothController.BluetoothMainController;

import static android.R.attr.duration;
import static android.R.attr.theme;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-5 下午1:34
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
public class ControllerViewActivity extends AppCompatActivity implements View.OnClickListener{
    private String DoubleClickCode = "1";
    private Intent intent_front;
    public NotificationManager notificationManager;
    private Button btn_ble_service;
    private Button btn_upload_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_view);
        btn_upload_controller=(Button)findViewById(R.id.btn_open_timer_connect_server);
        btn_ble_service=(Button)findViewById(R.id.blt_btn_open);
        btn_ble_service.setOnClickListener(this);
        btn_upload_controller.setOnClickListener(this);
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
                intent_front = new Intent(ControllerViewActivity.this, SmallBluetoothDeviceActivity.class);
                startActivity(intent_front);
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
                break;
            default:
                break;
        }
    }
}
