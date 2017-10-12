package zhanghegang.com.bawei.date0928jingdong.model;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zhanghegang.com.bawei.date0928jingdong.api.ApiUrl;
import zhanghegang.com.bawei.date0928jingdong.bean.UserBean;

/**
 * Created by asus on 2017/10/11.
 */

public class UserModel {
    private ILogin ilogin;

    public void login(Map<String,String> map){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody.Builder formBody=new FormBody.Builder();
        //添加参数
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(),entry.getValue());
        }


        Request request=new Request.Builder().post(formBody.build()).url(ApiUrl.USERLOGIN).build();
        //请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.gainFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if(!TextUtils.isEmpty(string)) {
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(string, UserBean.class);

                            if("0".equals( userBean.getCode()))
                            {
                                ilogin.gainSuc(string);
                            }
                            else
                            {
                                ilogin.loaginFail("登录有误");
                            }
                }

            }
        });

    }
    public void unload(Map<String,Object> map){
        OkHttpClient okHttpClient=new OkHttpClient();
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() instanceof File)
            {
                builder.addFormDataPart(entry.getKey(),((File) entry.getValue()).getName(), RequestBody.create(MediaType.parse("image/*"),(File) entry.getValue()));

            }
            else
            {
                builder.addFormDataPart(entry.getKey(),entry.getValue().toString());
            }
        }
        Request request=new Request.Builder().post(builder.build()).url(ApiUrl.USERUNLOAD).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.unloadFail("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if(!TextUtils.isEmpty(string))
                {
                    ilogin.unloadSuc("上传成功");
                }
            }
        });

    }
    public void gainUserInfo(Map<String,String> map){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody.Builder formBody=new FormBody.Builder();
        //添加参数
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(),entry.getValue());
        }


        Request request=new Request.Builder().post(formBody.build()).url(ApiUrl.USERINFO).build();
        //请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.gainUserInfoFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                if(!TextUtils.isEmpty(string)) {
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(string, UserBean.class);

                    if("0".equals( userBean.getCode()))
                    {
                        ilogin.gainUserSuc(string);
                    }
                    else
                    {
                        ilogin.loaginFail("登录有误");
                    }
                }

            }
        });

    }
    public void addUser(Map<String,String> map){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody.Builder formBody=new FormBody.Builder();
        //添加参数
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(),entry.getValue());
        }


        Request request=new Request.Builder().post(formBody.build()).url(ApiUrl.USERADD).build();
        //请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.gainFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                if(!TextUtils.isEmpty(string)) {
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(string, UserBean.class);
                    String msg = userBean.getMsg();
                    System.out.println("add======"+msg);
                    ilogin.gainaddSuc(msg);
                }

            }
        });

    }
    public void setIlogin(ILogin ilogin){
        this.ilogin=ilogin;
    }
    public void setNickName(Map<String,String> map){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody.Builder formBody=new FormBody.Builder();
        //添加参数
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(),entry.getValue());
        }


        Request request=new Request.Builder().post(formBody.build()).url(ApiUrl.USERKITNAME).build();
        //请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.gainFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                if(!TextUtils.isEmpty(string)) {


                        ilogin.setNickSuc("修改成功");


                }

            }
        });

    }
    public interface ILogin{
        void gainFail();
        void gainSuc(String data);
        void loaginFail(String msg);
        void gainUserInfoFail();
        void gainUserSuc(String data);
void setNickSuc(String msg);
        void gainaddSuc(String data);
        void unloadSuc(String msg);
        void unloadFail(String msg);
    }
}
