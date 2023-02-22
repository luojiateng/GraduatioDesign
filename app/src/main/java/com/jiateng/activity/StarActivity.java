package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiateng.R;
import com.jiateng.common.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class StarActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.star_title)
    private AppTitleView starTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_star);
        ViewUtils.inject(this);
        starTitle.onClickTitleListener(v -> {
            finish();
        });

    }

    @Override
    public void onClick(View v) {
    }
}