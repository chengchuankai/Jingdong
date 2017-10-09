package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhanghegang.com.bawei.date0928jingdong.CustomScanActivity;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.activity.ShopDetailActivity;
import zhanghegang.com.bawei.date0928jingdong.adapter.KindAdapter;
import zhanghegang.com.bawei.date0928jingdong.adapter.Vp_kind_head_Adapter;
import zhanghegang.com.bawei.date0928jingdong.bean.BannerBean;
import zhanghegang.com.bawei.date0928jingdong.bean.KindBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.BannerPrecenter;
import zhanghegang.com.bawei.date0928jingdong.view.BannerView;

/**
 * Created by asus on 2017/9/28.
 */

public class Fragment_head extends Fragment implements BannerView, ViewPager.OnPageChangeListener {

    private View view;
    private int maxPage=10;
    private LinearLayout ll_sao;
    private BannerPrecenter bannerPrecenter;
    @BindView(R.id.tv_tuijian)
    TextView tv_tuijian;
@BindView(R.id.iv_banner)
XBanner iv_banner;
    private List<String> list_img;
@BindView(R.id.ll_dot)
LinearLayout ll_dot;
    @BindView(R.id.vp_head_kind)
    ViewPager vp_head_kind;
    @BindView(R.id.rcv_show)
    RecyclerView rcv_show;
    private List<ImageView> img_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.head,null);
        ButterKnife.bind(this,view);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        bannerPrecenter = new BannerPrecenter(this);
        //调用precenter方法
        bannerPrecenter.gainBanner();
bannerPrecenter.gainKind();
        drawDot(2);
    }




    @Override
    public void gainSuc(final String data) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pareseData(data);
            }
        });
    }

    private void pareseData(String data) {
        list_img = new ArrayList<>();
        Gson gson=new Gson();
        BannerBean bannerBean = gson.fromJson(data, BannerBean.class);
        BannerBean.TuijianBean tuijian = bannerBean.getTuijian();
        //获得为你推荐的数据
        List<BannerBean.TuijianBean.ListBean> list = tuijian.getList();
        String name = tuijian.getName();
tv_tuijian.setText(name);

        final List<BannerBean.DataBean> been = bannerBean.getData();
        for (int i = 0; i <been.size() ; i++) {
            int type = been.get(i).getType();
            if(type==0)
            {
                list_img.add(been.get(i).getIcon());

            }
        }
        iv_banner.setData(list_img,null);
        iv_banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(list_img.get(position)).into((ImageView) view);
            }
        });
        //监听xbanner
        iv_banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {

               //跳转到详情
                Intent intent=new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("shopUrl",been.get(position).getUrl());
getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void gainFail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Banner请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void kindSuc(final String data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //解析分类
                pareseKindData(data);
            }
        });
    }

    private void pareseKindData(String data) {
Gson gson=new Gson();
        KindBean kindBean = gson.fromJson(data, KindBean.class);
        List<KindBean.DataBean> kind = kindBean.getData();
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
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        //向上取整
        int totalPage=(int) Math.ceil(kind.size() * 1.0 /maxPage);
        System.out.println("totalPage"+totalPage);
        List<View> viewList=new ArrayList<>();
        for (int i = 0; i <totalPage ; i++) {
          RecyclerView rcv= (RecyclerView) inflater.inflate(R.layout.head_rcv_kind,vp_head_kind,false);

            rcv.setLayoutManager(new GridLayoutManager(getActivity(),5));
            KindAdapter kindAdapter=new KindAdapter(getActivity(),kind,i,maxPage);
            rcv.setAdapter(kindAdapter);
            viewList.add(rcv);
        }



        System.out.println("totalPage"+viewList.size());
vp_head_kind.setAdapter(new Vp_kind_head_Adapter(viewList));
        //设置监听
        vp_head_kind.addOnPageChangeListener(this);






    }

    private void drawDot(int totalPage) {
        img_list = new ArrayList<>();
        for (int i = 0; i <totalPage ; i++) {
            ImageView iv=new ImageView(getActivity());
            if(i==0)
            {
               iv.setImageResource(R.drawable.shape_select);
            }
            else
            {
                iv.setImageResource(R.drawable.shape_normal);
            }
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(10,10);
            params.setMargins(5,0,5,0);
            ll_dot.addView(iv,params);
            img_list.add(iv);
        }
    }

    @Override
    public void kindFail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "分类请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i <2 ; i++) {
            if(i==position)
            {
                img_list.get(i).setImageResource(R.drawable.shape_select);
            }
            else
            {
                img_list.get(i).setImageResource(R.drawable.shape_normal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
