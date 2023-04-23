package com.jiateng.activity;

import static com.jiateng.utils.SharedPreferencesUtil.hasToken;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jiateng.R;
import com.jiateng.db.impl.UserCollectDaoImpl;
import com.jiateng.domain.Shop;
import com.jiateng.domain.UserCollect;
import com.jiateng.fragment.HomeFragment;
import com.jiateng.fragment.OrderFragment;
import com.jiateng.fragment.UserFragment;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.SharedPreferencesUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author LuoJiateng
 */
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.main_index)
    private RadioGroup main_index;
    @ViewInject(R.id.main_home)
    private RadioButton main_home;
    @ViewInject(R.id.main_order)
    private RadioButton main_order;
    @ViewInject(R.id.main_user)
    private RadioButton main_user;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        fragmentManager = getSupportFragmentManager();
        main_index.setOnCheckedChangeListener(this);
        main_home.setChecked(true);
        changeFragment(new HomeFragment(), false);
        if (hasToken()) {
            initUserCollect();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                changeFragment(new HomeFragment(), true);
                break;
            case R.id.main_order:
                changeFragment(new OrderFragment(), true);
                break;
            case R.id.main_user:
                changeFragment(new UserFragment(), true);
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
        transaction.replace(R.id.main_fragment, fragment);
        if (!isFirstInit) {
            //如果不是第一次识别，将加入回退栈null，消除重影问题
            transaction.addToBackStack(null);
        }
        //3、提交事务
        transaction.commit();
    }

    public void initUserCollect() {
        UserCollectDaoImpl instance = UserCollectDaoImpl.getInstance(MainActivity.this);
        RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserFavouriteShops().enqueue(new Callback<ResponseResult<ArrayList<Shop>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<Shop>>> call, Response<ResponseResult<ArrayList<Shop>>> response) {
                ArrayList<Shop> shops = ResultUtil.getResult(response);
                Integer userId = SharedPreferencesUtil.getInt(MainActivity.this, "userId", 0);
                ArrayList<UserCollect> collects = (ArrayList<UserCollect>) shops.stream().map(shop -> new UserCollect(userId, shop.getShopId())).collect(Collectors.toList());
                instance.putUserCollectShops(collects);
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<Shop>>> call, Throwable t) {

            }
        });
    }

}