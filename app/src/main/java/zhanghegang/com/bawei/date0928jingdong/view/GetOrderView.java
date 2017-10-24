package zhanghegang.com.bawei.date0928jingdong.view;

/**
 * Created by asus on 2017/10/17.
 */

public interface GetOrderView {
    void onGetOrderFail(String msg);
    void onGetOrderSuc(String data);
    void onUpdateOrderFail(String msg);
    void onUpdateOrderSuc(String data);

}
