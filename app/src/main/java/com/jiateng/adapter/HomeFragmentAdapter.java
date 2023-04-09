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
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: HomeFragmentAdapter
 * @ProjectName: orderFood
 * @date: 2023/2/6 12:48
 * @author: 骆家腾
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter {

    private int currentItem = 0;
    private static final int BANNER = 0;
    private static final int LIST = 1;
    private Context context;
    private ArrayList<Shop> shopData;
    private MyOnClickListener myOnClickListener;

    public HomeFragmentAdapter(Context context, ArrayList<Shop> shopData) {
        this.context = context;
        this.shopData = shopData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            View itemView = View.inflate(context, R.layout.banner_home, null);
            return new BannerHolder(context, itemView);
        } else {
            View listItemView = View.inflate(context, R.layout.item_home_shop, null);
            return new ListHolder(context, listItemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerHolder bannerHolder = (BannerHolder) holder;
            //TODO 修改为从服务器请求的数据，删除mokaImage这个方法
            bannerHolder.setData(mokaImage());
        } else {
            ListHolder listHolder = (ListHolder) holder;
            int index = position - 1;
            //TODO
            listHolder.setData(shopData.get(index), position);
        }
    }

    @Override
    public int getItemCount() {
        return 1 + shopData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentItem = BANNER;
        } else {
            currentItem = LIST;
        }
        return currentItem;
    }

    class ListHolder extends RecyclerView.ViewHolder {
        private ImageView shopImg;
        private TextView shopName;
        private TextView monthlySales;
        private TextView shopSpace;
        private TextView shopOpenTime;

        private View view;

        public ListHolder(Context context, @NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            shopImg = itemView.findViewById(R.id.shop_img);
            shopName = itemView.findViewById(R.id.list_item_shop_name);
            monthlySales = itemView.findViewById(R.id.list_item_shop_monthlySales);
            shopSpace = itemView.findViewById(R.id.list_item_shop_space);
            shopOpenTime = itemView.findViewById(R.id.list_item_shop_openTime);
        }

        public void setData(Shop shop, int position) {
            shopImg.setTag(position);
            Picasso.get().load(shop.getShopImageUrl()).fit().into(shopImg);
            shopName.setTag(position);
            shopName.setText(shop.getShopName());
            monthlySales.setTag(position);
            monthlySales.setText(shop.getMonthlySales().toString());
            shopSpace.setTag(position);
            shopSpace.setText(shop.getAddress().toString());
            shopOpenTime.setTag(position);
            shopOpenTime.setText(shop.getBeginTime() + "" + shop.getEndTime());

            view.setOnClickListener(v -> {
                if (myOnClickListener != null) {
                    myOnClickListener.viewClick(view, position);
                }
            });
        }
    }

    /**
     * 轮播图处理
     */
    class BannerHolder extends RecyclerView.ViewHolder {
        private Banner banner;

        public BannerHolder(Context context, View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }

        public void setData(ArrayList<Integer> data) {
            banner.setAdapter(new BannerImageAdapter<Integer>(data) {
                @Override
                public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                    holder.imageView.setImageResource(data);
                }
            });
            banner.isAutoLoop(true);
            banner.setIndicator(new CircleIndicator(context));
            banner.start();
        }

    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    /**
     * 点击事件回调接口.
     */
    public interface MyOnClickListener {
        void viewClick(View view, int position);
    }

    //TODO 将来获取banner从服务器中请求的数据后，删除这些数据
    private ArrayList<Integer> mokaImage() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.b_1);
        data.add(R.drawable.b_2);
        data.add(R.drawable.b_3);
        data.add(R.drawable.b_4);
        data.add(R.drawable.b_5);
        return data;
    }
}
