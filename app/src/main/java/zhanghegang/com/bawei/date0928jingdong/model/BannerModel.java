package zhanghegang.com.bawei.date0928jingdong.model;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zhanghegang.com.bawei.date0928jingdong.api.ApiUrl;

/**
 * Created by asus on 2017/9/29.
 */

public class BannerModel {
    private BannerListener banner;

    public void gainImg(){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody.Builder builder=new FormBody.Builder();
        FormBody build = builder.build();
        Request request=new Request.Builder().url(ApiUrl.BANNERURL).post(build).build();
        //子线程访问
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                banner.gainFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bannerData = response.body().string();


                banner.gainSuc(bannerData);
            }
        });
    }
    public void setBanner(BannerListener banner){
        this.banner=banner;
    }
    public interface BannerListener{
        void gainSuc(String data);
        void gainFail();
    }
}
