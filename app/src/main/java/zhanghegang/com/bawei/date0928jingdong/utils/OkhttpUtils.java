package zhanghegang.com.bawei.date0928jingdong.utils;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asus on 2017/10/15.
 */

public class OkhttpUtils {
    public Context context;
    //单例模式，声明
    public static OkhttpUtils okhttpInstanse;

    public OkhttpUtils(Context context) {
        this.context = context;
    }
    /**
     * 提供暴露方法
     *
     */
    public static OkhttpUtils getInstance(Context context){
        if(okhttpInstanse==null)
        {
            synchronized (OkhttpUtils.class){
                if(okhttpInstanse==null)
                {
                    okhttpInstanse=new OkhttpUtils(context);
                }
            }
        }
        return okhttpInstanse;
    }
    public void call(OkhttpMethod okhttpMethod, String url, Map<String,Object> map, final OkhttpCall okhttpCall){
        //创建构造者模式
//        OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
//        okHttpClient.connectTimeout(4, TimeUnit.SECONDS);
//        okHttpClient.readTimeout(4,TimeUnit.SECONDS);
//        okHttpClient.writeTimeout(4,TimeUnit.SECONDS);
        Request request=null;
//        OkHttpClient client = okHttpClient.build();
        OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(new LogInterceptor())

                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(false)
                        .build();
       if(map!=null&&map.entrySet().size()>0) {

               if (okhttpMethod == OkhttpMethod.GET) {
                   String mUrl = url + "?";
                   for (Map.Entry<String, Object> entry : map.entrySet()) {
                   mUrl+=entry.getKey()+"="+entry.getValue()+"&";
                   }
                   request=new Request.Builder().url(mUrl).get().build();
               }
               else if(okhttpMethod==OkhttpMethod.POST)
               {
                   FormBody.Builder formBody=new FormBody.Builder();
                   for (Map.Entry<String, Object> entry : map.entrySet()) {
                   formBody.add(entry.getKey(),entry.getValue().toString());
                   }
                   request=new Request.Builder().url(url).post(formBody.build()).build();
               }

       }
       if(request!=null) {
           client.newCall(request).enqueue(new Callback() {
               @Override
               public void onFailure(Call call, IOException e) {
                   String s = e.toString();
                   okhttpCall.onFailure(s,"请求失败");
               }

               @Override
               public void onResponse(Call call, Response response) throws IOException {
//子线程加载数据
                   final StringBuffer result=new StringBuffer();
                   InputStream inputStream=null;
                   BufferedReader bufferedReader=null;
                   if(response!=null) {
                       inputStream = response.body().byteStream();
                       bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                       String line = "";
                       while ((line = bufferedReader.readLine()) != null) {
                           result.append(line);
                       }
                       ((Activity)context).runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               okhttpCall.onResponse(result.toString());
                           }
                       });
                   }
               }
           });
       }


    }
}
