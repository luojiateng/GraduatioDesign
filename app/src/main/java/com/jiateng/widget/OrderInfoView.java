package com.jiateng.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jiateng.R;

/**
 * @Description:
 * @Title: OrderInfoView
 * @ProjectName: orderFood
 * @date: 2023/1/15 12:31
 * @author: 骆家腾
 */
public class OrderInfoView extends LinearLayout {
    private TextView tv_front;
    private TextView tv_mid;
    private TextView tv_back;

    public void setTv_front(String text) {
        tv_front.setText(text);
    }

    public void setTv_mid(String text) {
        tv_mid.setText(text);
    }

    public void setTv_back(String text) {
        tv_back.setText(text);
    }

    public OrderInfoView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_order_info, this);
        tv_front = findViewById(R.id.order_info_front);
        tv_back = findViewById(R.id.order_info_back);
        tv_mid = findViewById(R.id.order_info_mid);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.OrderInfoView);
        String frontText = attributes.getString(R.styleable.OrderInfoView_front);
        String midText = attributes.getString(R.styleable.OrderInfoView_mid);
        String backText = attributes.getString(R.styleable.OrderInfoView_back);

        tv_front.setText(frontText);
        tv_mid.setText(midText);
        tv_back.setText(backText);

        attributes.recycle();
    }

    public void setOrderInfo(int count, String msg) {
        tv_mid.setText("×" + count);
        tv_back.setText(msg);
    }

    public void setOrderInfo(String msg) {
        tv_back.setText(msg);
    }
}
