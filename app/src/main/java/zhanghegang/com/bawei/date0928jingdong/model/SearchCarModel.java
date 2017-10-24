package zhanghegang.com.bawei.date0928jingdong.model;

import android.content.Context;

import java.util.Map;

import zhanghegang.com.bawei.date0928jingdong.api.ApiUrl;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpCall;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpMethod;
import zhanghegang.com.bawei.date0928jingdong.utils.OkhttpUtils;

/**
 * Created by asus on 2017/10/17.
 */

public class SearchCarModel {
    public void searchCar(Context context, Map<String,Object> map, OkhttpCall okhttpCall){
        OkhttpUtils.getInstance(context).call(OkhttpMethod.POST, ApiUrl.SEARCHCARURL,map,okhttpCall);
    }
    public void updateCar(Context context, Map<String,Object> map, OkhttpCall okhttpCall){
        OkhttpUtils.getInstance(context).call(OkhttpMethod.POST, ApiUrl.UPDATECARURL,map,okhttpCall);
    }
}
