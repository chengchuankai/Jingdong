package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhanghegang.com.bawei.date0928jingdong.R;

/**
 * Created by asus on 2017/10/12.
 */

public class Fragment_detail extends Fragment {

    @BindView(R.id.wv_detail)
    WebView wvDetail;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fl_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences shopdetail = getActivity().getSharedPreferences("shopdetail", Context.MODE_PRIVATE);
        final String detail = shopdetail.getString("detail", null);

        if (!TextUtils.isEmpty(detail)) {
            WebSettings settings = wvDetail.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            wvDetail.loadUrl(detail);
            wvDetail.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(detail);
                    return false;
                }
            });
        }
    }


}
