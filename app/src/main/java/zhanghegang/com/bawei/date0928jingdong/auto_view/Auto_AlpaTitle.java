package zhanghegang.com.bawei.date0928jingdong.auto_view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stx.xhb.xbanner.XBanner;

import zhanghegang.com.bawei.date0928jingdong.R;

/**
 * Created by asus on 2017/10/10.
 */

public class Auto_AlpaTitle extends NestedScrollView {
    private View all;
    private ImageView iv_sao;
    private ImageView iv_msg;
    private TextView tv_sao;
    private TextView tv_msg;

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
    public void setTitleAndHead(LinearLayout toolbar, View headView, ImageView iv_sao, ImageView iv_msg, TextView tv_msg,TextView tv_sao) {
        this.toolbar = toolbar;
        this.headView = headView;
        this.iv_sao=iv_sao;
        this.iv_msg=iv_msg;
        this.tv_sao=tv_sao;
        this.tv_msg=tv_msg;


    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        float headHeight = headView.getMeasuredHeight()
                - toolbar.getMeasuredHeight();
        int alpha = (int) (((float) t / headHeight) * 255);
        if (alpha >= 255)
        {
            alpha = 255;

        }
        if (alpha <= mSlop)
        {
            alpha = 0;


        }
        if(alpha==255)
        {
            System.out.println("===============77777777777777777777777");
            iv_msg.setImageResource(R.drawable.msg_hei);
            iv_sao.setImageResource(R.drawable.sao_hei);
            tv_msg.setTextColor(Color.BLACK);
            tv_sao.setTextColor(Color.BLACK);
        }
        if(alpha==0)
        {
            System.out.println("===============88888888888888");
            iv_msg.setImageResource(R.drawable.a9x);
            iv_sao.setImageResource(R.drawable.a_s);
            tv_msg.setTextColor(Color.WHITE);
            tv_sao.setTextColor(Color.WHITE);
        }
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.getBackground().setAlpha(alpha);

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
