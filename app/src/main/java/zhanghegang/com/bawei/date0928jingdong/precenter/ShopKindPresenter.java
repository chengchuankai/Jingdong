package zhanghegang.com.bawei.date0928jingdong.precenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.Map;

import zhanghegang.com.bawei.date0928jingdong.api.ApiUrl;
import zhanghegang.com.bawei.date0928jingdong.model.ShopKindModel;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpCall;
import zhanghegang.com.bawei.date0928jingdong.view.ShopKindView;

/**
 * Created by asus on 2017/10/15.
 */

public class ShopKindPresenter {
    private final ShopKindView shopView;
    private final Context context;
    private final ShopKindModel shopKindModel;

    public ShopKindPresenter(ShopKindView shopKindView, Context context) {
        this.shopView=shopKindView;
        this.context=context;
        shopKindModel = new ShopKindModel();
    }
    public void gainShopKind( Map<String,Object> map,boolean flag){
        String url="";
        if(flag==false)
        {
          url=ApiUrl.SHOPCHILDKIND;
        }
        else {
            url=ApiUrl.FINDURL;
        }
            shopKindModel.gainShopKind(context,url, map, new OkhttpCall() {
                @Override
                public void onFailure(String e, String msg) {
                    shopView.gainFail();
                }

                @Override
                public void onResponse(String result) {
                    if(!TextUtils.isEmpty(result))
                    {
                        System.out.println("result=========="+result);
                        shopView.gainshopKindSuc(result);
                    }
                }
            });
        }
    }

