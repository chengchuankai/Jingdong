package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhanghegang.com.bawei.date0928jingdong.MainActivity;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.adapter.Child_kind_Adapter;
import zhanghegang.com.bawei.date0928jingdong.bean.ChildKindBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.ChildPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.ChildKindView;

/**
 * Created by asus on 2017/10/8.
 */

public class Fragment1_shop extends Fragment implements ChildKindView {

    private View view;
@BindView(R.id.lv_grid_childview)
    ListView lv_grid_childview;
    private String ccid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.f1_shop,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获得子分类
       View view=View.inflate(getActivity(),R.layout.child_head_img,null);

        lv_grid_childview.addHeaderView(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Bundle arguments = getArguments();
        String cid = arguments.getString("cid");
//        ccid = "";
//        Fragment_kind fragment_kind=new Fragment_kind();
//        fragment_kind.setParagms(new Fragment_kind.ParamsCid() {
//            @Override
//            public void gainCid(String cid) {
//                ccid =cid;
//            }
//        });
        ChildPresenter childPresenter=new ChildPresenter(this);
        childPresenter.gainChildKind(cid);
    }

    @Override
    public void gainSuc(final String data) {
        if (data!=null&&getActivity()!=null) {

            ((MainActivity)getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    System.out.println("data:"+data);
                    ChildKindBean childKindBean = gson.fromJson(data, ChildKindBean.class);
                    List<ChildKindBean.DataBean> kind_list = childKindBean.getData();
                    Child_kind_Adapter adapter = new Child_kind_Adapter(getActivity(), kind_list);
                    lv_grid_childview.setAdapter(adapter);
                }
            });
        }
    }

    @Override
    public void gainFail() {

    }
}
