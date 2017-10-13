package zhanghegang.com.bawei.date0928jingdong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import zhanghegang.com.bawei.date0928jingdong.R;

/**
 * Created by asus on 2017/10/12.
 */

public class Fragment_say extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fl_shopdetail_shop,null);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        String shopid = getArguments().getString("shopid", null);
        if(!TextUtils.isEmpty(shopid))
        {
            Toast.makeText(getActivity(), "shopsay", Toast.LENGTH_SHORT).show();
        }
    }
}
