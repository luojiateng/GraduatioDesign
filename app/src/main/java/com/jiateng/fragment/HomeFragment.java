package com.jiateng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.activity.ShopActivity;
import com.jiateng.adapter.HomeFragmentAdapter;
import com.jiateng.bean.ShopInfo;
import com.jiateng.common.base.BaseFragment;
import com.jiateng.common.utils.MockData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: HomeFragment
 * @ProjectName: orderFood
 * @date: 2023/1/10 17:44
 * @author: 骆家腾
 */
public class HomeFragment extends BaseFragment {

    @ViewInject(R.id.recycler)
    private RecyclerView recyclerView;
    private HomeFragmentAdapter adapter;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_home, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        //TODO mock data
        ArrayList<ShopInfo> shopInfoData = MockData.getShopInfoList();
        adapter = new HomeFragmentAdapter(context, shopInfoData);
        recyclerView.setAdapter(adapter);
        adapter.setMyOnClickListener((view, position) -> {
            int index = position - 1;
            Intent intent = new Intent(context, ShopActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("shopInfo", shopInfoData.get(index));
            intent.putExtras(bundle);
            startActivity(intent);
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
