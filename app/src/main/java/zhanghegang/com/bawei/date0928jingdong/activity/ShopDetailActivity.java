package zhanghegang.com.bawei.date0928jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhanghegang.com.bawei.date0928jingdong.R;

public class ShopDetailActivity extends AppCompatActivity {

    @BindView(R.id.shop_wv)
    WebView shopWv;
    private WebSettings settings;
    private String shopUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        initData();
        initSetting();
    }

    private void initData() {
        Intent intent = getIntent();

        shopUrl = intent.getStringExtra("shopUrl");
        System.out.println("shop============"+shopUrl);
    }

    private void initSetting() {
        settings = shopWv.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        //加载webView
        shopWv.loadUrl(shopUrl);
        //在weBView控件中打开，不跳到其他浏览器
        shopWv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(shopUrl);
                return true;
            }
        });
    }
}
