package cn.edu.jlu.limf.himsc.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import cn.edu.jlu.limf.himsc.R;
import cn.edu.jlu.limf.himsc.models.UserRegisterInfoBean;
import cn.edu.jlu.limf.himsc.utils.networking.OkHttpControl;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-5 上午11:49
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
public class RegisterActivity extends AppCompatActivity {
    static String TAG="Merlin";
    private String post_status="ERORR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button_Register = (Button)findViewById(R.id.regist_btn);
        final EditText edit_Text_name = (EditText)findViewById(R.id.register_edit_account);
        final EditText edit_Text_psw = (EditText)findViewById(R.id.register_edit_pwd);
        final EditText edit_Text_accountid = (EditText)findViewById(R.id.register_accountID);
        final EditText edit_Text_address = (EditText)findViewById(R.id.register_address);
        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson_register = new Gson();
                UserRegisterInfoBean userRegisterInfo = new UserRegisterInfoBean();
                userRegisterInfo.setUserName(edit_Text_name.getText().toString());
                userRegisterInfo.setPassword(edit_Text_psw.getText().toString());
                userRegisterInfo.setUserAccountId(edit_Text_accountid.getText().toString());
                userRegisterInfo.setUserAddress(edit_Text_address.getText().toString());
                final String post_json_user_info = gson_register.toJson(userRegisterInfo);
                final String url = "http://49.140.92.170:8080/himsc/register_from_himsc";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpControl post_register = new OkHttpControl();
                        try {
                            post_status = post_register.post(url,post_json_user_info);
                            Log.d(TAG, " 注册成功 "+post_status);
                        } catch (IOException e) {

                            Log.e(TAG, e.toString()+" 注册失败");
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
