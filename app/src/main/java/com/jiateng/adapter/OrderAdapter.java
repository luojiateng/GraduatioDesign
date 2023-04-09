package com.jiateng.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiateng.R;
import com.jiateng.domain.Order;

import java.util.List;

/**
 * @Description:
 * @Title: OrderAdapter
 * @ProjectName: orderFood
 * @date: 2023/1/11 12:59
 * @author: 骆家腾
 */
public class OrderAdapter extends BaseAdapter {
    private List<Order> orderList;
    private TextView shopName;
    private TextView orderInfo;
    private OrderItemClickCallBack orderItemClickCallBack;


    public OrderAdapter() {
    }

    public OrderAdapter(List<Order> orderList, OrderItemClickCallBack orderItemClickCallBack) {
        this.orderItemClickCallBack = orderItemClickCallBack;
        this.orderList = orderList;
    }

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }


    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_order, null);

        TextView tv_name = itemView.findViewById(R.id.order_item_shopInfo);
        tv_name.setText(orderList.get(position).getShop().getShopName());
        tv_name.setTag(position);

        TextView tv_paid = itemView.findViewById(R.id.order_item_pay);
        tv_paid.setText(orderList.get(position).getMoney() + "");
        tv_paid.setTag(position);

        TextView tv_status = itemView.findViewById(R.id.order_item_status);
        tv_status.setText(orderList.get(position).getStatus());
        tv_status.setTag(position);

        TextView tv_time = itemView.findViewById(R.id.order_item_time);
        tv_time.setText(orderList.get(position).getStartTimeOfOrder());
        tv_time.setTag(position);

        shopName = itemView.findViewById(R.id.order_item_shopInfo);
//        shopName.setText(); TODO
        shopName.setTag(position);

        orderInfo = itemView.findViewById(R.id.order_item_click);
        orderInfo.setTag(position);

        shopName.setOnClickListener(v -> {
            orderItemClickCallBack.clickShopName(v);
        });
        orderInfo.setOnClickListener(v -> {
            //接口回调为内部文本绑定监听事件
            orderItemClickCallBack.clickOrderInfo(v);
        });
        return itemView;
    }

    public interface OrderItemClickCallBack {
        void clickShopName(View view);

        void clickOrderInfo(View view);
    }
}
