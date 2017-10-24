package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.OrderBean;

/**
 * Created by asus on 2017/10/21.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{
    public Context context;
    public List<OrderBean.DataBean> list;
    private SetBuyListener setBuyListener;
boolean[] flag;
    public OrderAdapter(Context context, List<OrderBean.DataBean> list) {
        this.context = context;
        this.list = list;
        flag=new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getStatus()==1)
            {
                flag[i]=true;
            }
            else {
                flag[i]=false;
            }
            System.out.println("index====flag======="+flag[i]+"=========");

        }
    }
    public void setStatus(int index,int updateId){
       list.get(index).setStatus(updateId);
        flag=new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getStatus()==1)
            {
                flag[i]=true;
            }
            else {
                flag[i]=false;
            }
            System.out.println("index====flag222222222222======="+flag[i]+"=========");

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.order_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
//        boolean visible= (boolean) holder.iv_order_over.getTag(1);
//        if(visible)
//        {
//            holder.iv_order_over.setVisibility(View.VISIBLE);
//        }



    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        OrderBean.DataBean dataBean = list.get(position);
        holder.tv_order_id.setText(dataBean.getOrderid()+"");
        holder.tv_price.setText(dataBean.getPrice()+"");
        if(flag[holder.getLayoutPosition()])
        {

//            holder.iv_order_over.setTag(1,true);

           holder.iv_order_over.setVisibility(View.VISIBLE);

        }
        else {
            holder.iv_order_over.setVisibility(View.GONE);
        }

        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(holder.getLayoutPosition()).getStatus()==2)
                {
                    Toast.makeText(context, "订单已取消，不能购买", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (list.get(holder.getLayoutPosition()).getStatus() == 0) {
                        setBuyListener.onBuyListener(holder.getLayoutPosition());
                    } else {
                        Toast.makeText(context, "订单已购买", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "list.get(holder.getLayoutPosition()).getStatus():" + list.get(holder.getLayoutPosition()).getStatus(), Toast.LENGTH_SHORT).show();
                if(list.get(holder.getLayoutPosition()).getStatus()==1)
                {
                    Toast.makeText(context, "订单已购买,不能取消", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (list.get(holder.getLayoutPosition()).getStatus() == 0) {
                        setBuyListener.onCancelListener(holder.getLayoutPosition());
                    } else {
                        Toast.makeText(context, "订单已取消", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void setBuyStatus(SetBuyListener setBuyListener){
        this.setBuyListener=setBuyListener;

    }
public interface SetBuyListener{
    void onBuyListener(int pos);
    void onCancelListener(int pos);
}
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_order_id;
        private final TextView tv_price;
        private final Button btn_quxiao;
        private final Button btn_buy;
        private final ImageView iv_order_over;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_order_id = itemView.findViewById(R.id.tv_orderid);
            tv_price = itemView.findViewById(R.id.tv_order_price);
            btn_quxiao = itemView.findViewById(R.id.btn_quxiao);
            btn_buy = itemView.findViewById(R.id.btn_buy);
            iv_order_over = itemView.findViewById(R.id.iv_over);
        }
    }
}
