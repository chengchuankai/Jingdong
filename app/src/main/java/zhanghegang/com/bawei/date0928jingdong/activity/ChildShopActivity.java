package zhanghegang.com.bawei.date0928jingdong.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.adapter.ShopChildKindAdapter;
import zhanghegang.com.bawei.date0928jingdong.bean.ShopChildKindBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.ShopKindPresenter;

import zhanghegang.com.bawei.date0928jingdong.utils.Order;
import zhanghegang.com.bawei.date0928jingdong.utils.SaleOrder;
import zhanghegang.com.bawei.date0928jingdong.view.ShopKindView;

public class ChildShopActivity extends AppCompatActivity implements ShopKindView {
    private static final int SEARCHREQUEST = 7777;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    private boolean flag = false;
    private boolean numFlag = false;
    private boolean priceFlag = false;
    private boolean orderFlag = false;
    private boolean urlFlag = false;
    private int pager=0;
    @BindView(R.id.iv_kind_back)
    ImageView ivKindBack;
    @BindView(R.id.ll_sao)
    LinearLayout llSao;
    @BindView(R.id.iv_kind_type)
    ImageView ivKindType;
    @BindView(R.id.ll_msg)
    LinearLayout llMsg;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tool_head)
    Toolbar toolHead;
    @BindView(R.id.ll_nest_toolBar)
    LinearLayout llNestToolBar;
    @BindView(R.id.tab_child_kind)
    TabLayout tabChildKind;
    @BindView(R.id.tv_shaixuan)
    TextView tvShaixuan;
    @BindView(R.id.ll_shaixuan)
    LinearLayout llShaixuan;
    @BindView(R.id.btn_jingdong)
    Button btnJingdong;
    @BindView(R.id.btn_new_shop)
    Button btnNewShop;
    @BindView(R.id.btn_grade)
    Button btnGrade;
    @BindView(R.id.btn_caizhi)
    Button btnCaizhi;
    @BindView(R.id.rcv_kind)
    RecyclerView rcvKind;
    private ShopKindPresenter shopKindPresenter;
    private String key;
    private ShopChildKindAdapter shopChildKindAdapter;

