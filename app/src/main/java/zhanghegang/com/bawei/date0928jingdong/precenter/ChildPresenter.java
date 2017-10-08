package zhanghegang.com.bawei.date0928jingdong.precenter;

import zhanghegang.com.bawei.date0928jingdong.model.ChildKindModel;
import zhanghegang.com.bawei.date0928jingdong.view.ChildKindView;

/**
 * Created by asus on 2017/10/8.
 */

public class ChildPresenter implements ChildKindModel.ChildListener {
    private final ChildKindView childview;
    private final ChildKindModel childKindModel;

    public ChildPresenter(ChildKindView childKindView) {
        this.childview=childKindView;
        childKindModel = new ChildKindModel();
        childKindModel.setChild(this);
    }
    public void gainChildKind(String cid){
        childKindModel.gainChildKind(cid);
    }

    @Override
    public void gainSuc(String data) {

    }

    @Override
    public void gainFail() {

    }
}
