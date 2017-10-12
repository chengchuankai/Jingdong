package zhanghegang.com.bawei.date0928jingdong.precenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

import zhanghegang.com.bawei.date0928jingdong.model.UserModel;
import zhanghegang.com.bawei.date0928jingdong.view.UserView;

/**
 * Created by asus on 2017/10/11.
 */

public class UserPresenter implements UserModel.ILogin {
    private final UserView userView;
    private final UserModel userModel;
    private final Context context;

    public UserPresenter(UserView userView, Context context) {
        this.userView=userView;
        this.context=context;
        userModel = new UserModel();
        userModel.setIlogin(this);
    }
    public void login(Map<String,String> map){
        userModel.login(map);

    }
    public void gainUserInfo(Map<String,String> map){
        userModel.gainUserInfo(map);
    }
    public void gainaddUser(Map<String,String> map){
        userModel.addUser(map);
    }
public void unload(Map<String,Object> map){
    userModel.unload(map);
}
public void setNickName(Map<String,String> map){
    userModel.setNickName(map);
}
    @Override
    public void gainFail() {

            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userView.gainFail();
                }
            });


    }

    @Override
    public void gainSuc(final String data) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.gainSuc(data);
            }
        });
    }

    @Override
    public void loaginFail(final String msg) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.loaginFail(msg);
            }
        });
    }

    @Override
    public void gainUserInfoFail() {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.gainUserInfoFail();
            }
        });
    }

    @Override
    public void gainUserSuc(final String data) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.gainUserSuc(data);
            }
        });
    }

    @Override
    public void setNickSuc(final String msg) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.setNickSuc(msg);
            }
        });
    }

    @Override
    public void gainaddSuc(final String data) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.gainaddSuc(data);
            }
        });
    }

    @Override
    public void unloadSuc(final String msg) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.unloadSuc(msg);
            }
        });
    }

    @Override
    public void unloadFail(final String msg) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userView.unloadFail(msg);
            }
        });
    }
}
