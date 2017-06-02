package cn.edu.jlu.limf.himsc.controller.services.BluetoothController;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.OutputStream;
import cn.edu.jlu.limf.himsc.R;
import cn.edu.jlu.limf.himsc.controller.HealthExceptionAlarm.AlarmModuleController;
import cn.edu.jlu.limf.himsc.controller.services.BluetoothController.IOs.GetJson;
import cn.edu.jlu.limf.himsc.models.HealthRecordBean;
import cn.edu.jlu.limf.himsc.views.ControllerViewActivity;
import cn.edu.jlu.limf.himsc.views.SmallBluetoothDeviceActivity;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-7 下午4:10
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-10 下午3:54
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
 * @项目进度更新: 成功!!
*/
public class BluetoothMainController extends Service {
    private static final String TAG = "Merlin";
    private final String NAME = "Bluetooth_Socket";
    // 获取到选中设备的客户端串口，全局变量，否则连接在方法执行完就结束了
    private BluetoothSocket clientSocket;
    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    private OutputStream os;
    // UUID，蓝牙建立链接需要的
    private final UUID MY_UUID = UUID
            .fromString("db764ac8-4b08-7f25-aafe-59d03c27bae3");
    private static GetJson getJson ;
    private HealthRecordBean healthRecordBean;
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    // 服务端利用线程不断接受客户端信息
    private AcceptThread thread;

    private Gson gson;
    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    private ArrayAdapter<String> mConversationArrayAdapter;
    private AlarmModuleController alarmModuleController;
    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;


    public BluetoothMainController() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gson=new Gson();
        getJson = new GetJson();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
        // 实例接收客户端传过来的数据线程
        thread = new AcceptThread();
        // 线程开始
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        try {
            connectDevice(intent,true);
        }catch (Exception e){
            Log.d(TAG, "onStartCommand: "+e.toString());
        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(SmallBluetoothDeviceActivity.EXTRA_DEVICE_ADDRESS);
        Log.d(TAG, "connectDevice: "+address);
        // Get the BluetoothDevice object

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // 这里需要try catch一下，以防异常抛出
        try {
            // 判断客户端接口是否为空
            if (clientSocket == null) {
                // 获取到客户端接口
                clientSocket = device
                        .createRfcommSocketToServiceRecord(MY_UUID);
                // 向服务端发送连接
                clientSocket.connect();
                // 获取到输出流，向外写数据
                os = clientSocket.getOutputStream();

            }
            // 判断是否拿到输出流
            if (os != null) {
                // 需要发送的信息
//                String text_1 = "你好李铭峰你好李铭峰你好李铭峰你好李铭峰你好李铭峰你好李铭峰你好李铭峰你好李铭峰";
                InputStream inputStream = getResources().openRawResource(R.raw.health_1);
                String text_1 = getJson.getString(inputStream);
                Log.d(TAG, text_1);
                // 以utf-8的格式发送出去
                os.write(text_1.getBytes("UTF-8"));
            }
            // 吐司一下，告诉用户发送成功
            Log.d(TAG, "connectDevice: "+"信息发送成功");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // 如果发生异常则告诉用户发送失败
            Log.d(TAG, "connectDevice: "+"信息发送失败"+" "+e.toString());
        }
        // Attempt to connect to the device
        Log.d(TAG, "connectDevice: "+device.getName()+" "+device.getUuids());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    Log.d(TAG, "onActivityResult: RESULT_OK");
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                }
        }
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    // 服务端接收信息线程
    private class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;// 服务端接口
        private BluetoothSocket socket;// 获取到客户端的接口
        private InputStream is;// 获取到输入流
        private OutputStream os;// 获取到输出流

        public AcceptThread() {
            try {
                // 通过UUID监听请求，然后获取到对应的服务端接口
                serverSocket = mBluetoothAdapter
                        .listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        public void run() {
            try {
                // 接收其客户端的接口
                socket = serverSocket.accept();
                // 获取到输入流
                is = socket.getInputStream();
                // 获取到输出流
                os = socket.getOutputStream();
                // 无线循环来接收数据
                while (true) {
                    // 创建一个1024字节的缓冲
                    byte[] buffer = new byte[1024];
                        // 每次读取128字节，并保存其读取的角标
                    int count = is.read(buffer);
//                    Log.d(TAG, "run: "+"文件接收了" + (passedlen * 100 / len) + "%");
                    // 创建Message类，向handler发送数据
                    final Message msg = new Message();
                    // 发送一个String的数据，让他向上转型为obj类型
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    // 发送数据
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            try {
                healthRecordBean = gson.fromJson(msg.obj.toString(), HealthRecordBean.class);
                alarmModuleController = new AlarmModuleController(healthRecordBean);
                alarmModuleController.isException();
            }catch (Exception e){
                Log.d(TAG, "handleMessage: "+e.toString());
            }
            // 通过msg传递过来的信息，吐司一下收到的信息
            Log.d(TAG, "handleMessage: "+msg.obj.toString());
        }
    };

}
