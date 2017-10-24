package zhanghegang.com.bawei.date0928jingdong.auto_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import zhanghegang.com.bawei.date0928jingdong.R;

/**
 * Created by asus on 2017/10/18.
 */

public class AdPonitView extends LinearLayout implements TextWatcher, View.OnClickListener {

    private EditText etAmount;
    private Button btnDecrease;
    private Button btnIncrease;
    private int btnWidth;
    private int tvWidth;
    private int tvTextSize;
    private int btnTextSize;
    private OnAmountChangeListener mListener;
    private int goods_storage=10;
    private int amount = 1; //购买数量
    public AdPonitView(Context context) {
        this(context,null);
    }

    public AdPonitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdPonitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    public void setAmountNum(int num){
        amount=num;
        etAmount.setText(amount+"");

    }

    private void init(Context context, AttributeSet attrs) {
       LayoutInflater.from(context).inflate(R.layout.point,this);
        etAmount =findViewById(R.id.etAmount);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        //获得attrs
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AdPonitView);
        btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AdPonitView_btnWidth, LayoutParams.WRAP_CONTENT);
        tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AdPonitView_tvWidth, 80);
        tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AdPonitView_tvTextSize, 0);
        btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AdPonitView_btnTextSize, 0);
        obtainStyledAttributes.recycle();
        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }
        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }

    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if(TextUtils.isEmpty(editable.toString()))
        {
            return;
        }
        amount=Integer.valueOf(editable.toString());
        if(amount>goods_storage)
        {
            etAmount.setText(goods_storage + "");
            return;
        }
        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDecrease:
                if(amount>1)
                {
                    amount--;
                    etAmount.setText(amount + "");
                }
                break;
            case R.id.btnIncrease:
                if (amount < goods_storage) {
                    amount++;
                    etAmount.setText(amount + "");
                }
                break;
        }
        etAmount.clearFocus();

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }

    }
    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

}