private boolean adapterFlag;
    private List<ShopChildKindBean.DataBean> shop_shop;
    private boolean pagerFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.activity_child_shop);
        ButterKnife.bind(this);
        initTab();
        initData();
        initLoadMore();
    }

    private int lastVisibleItem=0;
    private void initLoadMore() {

       rcvKind.setOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//               Toast.makeText(ChildShopActivity.this, "外面"+lastVisibleItem, Toast.LENGTH_SHORT).show();
               if(newState==rcvKind.SCROLL_STATE_IDLE&&lastVisibleItem+1==shopChildKindAdapter.getItemCount()){

                   //设置正在加载更多
//                  shopChildKindAdaptera(mRefreshAdapter.LOADING_MORE);

                   //改为网络请求
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {


                           if(pager!=-1) {
                               Toast.makeText(ChildShopActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
                               pager++;
                               pagerFlag = true;

                               initData();
                           }
                           else {
                               Toast.makeText(ChildShopActivity.this, "到底了", Toast.LENGTH_SHORT).show();
                               pagerFlag = false;
                           }

                       }
                   }, 1000);


               }

           }

           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);

               RecyclerView.LayoutManager layoutManager = rcvKind.getLayoutManager();
               //最后一个可见的ITEM
               if(layoutManager instanceof LinearLayoutManager) {
                   lastVisibleItem =((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
               }
               else if(layoutManager instanceof GridLayoutManager){
                   lastVisibleItem =((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
               }
//               Toast.makeText(ChildShopActivity.this, "滑动"+lastVisibleItem, Toast.LENGTH_SHORT).show();
//
              }

       });
//        rcvKind.setOnScrollListener(new OnRcvScrollListener(){
//            @Override
//            public void onBottom() {
//                super.onBottom();
//
//            }
//        });
    }



    private void initTab() {
        shopKindPresenter = new ShopKindPresenter(this, this);
        tabChildKind.addTab(tabChildKind.newTab().setText("综合"));
        tabChildKind.addTab(tabChildKind.newTab().setText("销量"));
        tabChildKind.addTab(tabChildKind.newTab().setText("价格"));
        tabChildKind.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1) {
                    orderFlag = false;
                    if (shop_shop!=null&&shop_shop.size() > 0) {
                        if (orderFlag == false) {
                            Collections.sort(shop_shop, new SaleOrder(numFlag));
                            if (numFlag == false) {
                                numFlag = true;
                            } else {
                                numFlag = false;
                            }
                        }
                        rcvKind.setAdapter(shopChildKindAdapter);
//                        shopChildKindAdapter.notifyDataSetChanged();
                    }

                } else if (tab.getPosition() == 2) {
                    orderFlag = true;
                    if (shop_shop!=null&&shop_shop.size() > 0) {
                        Collections.sort(shop_shop, new Order(priceFlag));
                        if (priceFlag == false) {
                            priceFlag = true;
                        } else {
                            priceFlag = false;
                        }
                        rcvKind.setAdapter(shopChildKindAdapter);
//                        shopChildKindAdapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    orderFlag = false;
                    if (shop_shop!=null&&shop_shop.size() > 0) {
                        if (orderFlag == false) {
                            Collections.sort(shop_shop, new SaleOrder(numFlag));
                            if (numFlag == false) {
                                numFlag = true;
                            } else {
                                numFlag = false;
                            }
                        }
                        rcvKind.setAdapter(shopChildKindAdapter);
//                        shopChildKindAdapter.notifyDataSetChanged();
                    }
//                    initData();

                } else if (tab.getPosition() == 2) {
                    orderFlag = true;
                    if (shop_shop!=null&&shop_shop.size() > 0) {
                        Collections.sort(shop_shop, new Order(priceFlag));
                        if (priceFlag == false) {
                            priceFlag = true;
                        } else {
                            priceFlag = false;
                        }
                        rcvKind.setAdapter(shopChildKindAdapter);
//                        shopChildKindAdapter.notifyDataSetChanged();
                    }
//                    initData();


                }
            }
        });
    }

    private void initData() {

        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");

        if (urlFlag == false) {
            if (!TextUtils.isEmpty(cid)) {
//                Toast.makeText(this, cid, Toast.LENGTH_SHORT).show();
                Map<String, Object> map = new HashMap<>();
                map.put("pscid", cid);
                if(orderFlag)
                {
                    map.put("sort",1);

                }
                if(pager!=-1) {
                    map.put("page", pager + "");
                }
                shopKindPresenter.gainShopKind(map, urlFlag);
            }
        } else {

//            if (!TextUtils.isEmpty(cid)) {
//                key = cid;
//            }
            if (!TextUtils.isEmpty(key)) {
                if(adapterFlag){
                    shopChildKindAdapter=null;
                    adapterFlag=false;
                }

//                Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
                Map<String, Object> map = new HashMap<>();
                map.put("keywords", key);
                if(orderFlag)
                {
                    map.put("sort",1);

                }
                if(pager!=-1) {
                    System.out.println("pager=111111=========="+pager);
                    map.put("page", pager + "");
                }
                shopKindPresenter.gainShopKind(map, urlFlag);
            }
        }

    }

    @OnClick({R.id.iv_kind_back, R.id.tab_child_kind, R.id.ll_shaixuan, R.id.btn_jingdong, R.id.btn_new_shop, R.id.btn_grade, R.id.btn_caizhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_kind_back:
                finish();
                break;
            case R.id.tab_child_kind:
                break;
            case R.id.ll_shaixuan:
                break;
            case R.id.btn_jingdong:
                break;
            case R.id.btn_new_shop:
                break;
            case R.id.btn_grade:
                break;
            case R.id.btn_caizhi:
                break;
            case R.id.ll_search:

                break;
        }
    }

    @OnClick(R.id.iv_kind_type)
    public void onViewClicked() {
        if (flag == false) {
            flag = true;
            ivKindType.setImageResource(R.drawable.kind_liner);
            shopChildKindAdapter.setMarket(flag);
            rcvKind.setLayoutManager(new GridLayoutManager(this, 2));
            rcvKind.setAdapter(shopChildKindAdapter);




        } else {
            flag = false;
            rcvKind.setLayoutManager(new LinearLayoutManager(this));
            ivKindType.setImageResource(R.drawable.kind_grid);

            shopChildKindAdapter.setMarket(flag);
          rcvKind.setAdapter(shopChildKindAdapter);

        }
    }

    @Override
    public void gainFail() {

    }

    @Override
    public void gainshopKindSuc(String data) {
        Gson gson = new Gson();
        ShopChildKindBean shopChildKindBean = gson.fromJson(data, ShopChildKindBean.class);
        if (shopChildKindBean != null) {
            shop_shop = shopChildKindBean.getData();
            if (shop_shop.size() > 0) {
//                if (orderFlag == false) {
//                    Collections.sort(shop_shop, new SaleOrder(numFlag));
//                    if (numFlag == false) {
//                        numFlag = true;
//                    } else {
//                        numFlag = false;
//                    }
//                } else {
//                    Collections.sort(shop_shop, new Order(priceFlag));
//                    if (priceFlag == false) {
//                        priceFlag = true;
//                    } else {
//                        priceFlag = false;
//                    }
//                }

setAda(shop_shop);


            }
            else {
                pager=-1;
            }
        }

    }

    private void setAda( List<ShopChildKindBean.DataBean> shop_shop) {
        if (flag==false) {
            rcvKind.setLayoutManager(new LinearLayoutManager(this));
        } else {
            rcvKind.setLayoutManager(new GridLayoutManager(this, 2));

        }
        if (shopChildKindAdapter == null) {
            shopChildKindAdapter = new ShopChildKindAdapter(this, shop_shop, flag);
            rcvKind.setAdapter(shopChildKindAdapter);
        } else {

            if(pagerFlag) {
                shopChildKindAdapter.addList(shop_shop);
            }else{}
            shopChildKindAdapter.notifyDataSetChanged();

//                rcvKind.setAdapter(shopChildKindAdapter);


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        shopChildKindAdapter=null;
    }

    @OnClick(R.id.ll_search)
    public void onll_searchClicked() {
        startActivityForResult(new Intent(ChildShopActivity.this,SearchActivity.class),SEARCHREQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
if(requestCode==SEARCHREQUEST)
{
  key = data.getStringExtra("key");

    if(!TextUtils.isEmpty(key)) {
        urlFlag=true;
        adapterFlag=true;
        pager=0;
        pagerFlag=false;
        initData();

    }
}
    }
}
