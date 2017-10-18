package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.activity.ChildShopActivity;
import zhanghegang.com.bawei.date0928jingdong.bean.ChildKindBean;
import zhanghegang.com.bawei.date0928jingdong.bean.KindBean;

/**
 * Created by asus on 2017/9/29.
 */

public class Child_gv_kindAdapter extends RecyclerView.Adapter<Child_gv_kindAdapter.MyViewHolder> {
    private final Context context;
    private final List<ChildKindBean.DataBean.ListBean> list;


    public Child_gv_kindAdapter(Context context, List<ChildKindBean.DataBean.ListBean> list) {
        this.list=list;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=View.inflate(context, R.layout.kind_child_child_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

holder.tv_rcb.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(holder.iv_rcb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ChildShopActivity.class);
                intent.putExtra("cid",list.get(position).getPscid()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_rcb;
        private final TextView tv_rcb;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_rcb = itemView.findViewById(R.id.iv_child_child);
            tv_rcb = itemView.findViewById(R.id.tv_child_kind_childname);
        }
    }
}
