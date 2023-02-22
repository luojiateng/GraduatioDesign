package com.jiateng.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jiateng.R;

/**
 * @Description:
 * @Title: Title
 * @ProjectName: orderFood
 * @date: 2023/1/13 17:29
 * @author: 骆家腾
 */
public class AppTitleView extends LinearLayout {
    private TextView tipView;
    private TextView titleView;
    private ImageView arrowImageView;

    public AppTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_common_title, this);
        tipView = findViewById(R.id.title_tip);
        titleView = findViewById(R.id.title_title);
        arrowImageView = findViewById(R.id.title_image);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitleView);
        String title = typedArray.getString(R.styleable.AppTitleView_title);
        String tip = typedArray.getString(R.styleable.AppTitleView_tip);
        boolean isVisible = typedArray.getBoolean(R.styleable.AppTitleView_visible, false);
        setView(title, tip, isVisible);
        typedArray.recycle();
    }

    public void setView(String title, String tip, boolean isVisible) {
        if (titleView != null) {
            titleView.setText(title);
        }
        if (tipView != null) {
            tipView.setText(tip);
        }
        if (isVisible) {
            arrowImageView.setVisibility(View.VISIBLE);
        } else {
            arrowImageView.setVisibility(View.GONE);
        }
    }

    /**
     * 为左箭头设置监听事件
     *
     * @param listener
     */
    public void onClickTitleListener(OnClickListener listener) {
        arrowImageView.setOnClickListener(listener);
    }


}
