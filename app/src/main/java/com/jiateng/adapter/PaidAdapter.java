package com.jiateng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.domain.ShoppingCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: PaidAdapter
 * @ProjectName: orderFood
 * @date: 2023/4/13 21:56
 * @author: 骆家腾
 */
public class PaidAdapter extends RecyclerView.Adapter {
    private ArrayList<ShoppingCart> goodsArrayList;
    private Context context;

    public PaidAdapter() {
    }

    public PaidAdapter(ArrayList<ShoppingCart> goodsArrayList, Context context) {
        this.goodsArrayList = goodsArrayList;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_paid_goods, null);
        return new PaidAdapter.PaidHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PaidHolder paidHolder = (PaidHolder) holder;
        paidHolder.setData(goodsArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return goodsArrayList.size();
    }

    private class PaidHolder extends RecyclerView.ViewHolder {
        private TextView goodsName;
        private AppCompatImageView imageView;
        private TextView goodsCount;
        private TextView sumMoney;
        private View view;

        public PaidHolder(Context context, View itemView) {
            super(itemView);
            this.view = itemView;
            goodsName = itemView.findViewById(R.id.paid_goods_name);
            imageView = itemView.findViewById(R.id.paid_goods_icon);
            goodsCount = itemView.findViewById(R.id.paid_goods_cound);
            sumMoney = itemView.findViewById(R.id.goods_count_money);
        }

        public void setData(ShoppingCart shoppingCart, int position) {
            imageView.setTag(position);
            Picasso.get().load(shoppingCart.getGoods().getGoodsImageUrl()).fit().into(imageView);
            goodsName.setTag(position);
            goodsName.setText(shoppingCart.getGoods().getGoodName());
            goodsCount.setTag(position);
            goodsCount.setText(shoppingCart.getGoodsCount()+"");
            sumMoney.setTag(position);
            sumMoney.setText(String.format("%.1f", shoppingCart.getGoodsCount() * shoppingCart.getGoods().getPrice()));
        }
    }


}
