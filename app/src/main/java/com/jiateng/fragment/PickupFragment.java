package com.jiateng.fragment;

import android.view.View;
import android.widget.TextView;

import com.jiateng.R;
import com.jiateng.base.BaseFragment;
import com.jiateng.domain.Address;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @Description:
 * @Title: PickupFragment
 * @ProjectName: orderFood
 * @date: 2023/4/14 11:29
 * @author: 骆家腾
 */
public class PickupFragment extends BaseFragment {
    @ViewInject(R.id.address_shop_get)
    private TextView shopAdderssTv;

    private Address address;


    public PickupFragment(Address shopAddress) {
        super();
        this.address = shopAddress;
    }

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_pickup, null);
        ViewUtils.inject(this, view);
        shopAdderssTv.setText(address.toString());
        return view;
    }
}
