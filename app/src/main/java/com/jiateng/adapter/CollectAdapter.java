package com.jiateng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.domain.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: StartAdapter
 * @date: 2023/4/14 20:48
 * @author: 骆家腾
 */
public class CollectAdapter extends RecyclerView.Adapter {
    private ArrayList<Shop> shopArrayList;
    private Context context;

    public CollectAdapter() {
    }

    public CollectAdapter(ArrayList<Shop> shopArrayList, Context context) {
        this.shopArrayList = shopArrayList;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_shop_collect, null);
        return new StartHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StartHolder startHolder = (StartHolder) holder;
        startHolder.setData(shopArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return shopArrayList.size();
    }

    private class StartHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView starImage;
        private ImageView starCancel;
        private TextView starShopName;
        private TextView starShopMonthlySales;
        private TextView starShopSpace;
        private TextView starShopOpenTime;
        private View view;

        public StartHolder(Context context, View itemView) {
            super(itemView);
            this.view = itemView;
            starImage = itemView.findViewById(R.id.star_img);
            starCancel = itemView.findViewById(R.id.star_cancel);
            starShopName = itemView.findViewById(R.id.star_shop_name);
            starShopMonthlySales = itemView.findViewById(R.id.star_shop_monthlySales);
            starShopSpace = itemView.findViewById(R.id.star_shop_space);
            starShopOpenTime = itemView.findViewById(R.id.star_shop_openTime);
        }

        public void setData(Shop shop, int position) {
            starImage.setTag(position);
            Picasso.get().load(shop.getShopImageUrl()).fit().into(starImage);
            starShopName.setTag(position);
            starShopName.setText(shop.getShopName());
            starShopMonthlySales.setTag(position);
            starShopMonthlySales.setText(shop.getMonthlySales() + "");
            starShopSpace.setTag(position);
            starShopSpace.setText(shop.getAddress().toString());
            starShopOpenTime.setTag(position);
            starShopOpenTime.setText(shop.getBeginTime() + " " + shop.getEndTime());
            starCancel.setTag(position);
            starCancel.setOnClickListener(v -> {
                if (mineOnClickListener != null) {
                    mineOnClickListener.removeStarListener(view, position);
                }
            });
            view.setTag(position);
            view.setOnClickListener(v -> {
                if (mineOnClickListener != null) {
                    mineOnClickListener.itemClickListener(view, position);
                }
            });
        }
    }

    private MineOnClickListener mineOnClickListener;

    public void setMineOnClickListener(CollectAdapter.MineOnClickListener mineOnClickListener) {
        this.mineOnClickListener = mineOnClickListener;
    }

    public interface MineOnClickListener {
        void removeStarListener(View view, int position);

        void itemClickListener(View view, int position);
    }
}
