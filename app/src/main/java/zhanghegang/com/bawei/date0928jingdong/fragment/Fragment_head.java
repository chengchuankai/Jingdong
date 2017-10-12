package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.stx.xhb.xbanner.XBanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhanghegang.com.bawei.date0928jingdong.CustomScanActivity;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.activity.ShopDetailActivity;
import zhanghegang.com.bawei.date0928jingdong.adapter.KindAdapter;
import zhanghegang.com.bawei.date0928jingdong.adapter.MiaoshaAdapter;
import zhanghegang.com.bawei.date0928jingdong.adapter.TuijianAdapter;
import zhanghegang.com.bawei.date0928jingdong.adapter.Vp_kind_head_Adapter;
import zhanghegang.com.bawei.date0928jingdong.auto_view.Auto_AlpaTitle;
import zhanghegang.com.bawei.date0928jingdong.bean.BannerBean;
import zhanghegang.com.bawei.date0928jingdong.bean.KindBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.BannerPrecenter;
import zhanghegang.com.bawei.date0928jingdong.view.BannerView;

/**
 * Created by asus on 2017/9/28.
 */

public class Fragment_head extends Fragment implements BannerView, ViewPager.OnPageChangeListener {

    @BindView(R.id.ll_allboor)
    LinearLayout llAllboor;
    private View view;
    private int maxPage = 10;
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
    @BindView(R.id.rcv_miaosha)
    RecyclerView rcv_miaosha;
    @BindView(R.id.tv_miaosha_shi)
    TextView tv_miaosha_shi;
    @BindView(R.id.tv_miaosha_minter)
    TextView tv_miaosha_minter;
    @BindView(R.id.tv_miaosha_second)
    TextView tv_miaosha_second;
    @BindView(R.id.tv_miaosha_time)
    TextView tv_miaosha_time;
    private List<ImageView> img_list;
    @BindView(R.id.nest_scroll)
    Auto_AlpaTitle nest_scroll;
    @BindView(R.id.ll_nest_toolBar)
    LinearLayout ll_nest_toolBar;

    private int hourNormal = 0;
    private int secondNormal = 60;
    private int minterNormal = 60;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.head, null);
        ButterKnife.bind(this, view);
        int resourceId = getActivity().getResources().getIdentifier("status_bar_height", "dimen",
                "android");

        int marig = getResources().getDimensionPixelSize(resourceId);
//        int height = ll_nest_toolBar.getHeight();
//        Toast.makeText(getActivity(), marig + "=="+height, Toast.LENGTH_SHORT).show();
//
//       RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ll_nest_toolBar.getHeight());
//        params.setMargins(0, marig, 0, 0);
//        llAllboor.setLayoutParams(params);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {

        ll_sao = view.findViewById(R.id.ll_sao);
        ll_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator iit = new IntentIntegrator(getActivity());

