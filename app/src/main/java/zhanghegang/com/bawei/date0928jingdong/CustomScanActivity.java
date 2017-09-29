package zhanghegang.com.bawei.date0928jingdong;

import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CustomScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {
    Button swichLight;
    Button hint1Show;
    Button hint2Show;
    DecoratedBarcodeView mDBV;
    private CaptureManager captureManager;
    private boolean isLightOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        initView();

        mDBV.setTorchListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if(!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this,mDBV);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
    }

    private void initView() {
        swichLight= (Button) findViewById(R.id.btn_switch);
        hint1Show= (Button) findViewById(R.id.btn_hint1);
        hint2Show= (Button) findViewById(R.id.btn_hint2);
        mDBV= (DecoratedBarcodeView) findViewById(R.id.dbv);
        swichLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swichLight();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    private void onOpen() {




    }

    // torch 手电筒
    @Override
    public void onTorchOn() {
        Toast.makeText(this,"torch on",Toast.LENGTH_LONG).show();
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        Toast.makeText(this,"torch off",Toast.LENGTH_LONG).show();
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    // 点击切换闪光灯

    public void swichLight(){
        if(isLightOn){
            mDBV.setTorchOff();
        }else{
            mDBV.setTorchOn();
        }

}


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
