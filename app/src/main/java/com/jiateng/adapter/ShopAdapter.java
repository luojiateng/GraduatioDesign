package com.jiateng.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiateng.R;
import com.jiateng.domain.Shop;

import java.util.List;

/**
 * @Description:
 * @Title: ShopAdapter
 * @ProjectName: orderFood
 * @date: 2023/1/11 0:26
 * @author: 骆家腾
 */
public class ShopAdapter extends BaseAdapter {
    private List<Shop> shops;

    public ShopAdapter() {
    }

    public ShopAdapter(List<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Object getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_shop, null);
        TextView tv_name = itemView.findViewById(R.id.list_item_shop_name);
        tv_name.setText(shops.get(position).getShopName());
        TextView tv_count = itemView.findViewById(R.id.list_item_shop_monthlySales);
        tv_count.setText(shops.get(position).getMonthlySales());
        TextView tv_space = itemView.findViewById(R.id.list_item_shop_space);
        String location = shops.get(position).getAddress().getProvince() + " " +
                shops.get(position).getAddress().getCity() + " " +
                shops.get(position).getAddress().getCounty() + " " +
                shops.get(position).getAddress().getSchoolName() + " " +
                shops.get(position).getAddress().getSpecificAddress();
        tv_space.setText(location);
        TextView tv_time = itemView.findViewById(R.id.list_item_shop_openTime);
        tv_time.setText(shops.get(position).getBeginTime());
        return itemView;
    }
}
