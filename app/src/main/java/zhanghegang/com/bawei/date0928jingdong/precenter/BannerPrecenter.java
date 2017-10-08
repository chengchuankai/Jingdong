package zhanghegang.com.bawei.date0928jingdong.precenter;

import zhanghegang.com.bawei.date0928jingdong.model.BannerModel;
import zhanghegang.com.bawei.date0928jingdong.view.BannerView;

/**
 * Created by asus on 2017/9/29.
 */

public class BannerPrecenter implements BannerModel.BannerListener {
    private final BannerView bannerView;
    private final BannerModel bannerModel;

    public BannerPrecenter(BannerView bannerView) {
        this.bannerView=bannerView;
        bannerModel = new BannerModel();
        bannerModel.setBanner(this);
    }
    public void gainBanner(){
        bannerModel.gainImg();

    }
    public void gainKind(){
        bannerModel.gainKind();
    }

    @Override
    public void gainSuc(String data) {
        bannerView.gainSuc(data);
    }

    @Override
    public void gainFail() {
bannerView.gainFail();
    }

    @Override
    public void kindSuc(String data) {
        bannerView.kindSuc(data);

    }

    @Override
    public void kindFail() {
bannerView.kindFail();
    }
}
