package cn.edu.jlu.limf.himsc.views.tests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.jlu.limf.himsc.R;

public class TestSmallActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_small);
        button=(Button)findViewById(R.id.button_test);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_test:
                break;
            default:
                break;
        }
    }
}
