package com.jiateng.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiateng.domain.Shop;

import java.util.List;

/**
 * @Description:
 * @Title: SerachResultAdapter
 * @ProjectName: orderFood
 * @date: 2023/4/13 13:55
 * @author: 骆家腾
 */
public class SearchResultAdapter extends BaseAdapter {
    private List<Shop> shops;

    public SearchResultAdapter() {
    }

    public SearchResultAdapter(List<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
