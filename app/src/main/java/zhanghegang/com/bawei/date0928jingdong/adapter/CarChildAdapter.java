package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.auto_view.AdPonitView;
import zhanghegang.com.bawei.date0928jingdong.bean.SearchBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.DeletePresenter;
import zhanghegang.com.bawei.date0928jingdong.precenter.SearchPresenter;
import zhanghegang.com.bawei.date0928jingdong.utils.SearchCarUtils;
import zhanghegang.com.bawei.date0928jingdong.view.DeleteCarView;
import zhanghegang.com.bawei.date0928jingdong.view.SearchCarView;

/**
 * Created by asus on 2017/10/18.
 */

public class CarChildAdapter extends RecyclerView.Adapter<CarChildAdapter.MyViewHolder> implements SearchCarView, DeleteCarView {
    private final Context contenxt;
    private final List<SearchBean.DataBean.ListBean> list;
    private final TextView tv_sumPrice;
    private final TextView tv_go_pay;
    private SharedPreferences userAll;
    private MyViewHolder myViewHolder;
private int index;
    private OnsetShopNum onsetShopNum;
    private List<Boolean> booleList;
    private int flag;
    private SearchPresenter searchPresenter;
    private String uid;
    private OnItemLongListenter onItemLongClik;
    private final DeletePresenter deletePresenter;
private int deleteNum=0;
    public CarChildAdapter(Context context, List<SearchBean.DataBean.ListBean> list,TextView tv_sumPrice,TextView tv_go_pay) {
        this.contenxt=context;
        this.list=list;
        this.tv_sumPrice=tv_sumPrice;
        this.tv_go_pay=tv_go_pay;
        flag=0;
        booleList=new ArrayList<>();
        deletePresenter = new DeletePresenter(context,this);
        for (int i = 0; i <list.size() ; i++) {
            if(1==list.get(i).getSelected())
            {
                booleList.add(true);
            }
            else {
                booleList.add(false);
            }
        }
    }
    public void setCheckBox(int flag){
        this.flag=flag;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(contenxt, R.layout.car_child_item,null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    public void setLongClikListenter(OnItemLongListenter onItemLongListenter){
        this.onItemLongClik=onItemLongListenter;
    }

    @Override
    public void onDeleteCarFail(String msg) {

    }

    @Override
    public void onDeleteCarSuc(String data) {

    }

    public interface OnItemLongListenter{
    void onItemLongClik(int pos);
}
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //长按删除
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(contenxt);
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!TextUtils.isEmpty(uid)) {
                            deleteNum++;
                            Map<String, Object> map = new HashMap<>();
                            map.put("uid", uid);
                            map.put("pid", list.get(holder.getLayoutPosition()).getPid());
                            deletePresenter.deleteCar(map);
                            onItemLongClik.onItemLongClik(holder.getLayoutPosition());
                        }

                    }
                });
                ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                ab.setTitle("是否删除此商品");
                ab.show();
                return false;
            }
        });
        userAll = contenxt.getSharedPreferences("userAll", Context.MODE_PRIVATE);
        searchPresenter = new SearchPresenter(contenxt,this);
        uid = userAll.getString("uid", null);
        final SearchBean.DataBean.ListBean listBean = list.get(position);


       holder.tv_title.setText(listBean.getTitle());
        String images = listBean.getImages();
        String[] split = images.split("\\|");



        if(split.length>0) {
            Glide.with(contenxt).load(split[0]).into(holder.iv_car);
        }
        holder.tv_price.setText("¥ "+listBean.getBargainPrice()+"");
