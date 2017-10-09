package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.ChildKindBean;

/**
 * Created by asus on 2017/10/8.
 */

public class Child_Kind_Gv_adapter extends BaseAdapter {
    private final Context context;
    private final List<ChildKindBean.DataBean.ListBean> list;

    public Child_Kind_Gv_adapter(Context context, List<ChildKindBean.DataBean.ListBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(view==null)
        {
            vh=new ViewHolder();
            view=View.inflate(context, R.layout.kind_child_child_item,null);
            vh.iv_child_child=view.findViewById(R.id.iv_child_child);
            vh.tv_child_kind_childname=view.findViewById(R.id.tv_child_kind_childname);
            view.setTag(vh);
        }
        else
        {
           vh= (ViewHolder) view.getTag();
        }
        ChildKindBean.DataBean.ListBean listBean = list.get(i);
        vh.tv_child_kind_childname.setText(listBean.getName());
        Glide.with(context).load(listBean.getIcon()).into(vh.iv_child_child);
        return view;
    }
    class ViewHolder{
        TextView tv_child_kind_childname;
        ImageView iv_child_child;
    }
}
