package zhanghegang.com.bawei.date0928jingdong.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhanghegang.com.bawei.date0928jingdong.R;
import zhanghegang.com.bawei.date0928jingdong.bean.UserBean;
import zhanghegang.com.bawei.date0928jingdong.precenter.UserPresenter;
import zhanghegang.com.bawei.date0928jingdong.view.UserView;

public class MyInfoActivity extends AppCompatActivity implements UserView {

    @BindView(R.id.iv_cha)
    ImageView ivCha;
    @BindView(R.id.iv_myInfoSet)
    RoundedImageView ivMyInfoSet;
    @BindView(R.id.ll_myInfo_head)
    LinearLayout llMyInfoHead;
    @BindView(R.id.ll_my_head)
    LinearLayout llMyHead;
    @BindView(R.id.tv_myInfo_username)
    TextView tvMyInfoUsername;
    @BindView(R.id.ll_myInfo_head2)
    LinearLayout llMyInfoHead2;
    @BindView(R.id.ll_my_username)
    LinearLayout llMyUsername;
    @BindView(R.id.tv_myInfo_nick)
    TextView tvMyInfoNick;
    @BindView(R.id.ll_myInfo_head3)
    LinearLayout llMyInfoHead3;
    @BindView(R.id.ll_my_nick)
    LinearLayout llMyNick;
    @BindView(R.id.tv_myInfo_sex)
    TextView tvMyInfoSex;
    @BindView(R.id.ll_myInfo_head4)
    LinearLayout llMyInfoHead4;
    @BindView(R.id.ll_my_sex)
    LinearLayout llMySex;
    @BindView(R.id.tv_myInfo_birth)
    TextView tvMyInfoBirth;
    @BindView(R.id.ll_myInfo_head5)
    LinearLayout llMyInfoHead5;
    @BindView(R.id.ll_my_birth)
    LinearLayout llMyBirth;
    private SharedPreferences userAll;
    private UserPresenter userPresenter;
    private Uri tempUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userPresenter = new UserPresenter(this, this);
        userAll = getSharedPreferences("userAll", MODE_PRIVATE);
        gainUserInfo();

    }

    private void gainUserInfo() {
        String uid = userAll.getString("uid", null);
        if (!TextUtils.isEmpty(uid)) {
            Map<String, String> map = new HashMap<>();
            map.put("uid", uid);
            userPresenter.gainUserInfo(map);
        }

    }

    @Override
    public void gainFail() {

    }

    @Override
    public void gainSuc(String data) {

    }

    @Override
    public void loaginFail(String msg) {

    }

    @Override
    public void gainUserInfoFail() {

    }

    @Override
    public void gainUserSuc(String data) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);
        UserBean.DataBean data1 = userBean.getData();
        ivMyInfoSet.setOval(true);
        Glide.with(this.getApplicationContext())
                .load(data1.getIcon().toString())
               .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.mipmap.ic_launcher_round)
                .placeholder(R.mipmap.ic_launcher_round)
                .dontAnimate()
                .into(ivMyInfoSet);
        tvMyInfoUsername.setText(data1.getUsername());
        if(data1.getNickname()!=null)
        {
            tvMyInfoNick.setText(data1.getNickname().toString());
        }
        tvMyInfoBirth.setText(data1.getCreatetime());

        tvMyInfoSex.setText("男");
    }

    @Override
    public void gainaddSuc(String data) {

    }

    @Override
    public void unloadSuc(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unloadFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNickSuc(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        initData();
    }

    @OnClick({R.id.iv_cha, R.id.ll_my_head, R.id.ll_my_username, R.id.ll_my_nick, R.id.ll_my_sex, R.id.ll_my_birth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cha:
                finish();
                break;
            case R.id.ll_my_head:
showAlertDialog();
                break;
            case R.id.ll_my_username:
                break;
            case R.id.ll_my_nick:
setNickName();
                break;
            case R.id.ll_my_sex:
                break;
            case R.id.ll_my_birth:
                break;
        }
    }

    private void setNickName() {
        final EditText et_nick=new EditText(this);
        AlertDialog.Builder ab=new AlertDialog.Builder(this)
                .setTitle("修改昵称").setView(et_nick).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String uid = userAll.getString("uid", null);
                if(!TextUtils.isEmpty(uid))
                {
                    Map<String,String> map=new HashMap<String, String>();
                    map.put("uid",uid);
                    map.put("nickname",et_nick.getText().toString());
                    userPresenter.setNickName(map);
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ab.show();
    }

    private void showAlertDialog() {
        String[] items={"选择本地照片","拍照"};
        AlertDialog.Builder builder=new AlertDialog.Builder(MyInfoActivity.this)
                .setTitle("添加图片")
                .setNegativeButton("取消",null)
                .setItems(items, new DialogInterface.OnClickListener() {



                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent bendi=new Intent(Intent.ACTION_GET_CONTENT);
                                bendi.setType("image/*");
                                startActivityForResult(bendi,1000);
                                break;
                            case 1:
                                Intent came=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp_image.jpg"));
                                came.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                startActivityForResult(came,2000);
                                break;
                        }

                    }
                });
        builder.show();
    }

    public void setImageToView(Intent data){
        Bundle extras = data.getExtras();
        if(extras!=null)
        {

            bitmap = extras.getParcelable("data");
            ivMyInfoSet.setImageBitmap(bitmap);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            //把bitmap转化为byte[]
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] bytes = baos.toByteArray();
//修改当前图片
           ivMyInfoSet.setImageBitmap(bitmap);
            addFile(bytes);

        }
    }

    private void addFile(byte[] bytes) {
        FileOutputStream out=null;
        try {
            out= new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/userhead.png"));
            out.write(bytes);

           //输入发送头像
            String uid = userAll.getString("uid", null);
            if(!TextUtils.isEmpty(uid)) {
                Map<String,Object> map=new HashMap<>();
                map.put("uid",uid);
                map.put("file",new File(Environment.getExternalStorageDirectory()+"/userhead.png"));
                userPresenter.unload(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {

                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 裁剪图片
     * @param uri
     */
    public void cutImage(Uri uri){
        if(uri!=null)
        {
            tempUri=uri;
            Intent intent=new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri,"image/*");
            intent.putExtra("crop","true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY",1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 150);
            intent.putExtra("return-data",true);
            startActivityForResult(intent,3000);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==MyInfoActivity.RESULT_OK)
        {

            switch (requestCode)
            {
                case 1000:
                    cutImage(data.getData());
                    break;
                case 2000:
                    cutImage(tempUri);
                    break;
                case 3000:
                    if(data!=null)
                    {
                        setImageToView(data);
                    }
                    break;
            }

        }
    }
}
