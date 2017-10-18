package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.SearchBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.SearchPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.SearchCarView;

/**
 * Created by asus on 2017/9/28.
 */

public class Fragment_shopping extends Fragment implements SearchCarView {
    @BindView(R.id.tv_etCar)
    TextView tvEtCar;
    @BindView(R.id.iv_car_msg)
    ImageView ivCarMsg;
    @BindView(R.id.rcv_car)
    RecyclerView rcvCar;
    @BindView(R.id.car_all_select)
    CheckBox carAllSelect;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @BindView(R.id.tv_go_pay)
    TextView tvGoPay;
    @BindView(R.id.tv_shopNum)
    TextView tvShopNum;
    @BindView(R.id.ll_go_pay)
    LinearLayout llGoPay;
    private SearchPresenter searchPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fl_car, null);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        searchPresenter = new SearchPresenter(getActivity(),this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences userAll = getActivity().getSharedPreferences("userAll", Context.MODE_PRIVATE);
        String uid = userAll.getString("uid", null);
        if(!TextUtils.isEmpty(uid))
        {
            Map<String,Object> map=new HashMap<>();
            map.put("uid",uid);
            searchPresenter.searchCar(map);
        }


    }

    @OnClick({R.id.car_all_select, R.id.ll_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.car_all_select:
                break;
            case R.id.ll_go_pay:
                break;
        }
    }

    @Override
    public void onSearchCarFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchCarSuc(String data) {
        if(!TextUtils.isEmpty(data))
        {
            Gson gson=new Gson();
            SearchBean searchBean = gson.fromJson(data, SearchBean.class);
            if(searchBean!=null)
            {

            }
        }

    }
}
