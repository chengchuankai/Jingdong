package zhanghegang.com.bawei.date0928jingdong.precenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.util.Map;

import zhanghegang.com.bawei.date0928jingdong.model.CreateOrderModel;
import zhanghegang.com.bawei.date0928jingdong.model.DeleteCarModel;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpCall;
import zhanghegang.com.bawei.date0928jingdong.view.CreateOrderView;
import zhanghegang.com.bawei.date0928jingdong.view.DeleteCarView;

/**
 * Created by asus on 2017/10/17.
 */

public class CreateOrderPresenter {
    private final CreateOrderView createOrderView;
    private Context context;
    private final CreateOrderModel createOrderModel;

    public CreateOrderPresenter(Context context, CreateOrderView createOrderView) {
        this.context = context;
        this.createOrderView=createOrderView;
        createOrderModel = new CreateOrderModel();
    }
    public void createOrder(Map<String,Object> map){
        createOrderModel.createOrder(context, map, new OkhttpCall() {
            @Override
            public void onFailure(String e, final String msg) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createOrderView.onCreateOrderFail(msg);
                    }
                });
            }

            @Override
            public void onResponse(final String result) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
if(!TextUtils.isEmpty(result))
{
    createOrderView.onCreateOrderSuc(result);
}
                    }
                });
            }
        });

    }
}
