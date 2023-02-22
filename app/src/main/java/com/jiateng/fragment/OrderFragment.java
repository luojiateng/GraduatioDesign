package com.jiateng.fragment;


import static com.jiateng.common.config.Constants.ORDRE_STATUS_CANCEL;
import static com.jiateng.common.config.Constants.ORDRE_STATUS_FINISH;

import android.content.Intent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.jiateng.R;
import com.jiateng.activity.OrderInfoActivity;
import com.jiateng.activity.ShopActivity;
import com.jiateng.adapter.OrderAdapter;
import com.jiateng.bean.Order;
import com.jiateng.common.base.BaseFragment;
import com.jiateng.common.utils.RetrofitUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private RetrofitUtils retrofitUtils;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_order, null);
        ViewUtils.inject(this, view);
        retrofitUtils = RetrofitUtils.getInstance();
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
//        OrderApi orderApi = retrofitUtils.getApiService(OrderApi.class);
//        orderApi.getHistoryOrderByUserId("101").enqueue(new Callback<ResponseDate<Order>>() {
//            @Override
//            public void onResponse(Call<ResponseDate<Order>> call, Response<ResponseDate<Order>> response) {
//                System.out.println("success");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDate<Order>> call, Throwable t) {
//                System.out.println("error");
//            }
//        });
        orders = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            order.setShopName("户县软面" + (i + 1) + "店");
            order.setMoney(10.0);
            order.setStatus(i % 2 == 0 ? ORDRE_STATUS_FINISH : ORDRE_STATUS_CANCEL);
            order.setFinishTimeOfOrder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            orders.add(order);
        }
        orderListView.setAdapter(new OrderAdapter(orders, this));
        orderListView.setFriction(ViewConfiguration.getScrollFriction() * 3);
//        setListener();
    }


    @Override
    public void clickShopName(View view) {
        int index = (int) view.getTag();
        Order order = orders.get(index);
        startActivity(new Intent(context, ShopActivity.class));
    }

    @Override
    public void clickOrderInfo(View view) {
        int index = (int) view.getTag();
        Order order = orders.get(index);
        //TODO 设置Intent传递商店信息到下一个页面
        startActivity(new Intent(context, OrderInfoActivity.class));
    }
}
