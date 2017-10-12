package zhanghegang.com.bawei.date0928jingdong.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.UserBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.UserPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.UserView;

public class RegActivity extends AppCompatActivity implements UserView {

    @BindView(R.id.iv_cha)
    ImageView ivCha;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.btn_reg)
    Button btnReg;
    @BindView(R.id.tv_my_add)
    TextView tvMyAdd;
    @BindView(R.id.tv_my_forget_pass)
    TextView tvMyForgetPass;
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.iv_qq)
    ImageView ivQq;
    private UserPresenter userPresenter;
    private SharedPreferences login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userPresenter = new UserPresenter(this,this);
        login = getSharedPreferences("userAll", MODE_PRIVATE);
    }

    @OnClick({R.id.iv_cha, R.id.btn_reg, R.id.tv_my_add, R.id.tv_my_forget_pass, R.id.iv_weixin, R.id.iv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cha:
                finish();
                break;
            case R.id.btn_reg:
                submitUserInfo();

                break;
            case R.id.tv_my_add:
                addUser();
                break;
            case R.id.tv_my_forget_pass:
                break;
            case R.id.iv_weixin:
                break;
            case R.id.iv_qq:
                break;
        }
    }

    private void addUser() {
        Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
        Map<String,String> map=new HashMap<>();
        //获得输入内容
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();
        map.put("mobile",user);
        map.put("password",pass);
        userPresenter.gainaddUser(map);
    }

    private void submitUserInfo() {

        Map<String,String> map=new HashMap<>();
        //获得输入内容
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();
        map.put("mobile",user);
        map.put("password",pass);
       userPresenter.login(map);

    }

    @Override
    public void gainFail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gainSuc(String data) {
        System.out.println("mydata+++++======"+data);
        Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show();

            Gson gson = new Gson();
            UserBean userBean = gson.fromJson(data, UserBean.class);
            UserBean.DataBean result = userBean.getData();

        login.edit().putString("uid",result.getUid()+"").commit();
        finish();
    }

    @Override
    public void loaginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gainUserInfoFail() {

    }

    @Override
    public void gainUserSuc(String data) {

    }

    @Override
    public void gainaddSuc(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unloadSuc(String msg) {

    }

    @Override
    public void unloadFail(String msg) {

    }

    @Override
    public void setNickSuc(String msg) {

    }
}
