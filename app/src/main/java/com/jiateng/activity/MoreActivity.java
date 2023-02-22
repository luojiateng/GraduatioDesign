package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiateng.R;
import com.jiateng.common.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MoreActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.more_title)
    private AppTitleView moreTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_more);
        ViewUtils.inject(this);
        moreTitle.onClickTitleListener(v -> {
            finish();
        });
    }

    @Override
    public void onClick(View v) {
    }
}