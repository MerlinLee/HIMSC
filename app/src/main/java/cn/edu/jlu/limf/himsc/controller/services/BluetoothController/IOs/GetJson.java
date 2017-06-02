package cn.edu.jlu.limf.himsc.controller.services.BluetoothController.IOs;

/**
 * Created by merlin on 17-4-21.
 */
/**
 * Created by merlin on 17-5-9.
 */
/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-9 下午11:07
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class GetJson {
    public  String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}