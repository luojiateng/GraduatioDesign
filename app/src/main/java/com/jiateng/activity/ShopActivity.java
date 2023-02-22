package com.jiateng.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jiateng.R;
import com.jiateng.bean.ShopInfo;
import com.jiateng.fragment.AppraiseFragment;
import com.jiateng.fragment.BusinessFragment;
import com.jiateng.fragment.ShopFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

public class ShopActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.shop_title_choose)
    private RadioGroup choose;
    @ViewInject(R.id.shop_title_goods)
    private RadioButton goods;
    private FragmentManager fragmentManager;
    @ViewInject(R.id.shop_title_img)
    private AppCompatImageView image;
    @ViewInject(R.id.shop_back)
    private ImageView back;
    @ViewInject(R.id.shop_share)
    private ImageView share;
    @ViewInject(R.id.shop_title_name)
    private TextView tv_shopName;
    @ViewInject(R.id.shop_collect)
    private ImageButton collect;
    private boolean isCollect = false;
    private String shopId;

    private ShopInfo shopInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ViewUtils.inject(this);
        Bundle bundle = getIntent().getExtras();
        shopInfo = (ShopInfo) bundle.getSerializable("shopInfo");

        shopId = shopInfo.getShopId();
        String shopName = shopInfo.getShopName();
        String shopImgUrl = shopInfo.getShopImgUrl();
        Picasso.get().load(shopImgUrl).into(image);
        tv_shopName.setText(shopName);

        fragmentManager = getSupportFragmentManager();
        choose.setOnCheckedChangeListener(this);
        goods.setChecked(true);
        changeFragment(new ShopFragment(shopInfo), false);
        back.setOnClickListener(v -> finish());
        collect.setBackgroundResource(R.drawable.ic_start);
        collect.setOnClickListener(v -> {
            if (!isCollect) {
                collect.setBackgroundResource(R.drawable.ic_home_fill);
                isCollect = true;
            } else {
                collect.setBackgroundResource(R.drawable.ic_start);
                isCollect = false;
            }
        });


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.shop_title_goods:
                changeFragment(new ShopFragment(shopInfo), true);
                break;
            case R.id.shop_title_appraise:
                changeFragment(new AppraiseFragment(), true);
                break;
            case R.id.shop_title_business:
                changeFragment(new BusinessFragment(), true);
                break;
            default:
                break;
        }
    }

    /**
     * 切换不同的Fragment
     *
     * @param fragment    传入的Fragment
     * @param isFirstInit 用来控制回退栈
     */
    private void changeFragment(Fragment fragment, boolean isFirstInit) {
        //fragment使用流程
        //1、开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //2、将默认占位的Fragment替换为传入的Fragment
        transaction.replace(R.id.shop_fragment, fragment);
        if (!isFirstInit) {
            //如果不是第一次识别，将加入回退栈null，消除重影问题
            transaction.addToBackStack(null);
        }
        //3、提交事务
        transaction.commit();
    }

    /**
     * 点击退出键直接退出到home页面，否则需要点击两次才能退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}