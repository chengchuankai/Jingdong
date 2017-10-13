package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.ShopDetailBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.ShopDetailPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.ShopDetailView;

/**
 * Created by asus on 2017/10/12.
 */

public class Fragment_shopdetail extends Fragment implements ShopDetailView {

    @BindView(R.id.xban_shop_detail)
    XBanner xbanShopDetail;
    @BindView(R.id.tv_shop_title)
    TextView tvShopTitle;
    @BindView(R.id.tv_shop_subbtitle)
    TextView tvShopSubbtitle;
    @BindView(R.id.tv_shop_datail_price)
    TextView tvShopDatailPrice;
    @BindView(R.id.btn_blew_price)
    Button btnBlewPrice;
    private View view;
    private ShopDetailPresenter shopDetailPresenter;
    private SharedPreferences shopdetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fl_shopdetail_shop, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        shopdetail = getActivity().getSharedPreferences("shopdetail", Context.MODE_PRIVATE);
        String shopid = getArguments().getString("shopid", null);
        if (!TextUtils.isEmpty(shopid)) {
            shopDetailPresenter = new ShopDetailPresenter(this,getActivity());
            shopDetailPresenter.gainshopDetail(shopid);
        }
    }


    @OnClick({R.id.xban_shop_detail, R.id.btn_blew_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xban_shop_detail:
                break;
            case R.id.btn_blew_price:
                break;
        }
    }

    @Override
    public void gainSuc(String data) {
        final List<String> imgs_list=new ArrayList<>();
        Gson gson=new Gson();
        ShopDetailBean shopDetailBean = gson.fromJson(data, ShopDetailBean.class);
        if(shopDetailBean!=null) {
            ShopDetailBean.DataBean shopMsg = shopDetailBean.getData();

            //存入sp
            shopdetail.edit().putString("detail",shopMsg.getDetailUrl()).commit();
            String images = shopMsg.getImages();
            String title = shopMsg.getTitle();
            tvShopTitle.setText(title);
            tvShopDatailPrice.setText("¥  "+shopMsg.getPrice()+"");
            tvShopSubbtitle.setText(shopMsg.getSubhead());
            String[] split = images.split("\\|");
            if (split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    imgs_list.add(split[i]);
                }
            }
            xbanShopDetail.setData(imgs_list, null);
            xbanShopDetail.setmAdapter(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getActivity()).load(imgs_list.get(position)).into((ImageView) view);
                }
            });
        }
    }

    @Override
    public void gainFail() {

    }
}
