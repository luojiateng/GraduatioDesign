package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.jiateng.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PaidResultActivity extends Activity {

    @ViewInject(R.id.back_to_shopper)
    private Button backToShopper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_result);
        ViewUtils.inject(this);
        backToShopper.setOnClickListener(v -> {
//            startActivity(new Intent(PaidResultActivity.this,));
            finish();
        });
    }
}