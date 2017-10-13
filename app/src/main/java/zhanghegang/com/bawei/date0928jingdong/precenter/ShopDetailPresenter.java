package zhanghegang.com.bawei.date0928jingdong.precenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import zhanghegang.com.bawei.date0928jingdong.model.ChildKindModel;
import zhanghegang.com.bawei.date0928jingdong.model.ShopDetailModel;
import zhanghegang.com.bawei.date0928jingdong.view.ChildKindView;
import zhanghegang.com.bawei.date0928jingdong.view.ShopDetailView;

/**
 * Created by asus on 2017/10/8.
 */

public class ShopDetailPresenter implements ShopDetailModel.ShopListener {
    private final ShopDetailView shopview;
    private final ShopDetailModel shopDetailModel;
    private final Context context;

    public ShopDetailPresenter(ShopDetailView shopview, Context context) {
        this.context=context;
        this.shopview=shopview;
      shopDetailModel=new ShopDetailModel();
        shopDetailModel.setShop(this);
    }
    public void gainshopDetail(String pid){
        if(!TextUtils.isEmpty(pid)) {
         shopDetailModel.gainShopDetail(pid);
        }
    }

    @Override
    public void gainSuc(final String data) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!TextUtils.isEmpty(data)) {
                    shopview.gainSuc(data);
                }
            }
        });

    }

    @Override
    public void gainFail() {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                shopview.gainFail();
            }
        });

    }
}
