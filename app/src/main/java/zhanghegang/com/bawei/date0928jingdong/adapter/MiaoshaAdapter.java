package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.graphics.Paint;
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

public class MiaoshaAdapter extends RecyclerView.Adapter<MiaoshaAdapter.MyViewHolder> {
    private final Context context;
    private final List<BannerBean.MiaoshaBean.ListBeanX> list;


    public MiaoshaAdapter(Context context, List<BannerBean.MiaoshaBean.ListBeanX> list) {
        this.list=list;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=View.inflate(context, R.layout.miaosha_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

holder.tv_nowPrice.setText("¥"+list.get(position).getBargainPrice()+"");
        holder.tv_alongPrice.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        holder.tv_alongPrice.setText("¥"+list.get(position).getPrice()+"");
        String images = list.get(position).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.iv_rcb);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_rcb;
        private final TextView tv_nowPrice;
        private final TextView tv_alongPrice;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_rcb = itemView.findViewById(R.id.iv_miaosha);
            tv_nowPrice= itemView.findViewById(R.id.tv_nowPrice);
            tv_alongPrice = itemView.findViewById(R.id.tv_alongPrice);
        }
    }
}
