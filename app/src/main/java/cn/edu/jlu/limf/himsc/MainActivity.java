package cn.edu.jlu.limf.himsc;
/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-1 下午12:13
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.leakcanary.LeakCanary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.edu.jlu.limf.himsc.MyApplication.WholeDataApplication;
import cn.edu.jlu.limf.himsc.controller.services.BluetoothController.AsyncTasks.LoginCheckTask;
import cn.edu.jlu.limf.himsc.models.UserLoginInfoBean;
import cn.edu.jlu.limf.himsc.utils.networking.OkHttpControl;
import cn.edu.jlu.limf.himsc.views.ControllerViewActivity;
import cn.edu.jlu.limf.himsc.views.RegisterActivity;
import cn.edu.jlu.limf.himsc.views.TestActivity;

public class MainActivity extends AppCompatActivity {
    private WholeDataApplication wholeDataApplication;
    private Context context=this;
    static String TAG = "Merlin";
    private Intent intent_test=null;
    private String connect_status="";
    private Button btn_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        LeakCanary.install(this.getApplication());
        // Normal app init code...
        wholeDataApplication=(WholeDataApplication) getApplication();
        Button button_login = (Button)findViewById(R.id.login_btn_login);
        Button button_register= (Button)findViewById(R.id.regist_btn);
        final EditText editText_accont = (EditText)findViewById(R.id.login_edit_account);
        final EditText editText_psw = (EditText)findViewById(R.id.login_edit_pwd);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = editText_accont.getText().toString();
                String userPwd=editText_psw.getText().toString();
                final Map<String,String> map_user = new HashMap<String, String>();
                map_user.put("userName",userName);
                map_user.put("userPsw",userPwd);
//                final UserLoginInfoBean userLoginInfo = new UserLoginInfoBean();
//                userLoginInfo.setUserName(userName);
//                userLoginInfo.setUserPsw(userPwd);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        intent_test = new Intent(MainActivity.this, ControllerViewActivity.class);
//                        startActivity(intent_test);
//                    }
//                }).start();
//                new LoginCheckTask().execute();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpControl post_login_info = new OkHttpControl();
//                        Gson gson_str_json=new Gson();
                        String json = new Gson().toJson(map_user).toString();   //gson_str_json.toJson(userLoginInfo);
                        try {
                            connect_status=post_login_info.post(wholeDataApplication.getURL()+"/himsc/loginCheckHIMSC",json);
                        } catch (IOException e) {
                            Log.e(TAG, e.toString());
                        }
//                        intent_test=new Intent(MainActivity.this, TestActivity.class);
//                        startActivity(intent_test);
//                        Log.d(TAG, userName+" "+userPwd+"这是线程内"+json);
                        if(connect_status.equals("ERORR")){
//                            Toast.makeText(context.getApplicationContext(),"失败",Toast.LENGTH_LONG);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"登录失败",Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.d(TAG, "登录失败" );
                        }else if(connect_status.equals("SUCCESS")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    wholeDataApplication.setUserId(userName);
                                    Toast.makeText(context,"登陆成功,正在跳转...",Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.d(TAG, connect_status);
                            intent_test = new Intent(MainActivity.this, ControllerViewActivity.class);
                            try {
                                startActivity(intent_test);
                            } catch (Exception e) {
                                Log.d(TAG, e.toString());
                            }
                        }
                    }
                }).start();

                Log.d(TAG, userName+" "+userPwd);
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_test=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent_test);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_test=(Button)findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_into_test = new Intent(MainActivity.this,TestActivity.class);
                startActivity(intent_into_test);
            }
        });
    }
}
