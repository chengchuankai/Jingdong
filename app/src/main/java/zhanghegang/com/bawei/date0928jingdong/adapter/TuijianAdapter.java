package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zhanghegang.com.bawei.date0928jingdong.R;
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
       View view=View.inflate(context, R.layout.rcb_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

holder.tv_rcb.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getImages()).into(holder.iv_rcb);
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

            iv_rcb = itemView.findViewById(R.id.iv_rcb_kind);
            tv_rcb = itemView.findViewById(R.id.tv_rcb_kind);
        }
    }
}
