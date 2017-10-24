package zhanghegang.com.bawei.date0928jingdong.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhanghegang.com.bawei.date0928jingdong.api.ApiUrl;
import zhanghegang.com.bawei.date0928jingdong.bean.SearchBean;

/**
 * Created by asus on 2017/10/19.
 */

public class SearchCarUtils {

    private double sumPrice=0;
public int sumShop=0;


    public Map<String,Integer> searchCar(String result){
      Map<String,Integer> map=new HashMap<>();
                                Gson gson=new Gson();
                                SearchBean searchBean = gson.fromJson(result, SearchBean.class);
        if(searchBean!=null) {
            if (searchBean != null) {
                List<SearchBean.DataBean> data = searchBean.getData();
                if(data!=null&&data.size()>0) {
                    for (int i = 0; i < data.size(); i++) {
                        List<SearchBean.DataBean.ListBean> list = data.get(i).getList();
                        for (int j = 0; j < list.size(); j++) {
                            SearchBean.DataBean.ListBean listBean = list.get(j);
                            if (listBean.getSelected() == 1) {
                                int num = listBean.getNum();
                                String bargainPrice = listBean.getBargainPrice();
                                if (!TextUtils.isEmpty(bargainPrice)) {
                                    double v = Double.parseDouble(bargainPrice);
                                    double price = num * v;
                                    sumPrice += price;
                                    sumShop += num;
                                    System.out.println("searchprice========" + v + "num=====" + num);

                                }
                            }
                        }
                    }
                    map.put(doubleToString(sumPrice), sumShop);
                }
            }
        }

        return map;

    }
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
