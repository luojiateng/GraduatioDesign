package com.jiateng.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.jiateng.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import com.jiateng.common.widget.AppTitleView;

public class OrderInfoActivity extends Activity {

    @ViewInject(R.id.callBossPhone)
    private Button callBoss;

    @ViewInject(R.id.title_orderInfo)
    private AppTitleView titleOrderInfo;

    @ViewInject(R.id.order_status_checkout)
    private Button checkoutOrderStatus;

    @ViewInject(R.id.order_item_info)
    private TextView shopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        ViewUtils.inject(this);
        initViewFunction();
    }

    public void initViewFunction() {
        callBoss.setOnClickListener(v -> {
            //TODO 设置电话号，获取上个界面传过来的值
            String phoneNo = "12345";
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:" + phoneNo);
            intent.setData(uri);
            startActivity(intent);
        });

        titleOrderInfo.onClickTitleListener(v -> {
            finish();
        });
        checkoutOrderStatus.setOnClickListener(v -> {

        });
        shopInfo.setOnClickListener(v -> {
            startActivity(new Intent(OrderInfoActivity.this, ShopActivity.class));
        });
    }

}