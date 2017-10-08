package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.adapter.Kind_main;
import zhanghegang.com.bawei.date0928jingdong.adapter.Vp_kind_Adapter;
import zhanghegang.com.bawei.date0928jingdong.bean.KindBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.BannerPrecenter;
import zhanghegang.com.bawei.date0928jingdong.view.BannerView;

/**
 * Created by asus on 2017/9/28.
 */

public class Fragment_kind extends Fragment implements BannerView {

    private View view;
@BindView(R.id.kind_name)
    ListView lv_kind_name;
    @BindView(R.id.vp_kind_child)
    ViewPager vp_kind_chid;
    private BannerPrecenter bannerPrecenter;
    private Kind_main kind_main;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.kind_main,null);
        ButterKnife.bind(this,view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initVp();
    }

    private void initVp() {
        Vp_kind_Adapter vp_kind_adapter=new Vp_kind_Adapter(getActivity().getSupportFragmentManager());
        vp_kind_chid.setAdapter(vp_kind_adapter);
    }

    private void initData() {
        bannerPrecenter = new BannerPrecenter(this);
        bannerPrecenter.gainKind();
    }

    @Override
    public void gainSuc(String data) {

    }

    @Override
    public void gainFail() {

    }

    @Override
    public void kindSuc(final String data) {
getActivity().runOnUiThread(new Runnable() {
    @Override
    public void run() {
parseData(data);
    }
});
    }

    private void parseData(String data) {
        Gson gson=new Gson();
        KindBean kindBean = gson.fromJson(data, KindBean.class);
        final List<KindBean.DataBean> kind = kindBean.getData();
        System.out.println("king111++++++++"+kind.size());

        Iterator<KindBean.DataBean> iterator = kind.iterator();
        while (iterator.hasNext())
        {
            KindBean.DataBean next = iterator.next();
            if(next.getIshome().equals("0"))
            {
                iterator.remove();
            }
        }
        kind_main = new Kind_main(getActivity(), kind);
        lv_kind_name.setAdapter(kind_main);
        lv_kind_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              kind_main.changePosition(i);

            }
        });
    }

    @Override
    public void kindFail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
