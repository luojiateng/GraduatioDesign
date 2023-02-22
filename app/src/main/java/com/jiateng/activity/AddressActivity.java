package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiateng.R;
import com.jiateng.common.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AddressActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.address_title)
    private AppTitleView addressTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        ViewUtils.inject(this);
        addressTitle.onClickTitleListener(v -> {
            finish();
        });
    }

    @Override
    public void onClick(View v) {
    }
}