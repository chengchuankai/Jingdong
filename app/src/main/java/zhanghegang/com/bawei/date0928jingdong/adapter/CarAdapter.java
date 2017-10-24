package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.SearchBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.SearchPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.SearchCarView;

/**
 * Created by asus on 2017/10/18.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> implements SearchCarView {
    private final Context contenxt;
    private final List<SearchBean.DataBean> list;
    private final TextView tv_sumPrice;
    private final TextView tv_go_pay;
    private SearchPresenter searchPresenter;
private int allCheck=0;
   private double sumPriceDP=0;
    private int change;
    boolean[] flag;


    public CarAdapter(Context context, List<SearchBean.DataBean> list,TextView tv_sumPrice,TextView tv_go_pay) {
        this.contenxt=context;
        this.list=list;
        this.tv_sumPrice=tv_sumPrice;
        this.tv_go_pay=tv_go_pay;


        flag=new boolean[list.size()];
        //判断是否商家商品全选
        for (int i = 0; i <list.size() ; i++) {
            int posi=0;
           flag[i]=false;
            for (int j = 0; j <list.get(i).getList().size() ; j++) {
                SearchBean.DataBean.ListBean listBean = list.get(i).getList().get(j);
                if(listBean.getSelected()==1)
                {
                    posi++;
                    if(posi==list.get(i).getList().size())
                    {
                        flag[i]=true;
                    }

                }
            }
        }

    }
public void setAllStatus(int change){
    this.change=change;
}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(contenxt, R.layout.car_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SearchBean.DataBean dataBean = list.get(position);
        holder.tv.setText(dataBean.getSellerName());
        holder.cb_parent.setOnCheckedChangeListener(null);
        //获得uid
        SharedPreferences userAll = contenxt.getSharedPreferences("userAll", Context.MODE_PRIVATE);
        final String uid = userAll.getString("uid", null);
        searchPresenter = new SearchPresenter(contenxt,this);
        //判断是否勾选全选
        if(change==1)
        {
allCheck=1;
            flag[position]=true;
            for (int i = 0; i <dataBean.getList().size() ; i++) {
                SearchBean.DataBean.ListBean listBean = dataBean.getList().get(i);
                listBean.setSelected(1);

                Map<String,Object> map=new HashMap<>();
                if(!TextUtils.isEmpty(uid)) {
                    map.put("uid",uid);
                    map.put("sellerid",listBean.getSellerid());
                    map.put("pid",listBean.getPid());
                    map.put("num",listBean.getNum());
                    map.put("selected","1");
                  searchPresenter.updateCar(map);
                }
            }
        }
        else if(change==2){
            allCheck=2;
            flag[position]=false;
            for (int i = 0; i <dataBean.getList().size() ; i++) {
                SearchBean.DataBean.ListBean listBean = dataBean.getList().get(i);
                dataBean.getList().get(i).setSelected(0);
                Map<String,Object> map=new HashMap<>();
                if(!TextUtils.isEmpty(uid)) {
                    map.put("uid",uid);
                    map.put("sellerid",listBean.getSellerid());
                    map.put("pid",listBean.getPid());
                    map.put("num",listBean.getNum());
                    map.put("selected","0");
                    searchPresenter.updateCar(map);
                }
            }
        }
        holder.cb_parent.setChecked(flag[position]);
if(dataBean.getList().size()>0)
{
    holder.recyclerView.setLayoutManager(new LinearLayoutManager(contenxt));
    final CarChildAdapter carChildAdapter=new CarChildAdapter(contenxt,dataBean.getList(),tv_sumPrice,tv_go_pay);

    carChildAdapter.setShopNum(new CarChildAdapter.OnsetShopNum() {
        @Override
        public void shopNum(Map<String, Object> map, int pos,List<Boolean> booleaList) {
            if(map!=null&&map.entrySet().size()>0)
            {
                int allP=0;
                double sumPrice=0;

                for (int i = 0; i <booleaList.size() ; i++) {

                    if(booleaList.get(i)){
                        allP++;
                        String bargainPrice = dataBean.getList().get(i).getBargainPrice();
                        if(!TextUtils.isEmpty(bargainPrice)){
                            double v = Double.parseDouble(bargainPrice);
                           sumPrice+=v;
                        }
                    }
                }
                if(allP==dataBean.getList().size())
                {
                    holder.cb_parent.setChecked(true);
                }
                else {
                    holder.cb_parent.setChecked(false);
//                    tv_sumPrice.setText("¥  "+sumPrice);
                }

            }
        }
    });
    //设置价格

    holder.cb_parent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            double partPrice=0;
            flag[position]=b;

        }
    });
   holder.cb_parent.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           if(holder.cb_parent.isChecked())
           {
               flag[position]=true;
               holder.cb_parent.setChecked(flag[position]);
               carChildAdapter.setCheckBox(1);

           }
           else {
               flag[position]=false;
               holder.cb_parent.setChecked(flag[position]);
               carChildAdapter.setCheckBox(2);
           }
           holder.recyclerView.setAdapter(carChildAdapter);

       }
   });
if(allCheck==1)
{
    carChildAdapter.setCheckBox(1);
}
else if(allCheck==2)
{
    carChildAdapter.setCheckBox(2);
}
carChildAdapter.setLongClikListenter(new CarChildAdapter.OnItemLongListenter() {
    @Override
    public void onItemLongClik(int pos) {

        dataBean.getList().remove(pos);
        notifyDataSetChanged();
        if(dataBean.getList().size()==0)
        {
            list.remove(holder.getLayoutPosition());
            notifyDataSetChanged();
        }
    }
});

    holder.recyclerView.setAdapter(carChildAdapter);
}
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onSearchCarFail(String msg) {

    }

    @Override
    public void onSearchCarSuc(String data) {

    }

    @Override
    public void onUpdateCarFail(String msg) {

    }

    @Override
    public void onUpdateCarSuc(String data) {
        Toast.makeText(contenxt, "请求成功", Toast.LENGTH_SHORT).show();

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final CheckBox cb_parent;
        private final TextView tv;
        private final RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb_parent = itemView.findViewById(R.id.cb_car_item);
            tv = itemView.findViewById(R.id.tv_car_dianpuName);
            recyclerView = itemView.findViewById(R.id.rcv_car_item);
        }
    }
}
