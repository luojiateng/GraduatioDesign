package com.jiateng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiateng.R;
import com.jiateng.widget.OrderInfoView;
import com.jiateng.domain.ShoppingCart;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: OrderGoodsInfoAdapter
 * @ProjectName: orderFood
 * @date: 2023/4/21 16:22
 * @author: 骆家腾
 */
public class OrderGoodsInfoAdapter extends BaseAdapter {
    private ArrayList<ShoppingCart> shoppingCarts;
    private Context context;

    public OrderGoodsInfoAdapter(Context context, ArrayList<ShoppingCart> shoppingCarts) {
        this.context = context;
        this.shoppingCarts = shoppingCarts;

    }

    @Override
    public int getCount() {
        return shoppingCarts.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCarts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_goods, null);
        OrderInfoView item = view.findViewById(R.id.order_item_goods_info);
        ShoppingCart shoppingCart = shoppingCarts.get(position);
        item.setTv_front(shoppingCart.getGoods().getGoodName());
        item.setTv_mid("×" + shoppingCart.getGoodsCount() + "");
        item.setTv_back(getShopPrice(shoppingCart));
        return view;
    }

    private String getShopPrice(ShoppingCart shoppingCart) {
        double value = shoppingCart.getGoods().getPrice() * shoppingCart.getGoodsCount();
        return String.format("%.1f", value);
    }
}
