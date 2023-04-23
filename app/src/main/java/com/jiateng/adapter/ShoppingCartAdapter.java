package com.jiateng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.jiateng.R;
import com.jiateng.domain.ShoppingCart;
import com.jiateng.utils.PicassoUtil;

import java.util.List;

/**
 * @Description:
 * @Title: CarAdapter
 * @ProjectName: orderFood
 * @date: 2023/2/10 0:08
 * @author: 骆家腾
 */
public class ShoppingCartAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingCart> shoppingCartData;

    public ShoppingCartAdapter(Context context, List<ShoppingCart> shoppingCartData) {
        this.context = context;
        this.shoppingCartData = shoppingCartData;
    }

    @Override
    public int getCount() {
        return shoppingCartData.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCartData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_cars, null);
        TextView goodsName = itemView.findViewById(R.id.shoppingCart_name);
        TextView shoppingCartPrice = itemView.findViewById(R.id.shoppingCart_price);
        TextView shoppingCartCount = itemView.findViewById(R.id.shoppingCart_count);
        ImageView reduceButton = itemView.findViewById(R.id.carReduceGoods);
        ImageView addButton = itemView.findViewById(R.id.carAddGoods);

        AppCompatImageView image = itemView.findViewById(R.id.shoppingCart_img);
        PicassoUtil.setImage(shoppingCartData.get(position).getGoods().getGoodsImageUrl(), image);

        goodsName.setText(shoppingCartData.get(position).getGoods().getGoodName());
        shoppingCartCount.setText(shoppingCartData.get(position).getGoodsCount() + "");
        double moneyCount = 0.0;
        moneyCount += shoppingCartData.get(position).getGoods().getPrice().doubleValue() * shoppingCartData.get(position).getGoodsCount().intValue();
        shoppingCartPrice.setText(String.format("%.1f", moneyCount));

        reduceButton.setOnClickListener(v -> onSelectListener.onSelectReduce(position));
        addButton.setOnClickListener(v -> onSelectListener.onSelectAdd(position, shoppingCartData.get(position)));
        return itemView;
    }

    private OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener listener) {
        this.onSelectListener = listener;
    }

    public interface OnSelectListener {
        void onSelectAdd(int position, ShoppingCart shoppingCart);

        void onSelectReduce(int position);

    }
}
