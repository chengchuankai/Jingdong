package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.SearchBean;

/**
 * Created by asus on 2017/10/18.
 */

public class CarAdapter extends RecyclerView.Adapter {
    private final Context contenxt;
    private final List<SearchBean.DataBean> list;

    public CarAdapter(Context context, List<SearchBean.DataBean> list) {
        this.contenxt=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(contenxt, R.layout.car_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final CheckBox cb_parent;
        private final TextView tv;
        private final RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb_parent = itemView.findViewById(R.id.cb_car_item);
            tv = itemView.findViewById(R.id.tv_car_dianpuName);
            recyclerView = itemView.findViewById(R.id.rcv_car);
        }
    }
}
