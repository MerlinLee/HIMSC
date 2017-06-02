package cn.edu.jlu.limf.himsc.views;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cn.edu.jlu.limf.himsc.R;
import cn.edu.jlu.limf.himsc.controller.services.AlarmService;
import cn.edu.jlu.limf.himsc.controller.services.MyServiceTest;
import cn.edu.jlu.limf.himsc.controller.services.MyServiceTest2;
import cn.edu.jlu.limf.himsc.utils.bluetooth.adapter.LeDeviceListAdapter;
import cn.edu.jlu.limf.himsc.views.tests.TestBLEActivity;
/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-6 上午10:23
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
public class TestActivity extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mLEScanner;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private Button btn_open_bluetooth;
    private boolean mScanning;
    private Handler mHandler;
    private BluetoothGatt mGatt;
    private int DoubleCLick = 1;
    private int DoubleCLick_test2 = 1;
    private int DoubleCLick_test3 = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    static String TAG = "Merlin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btn_start_timer = (Button)findViewById(R.id.btn_start_timer);
        btn_start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: "+"运行线程");
                    }
                }).start();
                Intent intent = new Intent(TestActivity.this, AlarmService.class);
                if(DoubleCLick_test3==1){
                    DoubleCLick_test3=2;
                    startService(intent);
                }else {
                    DoubleCLick_test3=1;
                    stopService(intent);
                }
            }
        });
        Button btn_open_test2=(Button)findViewById(R.id.btn_test1);
        btn_open_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, MyServiceTest2.class);
                if(DoubleCLick_test2==1){
                    startService(intent);
                    DoubleCLick_test2=2;
                }else {
                    stopService(intent);
                    DoubleCLick_test2=1;
                }
            }
        });
        Button btn_open_service = (Button)findViewById(R.id.btn_open_service);
        btn_open_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, MyServiceTest.class);
                if(DoubleCLick==1){
                startService(intent);
                    DoubleCLick=2;
                }else {
                    stopService(intent);
                    DoubleCLick=1;
                }
            }
        });
        Button btn_open_new_activity = (Button)findViewById(R.id.btn_open_new_activity);
        btn_open_new_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, TestBLEActivity.class);
                startActivity(intent);
            }
        });

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        btn_open_bluetooth=(Button)findViewById(R.id.btn_open_bluetooth);
        btn_open_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ScanCallback mScanCallback = new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        Log.i("callbackType", String.valueOf(callbackType));
                        Log.i("result", result.toString());
                        BluetoothDevice btDevice = result.getDevice();
//                        connectToDevice(btDevice);
                    }

                    @Override
                    public void onBatchScanResults(List<ScanResult> results) {
                        for (ScanResult sr : results) {
                            Log.i("ScanResult - Results", sr.toString());
                        }
                    }

                    @Override
                    public void onScanFailed(int errorCode) {
                        Log.e("Scan Failed", "Error Code: " + errorCode);
                    }
                };
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                    Log.e(TAG, "不支持低功耗蓝牙");
                    finish();
                }else {
                    // Ensures Bluetooth is available on the device and it is enabled. If not,
// displays a dialog requesting user permission to enable Bluetooth.
//                    mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//                    mBluetoothAdapter.disable();
                    if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mScanning = false;
                                mLEScanner.stopScan(mScanCallback);
                                Log.d(TAG, "postDelayed");
                            }
                        },SCAN_PERIOD);
                        mScanning = true;
                        mBluetoothAdapter.startDiscovery();
                        while(mBluetoothAdapter.isDiscovering()){
                            Log.d(TAG, mBluetoothAdapter.getName()+" "+mBluetoothAdapter.getAddress());
                        }
                    }else {
                        mScanning = false;
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    Log.d(TAG, "支持低功耗蓝牙");
                    finish();
                }

            }
        });
    }

}