//判断是否点击商家多选框
        if(flag==1)
        {
            holder.cb_child.setChecked(true);
            booleList.set(holder.getLayoutPosition(),true);
            Map<String,Object> map=new HashMap<>();
            if(!TextUtils.isEmpty(uid)) {
                map.put("uid", uid);
                map.put("sellerid",listBean.getSellerid());
                map.put("pid",listBean.getPid());
                map.put("num",listBean.getNum());
                map.put("selected","1");

            }

            listBean.setSelected(1);
            searchPresenter.updateCar(map);
        }
        else if(flag==2){

            holder.cb_child.setChecked(false);
            booleList.set(holder.getLayoutPosition(),false);
            Map<String,Object> map=new HashMap<>();
            if(!TextUtils.isEmpty(uid)) {
                map.put("uid", uid);
                map.put("sellerid",listBean.getSellerid());
                map.put("pid",listBean.getPid());
                map.put("num",listBean.getNum());
                map.put("selected","0");

            }
            searchPresenter.updateCar(map);
            listBean.setSelected(0);
        }
        else {
            if(listBean.getSelected()==0)
            {
                holder.cb_child.setChecked(false);
                booleList.set(holder.getLayoutPosition(),false);
            }
            else {
                holder.cb_child.setChecked(true);
                booleList.set(holder.getLayoutPosition(),true);
            }

        }
        //设置选中状态
        holder.cb_child.setOnCheckedChangeListener(null);
        holder.cb_child.setChecked(booleList.get(position));

        //设置多选框点击事件
        holder.cb_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Map<String,Object> map;

                if(b)
                {

                    map=new HashMap<>();
                    if(!TextUtils.isEmpty(uid)) {
                        map.put("uid", uid);
                        map.put("sellerid",listBean.getSellerid());
                        map.put("pid",listBean.getPid());
                        map.put("num",listBean.getNum());
                        map.put("selected","1");

                    }
                    listBean.setSelected(1);

                }
                else
                {
                   map=new HashMap<>();
                    if(!TextUtils.isEmpty(uid)) {
                        map.put("uid", uid);
                        map.put("sellerid",listBean.getSellerid());
                        map.put("pid",listBean.getPid());
                        map.put("num",listBean.getNum());
                        map.put("selected","0");
                    }
                    listBean.setSelected(0);
                }

                booleList.set(holder.getLayoutPosition(),b);
                onsetShopNum.shopNum(map,holder.getLayoutPosition(),booleList);
                searchPresenter.updateCar(map);

            }
        });
holder.car_point.setAmountNum(listBean.getNum());



        holder.car_point.setOnAmountChangeListener(new AdPonitView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
               int position = holder.getLayoutPosition();

                SearchBean.DataBean.ListBean bean = list.get(position);
                Map<String,Object> map=new HashMap<>();
                if(!TextUtils.isEmpty(uid)) {
                    map.put("uid", uid);
                    map.put("sellerid",bean.getSellerid());
                    map.put("pid",bean.getPid());
                    map.put("num",amount);
                    map.put("selected","1");
//                    onsetShopNum.shopNum(map,position);
                    holder.cb_child.setChecked(true);
          booleList.set(holder.getLayoutPosition(),true);
                   searchPresenter.updateCar(map);
                    listBean.setNum(amount);
                    listBean.setSelected(1);
                }

            }
        });
    }
    public void setShopNum(OnsetShopNum onsetShopNum){
        this.onsetShopNum=onsetShopNum;
    }
public interface OnsetShopNum{
    void shopNum(Map<String,Object> map,int pos,List<Boolean> booleaList);
}

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onSearchCarFail(String msg) {

    }

    @Override
    public void onSearchCarSuc(final String data) {
        final SearchCarUtils utils=new SearchCarUtils();
        ((Activity)contenxt).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Map<String, Integer> stringIntegerMap = utils.searchCar(data);
                for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {
                    tv_sumPrice.setText(entry.getKey());
                    tv_go_pay.setText("（"+entry.getValue()+")");
                }
            }
        });


    }

    @Override
    public void onUpdateCarFail(String msg) {
        Toast.makeText(contenxt, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateCarSuc(String data) {

        if(!TextUtils.isEmpty(uid))
        {
            Map<String,Object> map=new HashMap<>();
            map.put("uid",uid);
            searchPresenter.searchCar(map);
        }

        Toast.makeText(contenxt, "请求成功", Toast.LENGTH_SHORT).show();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final CheckBox cb_child;
        private final TextView tv_title;
        private final AdPonitView car_point;
        private final TextView tv_price;
        private final ImageView iv_car;


        public MyViewHolder(View itemView) {
            super(itemView);
            iv_car = itemView.findViewById(R.id.iv_car_child);
            cb_child = itemView.findViewById(R.id.cb_car_child);
            tv_title = itemView.findViewById(R.id.tv_car_child_title);
            car_point = itemView.findViewById(R.id.car_point);
            tv_price = itemView.findViewById(R.id.tv_car_price);
        }
    }
}
