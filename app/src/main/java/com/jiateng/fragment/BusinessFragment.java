package com.jiateng.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.jiateng.R;
import com.jiateng.common.base.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @Description:
 * @Title: BusinessFragment
 * @ProjectName: orderFood
 * @date: 2023/2/7 16:53
 * @author: 骆家腾
 */
public class BusinessFragment extends BaseFragment {
    private String phoneNumber;
    @ViewInject(R.id.shop_business_phone)
    private ImageView phone;

    public BusinessFragment(String shopPhoneNumber) {
        this.phoneNumber = shopPhoneNumber;
    }

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_business, null);
        ViewUtils.inject(this, view);
        init();
        return view;
    }

    private void init() {
        phone.setOnClickListener(v -> {
            String phoneNo = phoneNumber;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:" + phoneNo);
            intent.setData(uri);
            startActivity(intent);
        });
    }
}
