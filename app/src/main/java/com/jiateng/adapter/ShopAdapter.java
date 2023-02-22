package com.jiateng.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiateng.R;

import java.util.List;

import com.jiateng.bean.ShopInfo;

/**
 * @Description:
 * @Title: ShopAdapter
 * @ProjectName: orderFood
 * @date: 2023/1/11 0:26
 * @author: 骆家腾
 */
public class ShopAdapter extends BaseAdapter {
    private List<ShopInfo> shopInfos;

    public ShopAdapter() {
    }
    public ShopAdapter(List<ShopInfo> shopInfos) {
        this.shopInfos = shopInfos;
    }

    @Override
    public int getCount() {
        return shopInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return shopInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_shop, null);
        TextView tv_name = itemView.findViewById(R.id.list_item_shop_name);
        tv_name.setText(shopInfos.get(position).getShopName());
        TextView tv_count = itemView.findViewById(R.id.list_item_shop_monthlySales);
        tv_count.setText(shopInfos.get(position).getMonthlySales());
        TextView tv_space = itemView.findViewById(R.id.list_item_shop_space);
        tv_space.setText(shopInfos.get(position).getLocation());
        TextView tv_time = itemView.findViewById(R.id.list_item_shop_openTime);
        tv_time.setText(shopInfos.get(position).getTime());
        return itemView;
    }
}
