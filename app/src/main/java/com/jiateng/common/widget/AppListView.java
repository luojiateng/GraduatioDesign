package com.jiateng.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @Description: 重写ListView, 实现在ScrollView 中ListView全尺寸显示
 * @Title: AppListView
 * @ProjectName: orderFood
 * @date: 2023/1/15 14:12
 * @author: 骆家腾
 */

public class AppListView extends ListView {
    public AppListView(Context context) {
        super(context);
    }

    public AppListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