//修改前后摄像头
                iit.setCameraId(0);
                iit.setPrompt("欢迎进入京东扫码环节");
                iit.setTimeout(5000);
                iit.setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan();// 初始化扫描

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        View view2 = View.inflate(getActivity(), R.layout.all_toobar, null);
        ImageView iv_sao = view.findViewById(R.id.iv_sao);
        ImageView iv_msg = view.findViewById(R.id.iv_msg);
        TextView tv_sao = view.findViewById(R.id.tv_sao);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        nest_scroll.setTitleAndHead(ll_nest_toolBar, iv_banner, iv_sao, iv_msg, tv_msg, tv_sao);
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
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pareseData(data);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(timeGo);
    }

    private void pareseData(String data) {
        list_img = new ArrayList<>();
        Gson gson = new Gson();
        BannerBean bannerBean = gson.fromJson(data, BannerBean.class);
        //获得秒杀数据
        BannerBean.MiaoshaBean miaosha = bannerBean.getMiaosha();

        //转化时间
        int time = miaosha.getTime();
        parseTime(time);
        List<BannerBean.MiaoshaBean.ListBeanX> miaosha_list = miaosha.getList();
        MiaoshaAdapter miaoshaAdapter = new MiaoshaAdapter(getActivity(), miaosha_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置水平滑动
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_miaosha.setLayoutManager(linearLayoutManager);
        rcv_miaosha.setAdapter(miaoshaAdapter);
        //获得为你推荐的数据
        BannerBean.TuijianBean tuijian = bannerBean.getTuijian();

        List<BannerBean.TuijianBean.ListBean> list = tuijian.getList();
        //设置recyclerview适配器
        TuijianAdapter tuijianAdapter = new TuijianAdapter(getActivity(), list);
        rcv_show.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcv_show.setAdapter(tuijianAdapter);
        String name = tuijian.getName();
        tv_tuijian.setText(name);

        final List<BannerBean.DataBean> been = bannerBean.getData();
        for (int i = 0; i < been.size(); i++) {
            int type = been.get(i).getType();
            if (type == 0) {
                list_img.add(been.get(i).getIcon());

            }
        }
        iv_banner.setData(list_img, null);
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
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("shopUrl", been.get(position).getUrl());
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 创建Handler
     *
     * @param time
     */
    Handler handler = new Handler();
    Runnable timeGo = new Runnable() {
        @Override
        public void run() {

            if (secondNormal >= 0) {
                if (secondNormal % 60 == 0) {
                    if (minterNormal % 60 == 0) {
                        hourNormal--;
                        tv_miaosha_shi.setText(hourNormal + "");
                        if (hourNormal == 0) {
                            tv_miaosha_time.setText("12点场");
                            hourNormal = 2;
                        }
                    }
                    minterNormal--;
                    tv_miaosha_minter.setText(minterNormal + "");
                }
                secondNormal--;
                tv_miaosha_second.setText(secondNormal + "");
                if (secondNormal == 0) {
                    secondNormal = 60;
                }
            }
            handler.postDelayed(timeGo, 1000);
        }
    };

    private void parseTime(int time) {
        int second = time / 1000;
        SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
        simple.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String format = simple.format(time);
        System.out.println("时间转化：========" + format);
        //分割时分秒
        String[] split = format.split(":");
        tv_miaosha_shi.setText(split[0]);
        tv_miaosha_minter.setText(split[1]);
        tv_miaosha_second.setText(split[2]);
        if (!TextUtils.isEmpty(split[0])) {
            hourNormal = Integer.parseInt(split[0]);
        }
        handler.postDelayed(timeGo, 1000);
    }

    @Override
    public void gainFail() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Banner请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void kindSuc(final String data) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //解析分类
                    pareseKindData(data);
                }
            });
        }
    }

    private void pareseKindData(String data) {
        Gson gson = new Gson();
        KindBean kindBean = gson.fromJson(data, KindBean.class);
        List<KindBean.DataBean> kind = kindBean.getData();
        System.out.println("king111++++++++" + kind.size());

        Iterator<KindBean.DataBean> iterator = kind.iterator();
        while (iterator.hasNext()) {
            KindBean.DataBean next = iterator.next();
            if (next.getIshome().equals("0")) {
                iterator.remove();
            }
        }
        if (getActivity() != null) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            //向上取整
            int totalPage = (int) Math.ceil(kind.size() * 1.0 / maxPage);
            System.out.println("totalPage" + totalPage);
            List<View> viewList = new ArrayList<>();
            for (int i = 0; i < totalPage; i++) {
                RecyclerView rcv = (RecyclerView) inflater.inflate(R.layout.head_rcv_kind, vp_head_kind, false);

                rcv.setLayoutManager(new GridLayoutManager(getActivity(), 5));
                KindAdapter kindAdapter = new KindAdapter(getActivity(), kind, i, maxPage);
                rcv.setAdapter(kindAdapter);
                viewList.add(rcv);
            }


            System.out.println("totalPage" + viewList.size());
            vp_head_kind.setAdapter(new Vp_kind_head_Adapter(viewList));
            //设置监听
            vp_head_kind.addOnPageChangeListener(this);

        }


    }

    private void drawDot(int totalPage) {
        img_list = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            ImageView iv = new ImageView(getActivity());
            if (i == 0) {
                iv.setImageResource(R.drawable.shape_select);
            } else {
                iv.setImageResource(R.drawable.shape_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(5, 0, 5, 0);
            ll_dot.addView(iv, params);
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
        for (int i = 0; i < 2; i++) {
            if (i == position) {
                img_list.get(i).setImageResource(R.drawable.shape_select);
            } else {
                img_list.get(i).setImageResource(R.drawable.shape_normal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
