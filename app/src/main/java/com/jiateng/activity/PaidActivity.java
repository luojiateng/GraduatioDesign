package com.jiateng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.adapter.PaidAdapter;
import com.jiateng.db.impl.ShoppingCartDaoImpl;
import com.jiateng.domain.Address;
import com.jiateng.domain.AddressInfo;
import com.jiateng.domain.Order;
import com.jiateng.domain.Shop;
import com.jiateng.domain.ShoppingCart;
import com.jiateng.domain.StoreBean;
import com.jiateng.domain.User;
import com.jiateng.fragment.PickupFragment;
import com.jiateng.fragment.PostFragment;
import com.jiateng.retrofit.api.AddressApi;
import com.jiateng.retrofit.api.OrderApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.utils.ToastUtil;
import com.jiateng.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Description:
 * @Title: PaidActivity
 * @ProjectName: orderFood
 * @date: 2023/4/13 21:56
 * @author: 骆家腾
 */
public class PaidActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.commit)
    private TextView commitButton;
    @ViewInject(R.id.paid_list)
    private RecyclerView recyclerView;
    @ViewInject(R.id.paid_choose)
    private RadioGroup allChoose;
    @ViewInject(R.id.paid_post)
    private RadioButton postWay;
    @ViewInject(R.id.paid_pickup)
    private RadioButton pickupWay;
    private FragmentManager fragmentManager;
    @ViewInject(R.id.paid_order_title)
    private AppTitleView titleView;
    @ViewInject(R.id.money_count)
    private TextView allMoneyView;
    @ViewInject(R.id.money)
    private TextView allMonenyResult;
    @ViewInject(R.id.comment)
    private RelativeLayout comment;
    private Integer userId;
    private Integer shopId;
    private Integer addressId;
    private ShoppingCartDaoImpl shoppingCartDao;
    private PaidAdapter adapter;
    private ArrayList<ShoppingCart> willPaidGoods;
    private String commentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid);
        ViewUtils.inject(this);
        shoppingCartDao = ShoppingCartDaoImpl.getInstance();
        Bundle bundle = getIntent().getExtras();
        willPaidGoods = (ArrayList<ShoppingCart>) bundle.getSerializable("willPaidGoods");
        shopId = willPaidGoods.get(0).getShop().getShopId();
        userId = willPaidGoods.get(0).getUser().getUserId();
        fragmentManager = getSupportFragmentManager();
        allChoose.setOnCheckedChangeListener(this);
        postWay.setChecked(true);
        SharedPreferencesUtil.putString(PaidActivity.this, "postMethod" + userId, "post");
        RetrofitManager.getInstance().getApiService("/address", AddressApi.class).getUserAddress().enqueue(new Callback<ResponseResult<ArrayList<AddressInfo>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<AddressInfo>>> call, Response<ResponseResult<ArrayList<AddressInfo>>> response) {
                ArrayList<AddressInfo> userAddress = ResultUtil.getResult(response);
                if (userAddress != null && userAddress.size() != 0) {
                    changeFragment(new PostFragment(userAddress), false);
                } else {
                    ToastUtil.ToastShow("请设置地址");
                    Intent intent = new Intent(PaidActivity.this, AddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fromPostFragment", "yes");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<AddressInfo>>> call, Throwable t) {
                ToastUtil.intenetErrorNotification();
            }
        });
        comment.setOnClickListener(v -> {
            Intent intent = new Intent(PaidActivity.this, CommentActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("userId", userId);
            b.putSerializable("shopId", shopId);
            intent.putExtras(b);
            startActivity(intent);
        });
        allMoneyView.setText(fixMoney(willPaidGoods));
        allMonenyResult.setText(fixMoney(willPaidGoods));
        adapter = new PaidAdapter(willPaidGoods, PaidActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //发起支付之后，销毁结算页面，清空购物车数据
        commitButton.setOnClickListener(v -> {
            commentInfo = SharedPreferencesUtil.getString(PaidActivity.this, "comment:" + userId + shopId, "无");
            addressId = SharedPreferencesUtil.getInt(PaidActivity.this, "currentUserAddressId-" + userId, -1);
            Order order = new Order();
            order.setUser(new User(userId));
            order.setShop(new Shop(shopId));
            AddressInfo addressInfo = new AddressInfo(addressId);
            order.setAddressInfo(addressInfo);
            order.setStatus("订单创建");
            order.setShoppingCartList(willPaidGoods);
            order.setMoney(Double.parseDouble(fixMoney(willPaidGoods)));
            order.setStartTimeOfOrder(new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss").format(new Date()));
            order.setRemark(commentInfo);
            String type = SharedPreferencesUtil.getString(PaidActivity.this, "postMethod" + userId, "post");
            order.setType(type);
            RetrofitManager.getInstance().getApiService("/order", OrderApi.class).createOrder(order).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Boolean ms = ResultUtil.getResultMsg(response);
                    startActivity(new Intent(PaidActivity.this, PaidResultActivity.class));
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ToastUtil.intenetErrorNotification();
                }
            });
            shoppingCartDao.clean(userId, this.shopId);
            SharedPreferencesUtil.remove(PaidActivity.this, "comment:" + userId + shopId);
        });
        recyclerView.setAdapter(adapter);
        titleView.setOnClickListener(v -> finish());
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.paid_post:
                SharedPreferencesUtil.putString(PaidActivity.this, "postMethod" + userId, "post");
                StoreBean.Goods goods = new StoreBean.Goods(999999, null, "https://img0.baidu.com/it/u=2482761802,1951729406&fm=253&fmt=auto&app=138&f=JPG?w=625&h=500", "外卖配送服务", "", 2.0, 1);
                ShoppingCart needPost = new ShoppingCart(new User(userId), new Shop(shopId), goods, 1);
                willPaidGoods.add(needPost);
                adapter = new PaidAdapter(willPaidGoods, PaidActivity.this);
                recyclerView.setAdapter(adapter);
                allMoneyView.setText(fixMoney(willPaidGoods));
                allMonenyResult.setText(fixMoney(willPaidGoods));
                RetrofitManager.getInstance().getApiService("/address", AddressApi.class).getUserAddress().enqueue(new Callback<ResponseResult<ArrayList<AddressInfo>>>() {
                    @Override
                    public void onResponse(Call<ResponseResult<ArrayList<AddressInfo>>> call, Response<ResponseResult<ArrayList<AddressInfo>>> response) {
                        ArrayList<AddressInfo> userAddress = ResultUtil.getResult(response);
                        if (userAddress != null && userAddress.size() != 0) {
                            changeFragment(new PostFragment(userAddress), true);
                        } else {
                            ToastUtil.ToastShow("请设置地址");
                            Intent intent = new Intent(PaidActivity.this, AddressActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("fromPostFragment", "yes");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseResult<ArrayList<AddressInfo>>> call, Throwable t) {
                    }
                });
                break;
            case R.id.paid_pickup:
                SharedPreferencesUtil.putString(PaidActivity.this, "postMethod" + userId, "pickup");
                willPaidGoods.remove(willPaidGoods.size() - 1);
                adapter = new PaidAdapter(willPaidGoods, PaidActivity.this);
                recyclerView.setAdapter(adapter);
                allMoneyView.setText(fixMoney(willPaidGoods));
                allMonenyResult.setText(fixMoney(willPaidGoods));
                RetrofitManager.getInstance().getApiService("/address", AddressApi.class).getShopAddress(shopId).enqueue(new Callback<ResponseResult<Address>>() {
                    @Override
                    public void onResponse(Call<ResponseResult<Address>> call, Response<ResponseResult<Address>> response) {
                        Address shopAddress = ResultUtil.getResult(response);
                        changeFragment(new PickupFragment(shopAddress), true);
                    }

                    @Override
                    public void onFailure(Call<ResponseResult<Address>> call, Throwable t) {
                        ToastUtil.intenetErrorNotification();
                    }
                });
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
        transaction.replace(R.id.methodsChoose, fragment);
        if (!isFirstInit) {
            //如果不是第一次识别，将加入回退栈null，消除重影问题
            transaction.addToBackStack(null);
        }
        //3、提交事务
        transaction.commit();
    }

    public String fixMoney(ArrayList<ShoppingCart> shoppingCartArrayList) {
        Double allMoney = 0.0;
        for (ShoppingCart shoppingCart : shoppingCartArrayList) {
            allMoney += shoppingCart.getGoods().getPrice() * shoppingCart.getGoodsCount();
        }
        return String.format("%.1f", allMoney);
    }
}