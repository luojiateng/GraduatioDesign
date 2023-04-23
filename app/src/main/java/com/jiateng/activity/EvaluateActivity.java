package com.jiateng.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jiateng.R;
import com.jiateng.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EvaluateActivity extends AppCompatActivity {
    @ViewInject(R.id.evaluate)
    private AppTitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ViewUtils.inject(this);
        titleView.setOnClickListener(v -> {
            finish();
        });
    }
}