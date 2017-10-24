package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.adapter.CarAdapter;
import zhanghegang.com.bawei.date0928jingdong.api.ApiUrl;
import zhanghegang.com.bawei.date0928jingdong.bean.SearchBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.CreateOrderPresenter;
import zhanghegang.com.bawei.date0928jingdong.precenter.SearchPresenter;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpCall;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpMethod;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpUtils;
import zhanghegang.com.bawei.date0928jingdong.utils.SearchCarUtils;
import zhanghegang.com.bawei.date0928jingdong.view.CreateOrderView;
import zhanghegang.com.bawei.date0928jingdong.view.SearchCarView;

/**
 * Created by asus on 2017/9/28.
 */

public class Fragment_shopping extends Fragment implements SearchCarView, CreateOrderView {
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
    @BindView(R.id.sw_car)
    SwipeRefreshLayout swCar;
    private SearchPresenter searchPresenter;
    private List<SearchBean.DataBean> data_list;
    public boolean[] flag;
    private CarAdapter carAdapter;
    private SharedPreferences userAll;
    private CreateOrderPresenter createOrderPresenter;
    private SharedPreferences userAll1;
    private String uid;

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
        userAll = getActivity().getSharedPreferences("userAll", Context.MODE_PRIVATE);
        searchPresenter = new SearchPresenter(getActivity(), this);
        createOrderPresenter = new CreateOrderPresenter(getActivity(), this);
        swCar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                            @Override
                   public void run() {
            // TODO Auto-generated method stub
               searchCarMethod();
                   swCar.setRefreshing(false);
                   }
          }, 3000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        userAll1 = getActivity().getSharedPreferences("userAll", Context.MODE_PRIVATE);
        uid = userAll1.getString("uid", null);
        searchCarMethod();



    }

    private void searchCarMethod() {
        if (!TextUtils.isEmpty(uid)) {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            searchPresenter.searchCar(map);
        }
    }

    @OnClick({R.id.car_all_select, R.id.ll_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.car_all_select:
                updateCarData();
                break;
            case R.id.ll_go_pay:
                if (!TextUtils.isEmpty(uid)) {
                    if (!TextUtils.isEmpty(tvSumPrice.getText().toString())) {
                        System.out.println("sumPrice=========" + tvSumPrice.getText().toString() + "=====sumShop=======" + tvShopNum.getText().toString());
                        Map<String, Object> map = new HashMap<>();
                        map.put("uid", uid);
                        map.put("price", tvSumPrice.getText().toString());
                        createOrderPresenter.createOrder(map);
                    }

                }
                break;
        }
    }

    private void updateCarData() {
        if (carAllSelect.isChecked()) {
            if (carAdapter != null) {
                carAdapter.setAllStatus(1);
               carAdapter.notifyDataSetChanged();
            }


        } else {
            if (carAdapter != null) {
                carAdapter.setAllStatus(2);
                carAdapter.notifyDataSetChanged();
            }

        }


    }


    private void findData() {
        final String uid = userAll.getString("uid", null);
        if (!TextUtils.isEmpty(uid)) {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            OkhttpUtils.getInstance(getActivity()).call(OkhttpMethod.POST, ApiUrl.SEARCHCARURL, map, new OkhttpCall() {
                @Override
                public void onFailure(String e, String msg) {

                }

                @Override
                public void onResponse(final String result) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SearchCarUtils utils = new SearchCarUtils();
                                Map<String, Integer> reSultMap = utils.searchCar(result);
                                if (reSultMap != null && reSultMap.entrySet().size() > 0) {

                                    for (Map.Entry<String, Integer> entry : reSultMap.entrySet()) {
                                        tvSumPrice.setText(entry.getKey());
                                        tvShopNum.setText("（" + entry.getValue() + "）");

                                    }

                                }

                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onSearchCarFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchCarSuc(final String data) {
        if (!TextUtils.isEmpty(data)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SearchCarUtils utils = new SearchCarUtils();
                    Map<String, Integer> reSultMap = utils.searchCar(data);
                    if (reSultMap != null && reSultMap.entrySet().size() > 0) {

                        for (Map.Entry<String, Integer> entry : reSultMap.entrySet()) {
                            tvSumPrice.setText(entry.getKey());
                            tvShopNum.setText("(" + entry.getValue() + ")");

                        }

                    }

                }
            });
            Gson gson = new Gson();
            SearchBean searchBean = gson.fromJson(data, SearchBean.class);
            if (searchBean != null) {
                data_list = searchBean.getData();

                if (data_list.size() > 0) {
                    carAdapter = new CarAdapter(getActivity(), data_list, tvSumPrice, tvShopNum);

                    rcvCar.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rcvCar.setAdapter(carAdapter);

                }
            }
        }

    }

    @Override
    public void onUpdateCarFail(String msg) {

    }

    @Override
    public void onUpdateCarSuc(String data) {

    }

    @Override
    public void onCreateOrderFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOrderSuc(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);


            String msg = jsonObject.getString("msg");
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
