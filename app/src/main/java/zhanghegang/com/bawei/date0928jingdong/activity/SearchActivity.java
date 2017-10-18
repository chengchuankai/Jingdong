package zhanghegang.com.bawei.date0928jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhanghegang.com.bawei.date0928jingdong.R;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.sv_data)
    SearchView svData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        svData.setIconifiedByDefault(false);
        svData.setSubmitButtonEnabled(true);
        svData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent();
                intent.putExtra("key",query);

                SearchActivity.this.setResult(1000,intent);
                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
