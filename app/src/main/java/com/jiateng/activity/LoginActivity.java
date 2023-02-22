package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiateng.R;
import com.jiateng.common.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LoginActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.login_title)
    private AppTitleView loginTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ViewUtils.inject(this);
        loginTitle.onClickTitleListener(v -> {
            finish();
        });
    }

    @Override
    public void onClick(View v) {
    }
}