package com.jiateng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.jiateng.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.jiateng.bean.Appraise;

/**
 * @Description:
 * @Title: AppraiseAdapter
 * @ProjectName: orderFood
 * @date: 2023/2/8 14:24
 * @author: 骆家腾
 */
public class AppraiseAdapter extends BaseAdapter {
    private Context context;
    private List<Appraise> data;

    private AppCompatImageView imageView;
    private TextView username;
    private TextView time;
    //TODO fix 使用StarBar替换
    private TextView star;
    private TextView appraiseContext;


    public AppraiseAdapter(Context context, List<Appraise> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_appraise, null);
        imageView = view.findViewById(R.id.appraise_user_avatar_image);
        username = view.findViewById(R.id.appraise_user_name);
        time = view.findViewById(R.id.appraise_time);
        star = view.findViewById(R.id.appraise_star);
        appraiseContext = view.findViewById(R.id.appraise_context);
        Appraise appraise = data.get(position);

        Picasso.get().load(appraise.getAvatarUrl()).fit().into(imageView);
        username.setText(appraise.getUserName());
        time.setText(appraise.getTime());
        appraiseContext.setText(appraise.getContext());
        star.setText(appraise.getServeScore() + "");

        return view;
    }
}
