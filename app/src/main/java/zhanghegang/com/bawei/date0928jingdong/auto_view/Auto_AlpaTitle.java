package zhanghegang.com.bawei.date0928jingdong.auto_view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by asus on 2017/10/10.
 */

public class Auto_AlpaTitle extends NestedScrollView {
    public Auto_AlpaTitle(Context context) {
        this(context,null);
    }

    public Auto_AlpaTitle(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Auto_AlpaTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private int mSlop;
    private LinearLayout toolbar;
    private View headView;


    private void init(Context context) {
        // mSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        mSlop = 10;
        // Log.i(TAG, mSlop + "");
    }

    /**
     *
     *            头部布局
     *            标题
     */
    public void setTitleAndHead(LinearLayout toolbar, View headView) {
        this.toolbar = toolbar;
        this.headView = headView;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        float headHeight = headView.getMeasuredHeight()
                - toolbar.getMeasuredHeight();
        int alpha = (int) (((float) t / headHeight) * 255);
        if (alpha >= 255)
            alpha = 255;
        if (alpha <= mSlop)
            alpha = 0;
        toolbar.getBackground().setAlpha(alpha);

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
