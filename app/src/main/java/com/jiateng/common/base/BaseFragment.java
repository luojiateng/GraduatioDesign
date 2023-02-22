package com.jiateng.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Description: 基本Fragment抽象类，主要用于新建Fragment的初始化与继承
 * @Title: BaseFragment
 * @ProjectName: orderFood
 * @date: 2023/1/10 13:17
 * @author: 骆家腾
 */
//创建时生命周期：onCreate->onCreateView->onCreateActivity->onStart
public abstract class BaseFragment extends Fragment {
    //供子类使用
    protected Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 抽象类，由子类实现，实现不同效果
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 当Activity被创建之后，回调这个函数
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 当子类需要联网请求数据的时候，在该方法内执行
     */
    protected void initData() {

    }
}
