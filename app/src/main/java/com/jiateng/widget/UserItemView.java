package com.jiateng.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiateng.R;

/**
 * @Description:
 * @Title: userItemView
 * @ProjectName: orderFood
 * @date: 2023/1/12 15:10
 * @author: 骆家腾
 */

/**
 * 自定义View：
 * 1、继承一个组件或者布局方式
 * 2、编写attrs.xml文件
 * 3、在java 文件中重写 含有两个参数的构造器
 * 4、绑定attrs.xml
 * 注意图片文件的引用
 */
public class UserItemView extends RelativeLayout {
    private ImageView item_image;
    private TextView item_text;


    public UserItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_user_item, this);
        item_image = findViewById(R.id.item_image);
        item_text = findViewById(R.id.item_text);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserItemView);
        int resourceId = typedArray.getResourceId(R.styleable.UserItemView_itemImage, R.drawable.ic_default);
        String text = typedArray.getString(R.styleable.UserItemView_itemText);
        item_image.setImageResource(resourceId);
        item_text.setText(text);
        typedArray.recycle();
    }
}

