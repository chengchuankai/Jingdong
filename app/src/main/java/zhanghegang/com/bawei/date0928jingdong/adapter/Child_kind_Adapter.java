package zhanghegang.com.bawei.date0928jingdong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.ChildKindBean;

/**
 * Created by asus on 2017/10/8.
 */

public class Child_kind_Adapter extends BaseAdapter {
    private final Context context;
    private final List<ChildKindBean.DataBean> list;

    public Child_kind_Adapter(Context context, List<ChildKindBean.DataBean> list) {
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
            view=View.inflate(context, R.layout.kind_child,null);
            vh.tv_kind_chidname=view.findViewById(R.id.tv_child_name_kind);
            vh.gv_child=view.findViewById(R.id.gv_child_kind);
            view.setTag(vh);
        }
        else
        {
            vh= (ViewHolder) view.getTag();
        }
        ChildKindBean.DataBean dataBean = list.get(i);
        String name = dataBean.getName();
        vh.tv_kind_chidname.setText(name);
        vh.gv_child.setAdapter(new Child_Kind_Gv_adapter(context,dataBean.getList()));
        return view;
    }
    class ViewHolder{
         TextView tv_kind_chidname;
        GridView gv_child;
    }
}
