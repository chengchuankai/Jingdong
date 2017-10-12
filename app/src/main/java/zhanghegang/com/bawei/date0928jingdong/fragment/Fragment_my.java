package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.activity.RegActivity;
import zhanghegang.com.bawei.date0928jingdong.activity.UserSetActivity;
import zhanghegang.com.bawei.date0928jingdong.bean.UserBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.UserPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.UserView;

/**
 * Created by asus on 2017/9/28.
 */

public class Fragment_my extends Fragment implements UserView {

    private static final int REGRESULT = 2222;
    @BindView(R.id.ll_my_reg)
    LinearLayout llMyReg;
    @BindView(R.id.my_user)
    RoundedImageView myUser;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.rl_reg)
    RelativeLayout rlReg;
    private View view;
    private SharedPreferences userAll;
    private UserPresenter up;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fl_my, null);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        userAll = getActivity().getSharedPreferences("userAll", Context.MODE_PRIVATE);
        String uid = userAll.getString("uid", null);
        System.out.println("uid========"+uid);
        if (!TextUtils.isEmpty(uid)) {
            rlReg.setBackgroundResource(R.drawable.reg_bg);
            initData(uid);

        }else
        {
            rlReg.setBackgroundResource(R.drawable.normal_regbg);
            myUser.setImageResource(R.drawable.user);
        }
    }

    private void initData(String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        up = new UserPresenter(this, getActivity());
        up.gainUserInfo(map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();



    }

    @OnClick(R.id.ll_my_reg)
    public void onViewClicked() {
        String uid = userAll.getString("uid", null);
        if(TextUtils.isEmpty(uid)) {
            startActivityForResult(new Intent(getActivity(), RegActivity.class), REGRESULT);
        }
        else
        {
            startActivity(new Intent(getActivity(), UserSetActivity.class));
        }

    }

    @Override
    public void gainFail() {

    }

    @Override
    public void gainSuc(String datas) {


    }

    @Override
    public void loaginFail(String msg) {

    }

    @Override
    public void gainUserInfoFail() {



    }

    @Override
    public void gainUserSuc(String data) {

        System.out.println("fragmentdata=====data===="+data);
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);
        UserBean.DataBean datas = userBean.getData();
        if (!TextUtils.isEmpty(datas.getIcon())) {
            System.out.println("touxiang========="+datas.getIcon().toString());
            myUser.setOval(true);
            Glide.with(getActivity().getApplicationContext())
                    .load(datas.getIcon().toString())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .dontAnimate()
                    .into(myUser);

        }
        if (datas.getNickname() != null) {
            tvNickname.setText(datas.getNickname().toString());
        } else {
            tvNickname.setText("没有昵称，随机了");
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
}
