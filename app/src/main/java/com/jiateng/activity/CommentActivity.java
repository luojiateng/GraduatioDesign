package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jiateng.R;
import com.jiateng.widget.AppTitleView;
import com.jiateng.utils.SharedPreferencesUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 备注功能
 */
public class CommentActivity extends Activity {
    @ViewInject(R.id.comment_title)
    private AppTitleView titleView;
    @ViewInject(R.id.comment_info)
    private EditText commentInfo;
    @ViewInject(R.id.comment_commit)
    private Button submit;

    private Integer userId;
    private Integer shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ViewUtils.inject(this);
        Bundle bundle = getIntent().getExtras();
        userId = (Integer) bundle.getSerializable("userId");
        shopId = (Integer) bundle.getSerializable("shopId");
        titleView.onClickTitleListener(v -> {
            finish();
        });
        String string = SharedPreferencesUtil.getString(CommentActivity.this, "comment:" + userId + shopId, "");
        System.out.println();
        if (!"".equals(string)) {
            commentInfo.setText(string);
        }
        submit.setOnClickListener(v -> {
            String info = commentInfo.getText().toString().trim();
            SharedPreferencesUtil.putString(CommentActivity.this, "comment:" + userId + shopId, info);
            finish();
        });

    }
}