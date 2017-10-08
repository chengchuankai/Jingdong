package zhanghegang.com.bawei.date0928jingdong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_find;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_head;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_kind;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_my;
import zhanghegang.com.bawei.date0928jingdong.fragment.Fragment_shopping;

public class MainActivity extends AppCompatActivity {
private LinearLayout ll_sao;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.iv_kind)
    ImageView ivKind;
    @BindView(R.id.iv_find)
    ImageView ivFind;
    @BindView(R.id.iv_shopping)
    ImageView ivShopping;
    @BindView(R.id.iv_my)
    ImageView ivMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initFragment();
    }



    private void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_all, new Fragment_head()).commit();
    }

    private void initView() {
        ll_sao = (LinearLayout) findViewById(R.id.ll_sao);
        ll_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator iit= new IntentIntegrator(MainActivity.this);

//修改前后摄像头
                iit.setCameraId(0);
                iit.setPrompt("欢迎进入京东扫码环节");
                iit.setTimeout(5000);
                iit.setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan();// 初始化扫描

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {

                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                Toast.makeText(this, ScanResult, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.iv_head, R.id.iv_kind, R.id.iv_find, R.id.iv_shopping, R.id.iv_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_all, new Fragment_head()).commit();
                ivHead.setImageResource(R.drawable.ac1);
                ivKind.setImageResource(R.drawable.abw);
                ivFind.setImageResource(R.drawable.aby);
                ivShopping.setImageResource(R.drawable.abu);
                ivMy.setImageResource(R.drawable.ac2);
                break;
            case R.id.iv_kind:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_all, new Fragment_kind()).commit();
                ivHead.setImageResource(R.drawable.ac0);
                ivKind.setImageResource(R.drawable.abx);
                ivFind.setImageResource(R.drawable.aby);
                ivShopping.setImageResource(R.drawable.abu);
                ivMy.setImageResource(R.drawable.ac2);

                break;
            case R.id.iv_find:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_all, new Fragment_find()).commit();
                ivHead.setImageResource(R.drawable.ac0);
                ivKind.setImageResource(R.drawable.abw);
                ivFind.setImageResource(R.drawable.abz);
                ivShopping.setImageResource(R.drawable.abu);
                ivMy.setImageResource(R.drawable.ac2);
                break;
            case R.id.iv_shopping:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_all, new Fragment_shopping()).commit();
                ivHead.setImageResource(R.drawable.ac0);
                ivKind.setImageResource(R.drawable.abw);
                ivFind.setImageResource(R.drawable.aby);
                ivShopping.setImageResource(R.drawable.abv);
                ivMy.setImageResource(R.drawable.ac2);
                break;
            case R.id.iv_my:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_all, new Fragment_my()).commit();
                ivHead.setImageResource(R.drawable.ac0);
                ivKind.setImageResource(R.drawable.abw);
                ivFind.setImageResource(R.drawable.aby);
                ivShopping.setImageResource(R.drawable.abu);
                ivMy.setImageResource(R.drawable.ac3);
                break;
        }
    }


}
