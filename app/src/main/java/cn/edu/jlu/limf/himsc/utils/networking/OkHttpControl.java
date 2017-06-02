package cn.edu.jlu.limf.himsc.utils.networking;

/**
 * Created by merlin on 17-5-3.
 */

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-3 下午12:13
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/
/**
 *@版本:1.1
 *@作者: 李铭峰-吉林大学-软件学院13级02班
 *@日期: 17-5-4 下午3:09
 *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
*/

public class OkHttpControl {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
