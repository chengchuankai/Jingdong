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
import zhanghegang.com.bawei.date0928jingdong.activity.ShopActivity;
import zhanghegang.com.bawei.date0928jingdong.activity.ShopDetailActivity;
import zhanghegang.com.bawei.date0928jingdong.bean.ShopChildKindBean;


/**
 * Created by asus on 2017/10/14.
 */

public class ShopChildKindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private boolean flag=false;
    private List<ShopChildKindBean.DataBean> list;


    public ShopChildKindAdapter(Context context, List<ShopChildKindBean.DataBean> list,boolean flag) {
        this.context=context;
        this.list=list;
//        this.flag=flag;
    }
    public void setMarket(boolean flag){
        this.flag=flag;
        System.out.println("kindflag=========="+flag);
    }
    public void addList(List<ShopChildKindBean.DataBean> list2){
        this.list.addAll(list2);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(flag)
        {
            System.out.println("shu11111111");
            view=View.inflate(context, R.layout.gridlayout,null);
            return new LinerView(view);
        }
        else
        {
            System.out.println("shu222222222");
            view=View.inflate(context,R.layout.linerlayout,null);
            return new LinerView(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ShopChildKindBean.DataBean dataBean = list.get(position);

        ((LinerView)holder).tv_price.setText("¥  "+dataBean.getPrice()+"");
        ((LinerView)holder).tv_title.setText(dataBean.getTitle());
        String[] split = dataBean.getImages().split("\\|");
    Glide.with(context).load(split[0]).into(((LinerView) holder).iv_liner);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        System.out.println("pid========"+position+"======"+list.get(position).getPid());
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
    class LinerView extends RecyclerView.ViewHolder{

        private final TextView tv_title;
        private final TextView tv_price;
        private final ImageView iv_liner;

        public LinerView(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_liner_title);
            tv_price=itemView.findViewById(R.id.tv_liner_price);
            iv_liner = itemView.findViewById(R.id.iv_liner);
        }
    }

}
