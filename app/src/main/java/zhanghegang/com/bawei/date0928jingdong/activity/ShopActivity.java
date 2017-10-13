package zhanghegang.com.bawei.date0928jingdong.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_detail;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_say;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_shopdetail;

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.tab_shop)
    TabLayout tabShop;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_pop_menu)
    ImageView ivPopMenu;
    @BindView(R.id.ll_lingdang)
    LinearLayout llLingdang;
    @BindView(R.id.ll_dianpu)
    LinearLayout llDianpu;
    @BindView(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @BindView(R.id.ll_shopcar)
    LinearLayout llShopcar;
    @BindView(R.id.tv_addshop)
    TextView tvAddshop;
    @BindView(R.id.iv_back_shop)
    ImageView ivBackShop;
    @BindView(R.id.vp_tab_shop)
    ViewPager vpTabShop;
    @BindView(R.id.iv_guanzhu)
    ImageView ivGuanzhu;
    private List<Fragment> list_fragment;
    private List<String> list_shoptitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
//        tabShop.addTab(tabShop.newTab().setText("商品"));
//        tabShop.addTab(tabShop.newTab().setText("详情"));
//        tabShop.addTab(tabShop.newTab().setText("评价"));
        list_shoptitle = new ArrayList<>();
        list_shoptitle.add("商品");
        list_shoptitle.add("详情");
        list_shoptitle.add("评价");
        String pid = getIntent().getStringExtra("pid");
        //存储fragment
        list_fragment = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("shopid", pid);
        Fragment_shopdetail fragment_shop = new Fragment_shopdetail();
        fragment_shop.setArguments(bundle);
        list_fragment.add(fragment_shop);
        Fragment_detail fragment_detail = new Fragment_detail();
        fragment_detail.setArguments(bundle);
        list_fragment.add(fragment_detail);
        Fragment_say fragment_say = new Fragment_say();
        fragment_say.setArguments(bundle);
        list_fragment.add(fragment_say);
//        fragment_shop
        //设置适配器
        vpTabShop.setAdapter(new ShopvpAdapter(getSupportFragmentManager()));
        Toast.makeText(this, pid, Toast.LENGTH_SHORT).show();
        tabShop.setupWithViewPager(vpTabShop);

    }

    @OnClick({R.id.ll_lingdang, R.id.ll_dianpu, R.id.ll_guanzhu, R.id.ll_shopcar, R.id.tv_addshop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_lingdang:
                break;
            case R.id.ll_dianpu:
                break;
            case R.id.ll_guanzhu:

                break;
            case R.id.ll_shopcar:
                break;
            case R.id.tv_addshop:
                break;
        }
    }

    @OnClick(R.id.iv_back_shop)
    public void onViewClicked() {
        finish();
    }

    private class ShopvpAdapter extends FragmentPagerAdapter {


        public ShopvpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }


        @Override
        public int getCount() {
            return list_fragment.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_shoptitle.get(position);
        }
    }
}
