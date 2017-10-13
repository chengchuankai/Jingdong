package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.activity.ShopActivity;
import zhanghegang.com.bawei.date0928jingdong.bean.BannerBean;
import zhanghegang.com.bawei.date0928jingdong.bean.KindBean;

/**
 * Created by asus on 2017/9/29.
 */

public class TuijianAdapter extends RecyclerView.Adapter<TuijianAdapter.MyViewHolder> {
    private final Context context;
    private final List<BannerBean.TuijianBean.ListBean> list;


    public TuijianAdapter(Context context, List<BannerBean.TuijianBean.ListBean> list) {
        this.list=list;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=View.inflate(context, R.layout.tuijian_rcv_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

holder.tv_title_rcb.setText(list.get(position).getTitle());
        holder.tv_rcv_price.setText("¥"+list.get(position).getPrice());
        String images = list.get(position).getImages();
        String[] split = images.split("\\|");

        Glide.with(context).load(split[0]).into(holder.iv_rcb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShopActivity.class);
                intent.putExtra("pid",list.get(position).getPid()+"");

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
        private final TextView tv_title_rcb;
        private final TextView tv_rcv_price;
        private final Button btn_like;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_rcb = itemView.findViewById(R.id.iv_tuijian_rcv);
            tv_title_rcb = itemView.findViewById(R.id.tv_tuijian_title);
            tv_rcv_price = itemView.findViewById(R.id.tv_tuijian_price);
            btn_like = itemView.findViewById(R.id.btn_like);
        }
    }
}
