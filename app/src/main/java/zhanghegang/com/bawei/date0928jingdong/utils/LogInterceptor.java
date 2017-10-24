package zhanghegang.com.bawei.date0928jingdong.utils;

import android.util.Log;

import java.io.IOException;
import java.util.Currency;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by asus on 2017/10/19.
 */

public class LogInterceptor implements Interceptor {
    public static String TAG="LogIntercepter我请求的数据";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        //当前的时间
        long startTime = System.currentTimeMillis();
        Response response=chain.proceed(request);
        //请求后时间
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        MediaType mediaType = response.body().contentType();
        //请求的数据
        String content = response.body().string();
        Log.d(TAG,"\n");
        Log.d(TAG,"----------网络数据----------------");
        Log.d(TAG, "| "+request.toString());
        //判断方法
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Log.d(TAG, "| RequestParams:{"+sb.toString()+"}");
            }
        }
        Log.d(TAG, "| Response:" + content);
        Log.d(TAG,"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();

    }
}
