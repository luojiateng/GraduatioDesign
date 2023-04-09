package com.jiateng.activity;

import static com.jiateng.utils.ToastUtil.ToastShow;

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
import com.jiateng.domain.Shop;
import com.jiateng.domain.StoreBean;
import com.jiateng.fragment.AppraiseFragment;
import com.jiateng.fragment.BusinessFragment;
import com.jiateng.fragment.ShopFragment;
import com.jiateng.retrofit.api.ShopApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Integer shopId;

    private Shop shop;
    private ArrayList<StoreBean.Goods> goodsList;
    private String shopPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ViewUtils.inject(this);
        Bundle bundle = getIntent().getExtras();
        shop = (Shop) bundle.getSerializable("shop");

        shopId = shop.getShopId();
        String shopName = shop.getShopName();
        String shopImgUrl = shop.getShopImageUrl();
        Picasso.get().load(shopImgUrl).into(image);
        tv_shopName.setText(shopName);
        fragmentManager = getSupportFragmentManager();
        choose.setOnCheckedChangeListener(this);
        goods.setChecked(true);
        RetrofitManager.getInstance().getApiService("shop/", ShopApi.class).getShopGoods(shopId).enqueue(new Callback<ResponseResult<ArrayList<StoreBean.Goods>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<StoreBean.Goods>>> call, Response<ResponseResult<ArrayList<StoreBean.Goods>>> response) {
                ArrayList<StoreBean.Goods> result = ResultUtil.getResult(response);
                shopPhoneNumber = result.get(0).getShop().getShopPhone();
                changeFragment(new ShopFragment(result), false);
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<StoreBean.Goods>>> call, Throwable t) {
                ToastShow("请检查网络");
            }
        });
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
                RetrofitManager.getInstance().getApiService("shop/", ShopApi.class).getShopGoods(shopId).enqueue(new Callback<ResponseResult<ArrayList<StoreBean.Goods>>>() {
                    @Override
                    public void onResponse(Call<ResponseResult<ArrayList<StoreBean.Goods>>> call, Response<ResponseResult<ArrayList<StoreBean.Goods>>> response) {
                        ArrayList<StoreBean.Goods> result = ResultUtil.getResult(response);
                        shopPhoneNumber = result.get(0).getShop().getShopPhone();
                        changeFragment(new ShopFragment(result), true);
                    }

                    @Override
                    public void onFailure(Call<ResponseResult<ArrayList<StoreBean.Goods>>> call, Throwable t) {
                        ToastShow("请检查网络");
                    }
                });
                break;
            case R.id.shop_title_appraise:
                changeFragment(new AppraiseFragment(), true);
                break;
            case R.id.shop_title_business:
                changeFragment(new BusinessFragment(shopPhoneNumber), true);
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