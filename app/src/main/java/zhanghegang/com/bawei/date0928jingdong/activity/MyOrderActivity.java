package zhanghegang.com.bawei.date0928jingdong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.adapter.OrderAdapter;
import zhanghegang.com.bawei.date0928jingdong.bean.OrderBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.GetOrderPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.GetOrderView;

public class MyOrderActivity extends AppCompatActivity implements GetOrderView {

    @BindView(R.id.iv_order_find)
    ImageView ivOrderFind;
    @BindView(R.id.iv_order_msg)
    ImageView ivOrderMsg;
    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.rcv_order)
    RecyclerView rcvOrder;
    @BindView(R.id.iv_order_back)
    ImageView ivOrderBack;
    private OrderAdapter orderAdapter;
    private int index;
    private GetOrderPresenter presenter;
    private String uid;
    private SharedPreferences userAll;
    private List<OrderBean.DataBean> data_list;
private int updateId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        tabOrder.addTab(tabOrder.newTab().setText("全部"));
        tabOrder.addTab(tabOrder.newTab().setText("待付款"));
        tabOrder.addTab(tabOrder.newTab().setText("待收货"));
        tabOrder.addTab(tabOrder.newTab().setText("已完成"));
        tabOrder.addTab(tabOrder.newTab().setText("已取消"));
        presenter = new GetOrderPresenter(this,this);
        userAll = getSharedPreferences("userAll", MODE_PRIVATE);
        uid = userAll.getString("uid", null);
        if(!TextUtils.isEmpty(uid))
        {
        Map<String,Object> map=new HashMap<>();
            map.put("uid", uid);
        presenter.createOrder(map);
        }

    }

    @OnClick({R.id.iv_order_find, R.id.iv_order_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_order_find:
                break;
            case R.id.iv_order_msg:
                break;
        }
    }

    @OnClick(R.id.iv_order_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onGetOrderFail(String msg) {

    }

    @Override
    public void onGetOrderSuc(String data) {
        Gson gson=new Gson();
        OrderBean orderBean = gson.fromJson(data, OrderBean.class);
        if(orderBean!=null)
        {
            data_list = orderBean.getData();
            rcvOrder.setLayoutManager(new LinearLayoutManager(this));

            orderAdapter = new OrderAdapter(this, data_list);
            orderAdapter.setBuyStatus(new OrderAdapter.SetBuyListener() {
                @Override
                public void onBuyListener(int pos) {
                    index = pos;
                    Intent intent=new Intent(MyOrderActivity.this,PayActivity.class);

                    startActivityForResult(intent,7777);
                }

                @Override
                public void onCancelListener(int pos) {
                    if(!TextUtils.isEmpty(uid))
                    {
                        index = pos;
                        Map<String,Object> map=new HashMap<>();
                        map.put("uid",uid);
                        updateId=2;
                        map.put("status",updateId);
                        map.put("orderId",data_list.get(index).getOrderid());
                        presenter.updateOrder(map);
                    }
                }
            });
       rcvOrder.setAdapter(orderAdapter);
        }
    }

    @Override
    public void onUpdateOrderFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateOrderSuc(String data) {
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        if(data_list!=null&&data_list.size()>0)
        {
            System.out.println("index==========="+index+"========="+updateId);
            data_list.get(index).setStatus(updateId);
            orderAdapter.setStatus(index,updateId);
        orderAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==7777)
        {

            String buymsg = data.getStringExtra("buymsg");

            if(TextUtils.equals(buymsg, "9000"))
            {
                if(orderAdapter!=null) {


                    if(!TextUtils.isEmpty(uid)&&data_list!=null)
                    {
                        Map<String,Object> map=new HashMap<>();
                        map.put("uid",uid);
                        updateId=1;
                        map.put("status",updateId);
                        map.put("orderId",data_list.get(index).getOrderid());
                        presenter.updateOrder(map);

                    }
                }
            }
        }
    }
}
