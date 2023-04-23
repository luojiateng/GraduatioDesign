package com.jiateng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.domain.Shop;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @Description:
 * @Title: ShopAdapter
 * @ProjectName: orderFood
 * @date: 2023/1/11 0:26
 * @author: 骆家腾
 */
public class ShopAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Shop> shops;
    private HomeFragmentAdapter.MyOnClickListener myOnClickListener;

    public ShopAdapter() {
    }

    public ShopAdapter(List<Shop> shops, Context context) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_home_shop, null);
        return new ShopAdapter.ShopHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopHolder shopHolder = (ShopHolder) holder;
        shopHolder.setData(shops.get(position), position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }


    private class ShopHolder extends RecyclerView.ViewHolder {
        private ImageView shopImg;
        private TextView shopName;
        private TextView monthlySales;
        private TextView shopSpace;
        private TextView shopOpenTime;

        private View view;

        public ShopHolder(Context context, @NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            shopImg = itemView.findViewById(R.id.shop_img);
            shopName = itemView.findViewById(R.id.star_shop_name);
            monthlySales = itemView.findViewById(R.id.star_shop_monthlySales);
            shopSpace = itemView.findViewById(R.id.star_shop_space);
            shopOpenTime = itemView.findViewById(R.id.star_shop_openTime);
        }

        public void setData(Shop shop, int position) {
            shopImg.setTag(position);
            Picasso.get().load(shop.getShopImageUrl()).fit().into(shopImg);
            shopName.setTag(position);
            shopName.setText(shop.getShopName());
            monthlySales.setTag(position);
            monthlySales.setText(shop.getMonthlySales().toString());
            shopSpace.setTag(position);
            shopSpace.setText(shop.getAddress().getSpecific());
            shopOpenTime.setTag(position);
            shopOpenTime.setText(shop.getBeginTime() + " " + shop.getEndTime());

            view.setOnClickListener(v -> {
                if (myOnClickListener != null) {
                    myOnClickListener.viewClick(view, position);
                }
            });
        }
    }

    public void setMyOnClickListener(HomeFragmentAdapter.MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    /**
     * 点击事件回调接口.
     */
    public interface MyOnClickListener {
        void viewClick(View view, int position);
    }
}
