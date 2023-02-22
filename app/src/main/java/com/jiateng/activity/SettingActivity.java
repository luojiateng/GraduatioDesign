package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiateng.R;
import com.jiateng.common.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SettingActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.setting_title_id)
    private AppTitleView settingTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ViewUtils.inject(this);
        //为返回键设置监听事件
        settingTitle.onClickTitleListener(v -> {
            finish();
        });

    }

    @Override
    public void onClick(View v) {
    }
}