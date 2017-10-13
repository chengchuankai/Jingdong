package zhanghegang.com.bawei.date0928jingdong.model;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zhanghegang.com.bawei.date0928jingdong.api.ApiUrl;
import zhanghegang.com.bawei.date0928jingdong.bean.ChildKindBean;
import zhanghegang.com.bawei.date0928jingdong.bean.ShopDetailBean;

/**
 * Created by asus on 2017/9/29.
 */

public class ShopDetailModel {


    private ShopListener shop;

public void gainShopDetail(String pid){

    OkHttpClient okHttpClient=new OkHttpClient();
    FormBody.Builder builder=new FormBody.Builder();
    builder.add("pid",pid);
    FormBody build = builder.build();
    Request request=new Request.Builder().url(ApiUrl.SHOPDETAIL).post(build).build();
    okHttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            shop.gainFail();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String string = response.body().string();

            if(string!=null)
            {
                Gson gson=new Gson();
                ShopDetailBean shopDetailBean = gson.fromJson(string, ShopDetailBean.class);
                if("0".equals(shopDetailBean.getCode()))
                {
                    shop.gainSuc(string);
                }

            }

        }
    });

}
    public void setShop(ShopListener child){
        this.shop=child;
    }
    public interface ShopListener{
        void gainSuc(String data);
        void gainFail();

    }
}
