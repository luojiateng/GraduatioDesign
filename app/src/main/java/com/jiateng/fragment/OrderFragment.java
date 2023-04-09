package com.jiateng.fragment;


import static com.jiateng.config.Constants.ORDRE_STATUS_CANCEL;
import static com.jiateng.config.Constants.ORDRE_STATUS_FINISH;

import android.content.Intent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.jiateng.R;
import com.jiateng.activity.OrderInfoActivity;
import com.jiateng.activity.ShopActivity;
import com.jiateng.adapter.OrderAdapter;
import com.jiateng.common.base.BaseFragment;
import com.jiateng.domain.Order;
import com.jiateng.domain.User;
import com.jiateng.utils.MockData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

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

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_order, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        orders = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            order.setOrderId(i);
            order.setUser(new User(1, "nickname", "admin", "admin", "", "", "", null));
            order.setShop(MockData.getShopInfoList().get(1));
            order.setAddress(MockData.address);
            order.setShoppingCartList(null);
            order.setStatus("over");
            order.setMoney(10.0);
            order.setStartTimeOfOrder("2023-04-03");
            order.setStatus(i % 2 == 0 ? ORDRE_STATUS_FINISH : ORDRE_STATUS_CANCEL);
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
