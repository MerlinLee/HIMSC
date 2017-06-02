package cn.edu.jlu.limf.himsc.controller.services.BluetoothController.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import cn.edu.jlu.limf.himsc.utils.networking.OkHttpControl;

import static android.content.ContentValues.TAG;

/**
 * Created by merlin on 17-5-7.
 */

public class LoginCheckTask extends AsyncTask {
    private String userLoginInfo;
    private String url;
    private String connect_status;

    public String getConnect_status() {
        return connect_status;
    }

    public void setConnect_status(String connect_status) {
        this.connect_status = connect_status;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        OkHttpControl post_login_info = new OkHttpControl();
        Gson gson_str_json=new Gson();
        String json = gson_str_json.toJson(userLoginInfo);
        try {
            connect_status=post_login_info.post(url+"/himsc/loginCheckHIMSC",json);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
