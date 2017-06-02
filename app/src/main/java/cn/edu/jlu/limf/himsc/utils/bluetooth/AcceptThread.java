package cn.edu.jlu.limf.himsc.utils.bluetooth;

/**
 * Created by merlin on 17-5-5.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-5 下午2:09
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
// 服务端接收信息线程
public final class AcceptThread extends Thread {
    private BluetoothServerSocket serverSocket;// 服务端接口
    private BluetoothSocket socket;// 获取到客户端的接口
    private InputStream is;// 获取到输入流
    private OutputStream os;// 获取到输出流

    public AcceptThread(BluetoothAdapter mBluetoothAdapter, String NAME, UUID MY_UUID) {
        try {
            // 通过UUID监听请求，然后获取到对应的服务端接口
            serverSocket = mBluetoothAdapter
                    .listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void run(Handler handler) {
        try {
            // 接收其客户端的接口
            socket = serverSocket.accept();
            // 获取到输入流
            is = socket.getInputStream();
            // 获取到输出流
            os = socket.getOutputStream();

            // 无线循环来接收数据
            while (true) {
                // 创建一个128字节的缓冲
                byte[] buffer = new byte[128];
                // 每次读取128字节，并保存其读取的角标
                int count = is.read(buffer);
                // 创建Message类，向handler发送数据
                Message msg = new Message();
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
