package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiateng.R;
import com.jiateng.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FeedbackActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.feedback_title)
    private AppTitleView feedbackTitle;
    @ViewInject(R.id.zhidaol)
    private Button zhidaole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);
        ViewUtils.inject(this);
        feedbackTitle.onClickTitleListener(v -> {
            finish();
        });
        zhidaole.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void onClick(View v) {
    }
}