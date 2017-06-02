package cn.edu.jlu.limf.himsc.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import cn.edu.jlu.limf.himsc.R;

public class SmallWindowsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_small_windows);
        }catch (Exception e){
            this.finish();
            Log.d("Merlin", e.toString());
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //点击外围，退出窗口
//        this.finish();
//        return true;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击外围，退出窗口
        this.finish();
        return true;
    }

    public void YesButton(View v) {
        this.finish();
        Toast.makeText(getApplicationContext(), "Yes, Hunman is Activity", Toast.LENGTH_LONG).show();
    }

    public void NoButton(View v) {
        this.finish();
        Toast.makeText(getApplicationContext(), "No, Hunman is not Activity", Toast.LENGTH_LONG).show();
    }
}
