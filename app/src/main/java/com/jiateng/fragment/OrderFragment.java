package com.jiateng.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiateng.R;
import com.jiateng.activity.OrderInfoActivity;
import com.jiateng.activity.ShopActivity;
import com.jiateng.adapter.OrderAdapter;
import com.jiateng.base.BaseFragment;
import com.jiateng.domain.Order;
import com.jiateng.retrofit.api.OrderApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Description:
 * @Title: OrderFragment
 * @ProjectName: orderFood
 * @date: 2023/1/10 17:44
 * @author: 骆家腾
 */
public class OrderFragment extends BaseFragment implements OrderAdapter.OrderItemClickCallBack {
    @ViewInject(R.id.list_item_order_history)
    private ListView orderListView;
    private ArrayList<Order> orders;
    private OrderAdapter adapter;
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;
    @ViewInject(R.id.tip_hiden_order)
    private TextView hidenText;

    @SneakyThrows
    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_order, null);
        ViewUtils.inject(this, view);
        super.initData();
        return view;
    }

    @SneakyThrows
    @Override
    protected void initData() {
        super.initData();
    }


    @Override
    public void clickShopName(View view, int position) {
        Intent intent = new Intent(context, ShopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("shop", orders.get(position).getShop());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void clickOrderInfo(View view, int position) {
        Intent intent = new Intent(context, OrderInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orders.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        RetrofitManager.getInstance().getApiService("/order", OrderApi.class).getCurrentUserAllOrders().enqueue(new Callback<ResponseResult<ArrayList<Order>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<Order>>> call, Response<ResponseResult<ArrayList<Order>>> response) {
                ArrayList<Order> result = ResultUtil.getResult(response);
                orders = result;
                adapter = new OrderAdapter(result, OrderFragment.this);
                orderListView.setAdapter(adapter);
                orderListView.setFriction(ViewConfiguration.getScrollFriction() * 3);
                progressBar.setVisibility(View.GONE);
                if (result.size() == 0) {
                    hidenText.setVisibility(View.VISIBLE);
                } else {
                    hidenText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<Order>>> call, Throwable t) {

            }
        });
    }
}
