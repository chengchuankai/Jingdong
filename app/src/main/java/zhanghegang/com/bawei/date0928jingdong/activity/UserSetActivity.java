package zhanghegang.com.bawei.date0928jingdong.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.UserBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.UserPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.UserView;

public class UserSetActivity extends AppCompatActivity implements UserView {


    @BindView(R.id.iv_cha)
    ImageView ivCha;
    @BindView(R.id.iv_set_icon)
    RoundedImageView ivSetIcon;
    @BindView(R.id.tv_set_nickname)
    TextView tvSetNickname;
    @BindView(R.id.tv_set_username)
    TextView tvSetUsername;
    @BindView(R.id.ll_user_set)
    LinearLayout llUserSet;

    @BindView(R.id.sv_userSet)
    ScrollView svUserSet;
    @BindView(R.id.btn_back_reg)
    Button btnBackReg;
    private UserPresenter userPresenter;
    private SharedPreferences userAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.activity_user_set);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        userPresenter = new UserPresenter(this, this);
        userAll = getSharedPreferences("userAll", MODE_PRIVATE);
        gainUserInfo();

    }

    private void gainUserInfo() {
        String uid = userAll.getString("uid", null);
        if (!TextUtils.isEmpty(uid)) {
            Map<String, String> map = new HashMap<>();
            map.put("uid", uid);
            userPresenter.gainUserInfo(map);
        }

    }

    @OnClick({R.id.iv_cha, R.id.iv_set_icon, R.id.tv_set_nickname, R.id.tv_set_username, R.id.ll_user_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cha:
                finish();
                break;
            case R.id.iv_set_icon:
                break;
            case R.id.tv_set_nickname:
                break;
            case R.id.tv_set_username:
                break;
            case R.id.ll_user_set:
                startActivity(new Intent(this, MyInfoActivity.class));
                break;
        }
    }

    @Override
    public void gainFail() {

    }

    @Override
    public void gainSuc(String data) {

    }

    @Override
    public void loaginFail(String msg) {

    }

    @Override
    public void gainUserInfoFail() {

    }

    @Override
    public void gainUserSuc(String data) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);
        UserBean.DataBean data1 = userBean.getData();
        ivSetIcon.setOval(true);
        Glide.with(this.getApplicationContext())
                .load(data1.getIcon().toString())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.mipmap.ic_launcher_round)
                .placeholder(R.mipmap.ic_launcher_round)
                .dontAnimate()
                .into(ivSetIcon);
        tvSetUsername.setText(data1.getUsername());
        if (data1.getNickname() != null) {
            tvSetNickname.setText(data1.getNickname().toString());
        }
    }

    @Override
    public void gainaddSuc(String data) {

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

    @OnClick(R.id.btn_back_reg)
    public void onViewClicked() {
        AlertDialog.Builder ab=new AlertDialog.Builder(this).setMessage("确认退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userAll.edit().remove("uid").commit();
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        ab.show();

    }
}
