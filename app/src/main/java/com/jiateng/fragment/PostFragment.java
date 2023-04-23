package com.jiateng.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jiateng.R;
import com.jiateng.activity.AddressActivity;
import com.jiateng.base.BaseFragment;
import com.jiateng.domain.AddressInfo;
import com.jiateng.utils.SharedPreferencesUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: PostFragment
 * @ProjectName: orderFood
 * @date: 2023/4/14 11:30
 * @author: 骆家腾
 */
public class PostFragment extends BaseFragment {

    private ArrayList<AddressInfo> address;
    @ViewInject(R.id.shop_address_info)
    private TextView shopAdderssTv;
    @ViewInject(R.id.user_name)
    private TextView usernameTV;
    @ViewInject(R.id.user_phone_number)
    private TextView phoneNumberTv;
    @ViewInject(R.id.set_address)
    private ImageView setAddress;

    public PostFragment(ArrayList<AddressInfo> address) {
        super();
        this.address = address;
    }

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_post, null);
        ViewUtils.inject(this, view);
        int userId = SharedPreferencesUtil.getInt(context, "userId", 0);
        int index = SharedPreferencesUtil.getInt(context, "currentUserDefaultIAddressIndex-" + userId, 0);
        shopAdderssTv.setText(address.get(index).getAddressInfo());
        usernameTV.setText(address.get(index).getUserName());
        phoneNumberTv.setText(address.get(index).getUserPhone());
        SharedPreferencesUtil.putInt(context, "currentUserAddressId-" + userId, address.get(index).getAddressId());
        setAddress.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddressActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("fromPostFragment", "yes");
            intent.putExtras(bundle);
            startActivityForResult(intent, 2);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    ArrayList<AddressInfo> addressInfo = (ArrayList<AddressInfo>) bundle.getSerializable("addressInfo");
                    int userId = SharedPreferencesUtil.getInt(context, "userId", 0);
                    int index = SharedPreferencesUtil.getInt(context, "currentUserDefaultIAddressIndex-" + userId, 0);
                    SharedPreferencesUtil.putInt(context, "currentUserAddressId-" + userId, addressInfo.get(index).getAddressId());
                    shopAdderssTv.setText(addressInfo.get(index).getAddressInfo());
                    usernameTV.setText(addressInfo.get(index).getUserName());
                    phoneNumberTv.setText(addressInfo.get(index).getUserPhone());
                }
        }
    }
}
