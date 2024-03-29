package com.jiateng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.activity.SearchActivity;
import com.jiateng.activity.ShopActivity;
import com.jiateng.adapter.HomeFragmentAdapter;
import com.jiateng.base.BaseFragment;
import com.jiateng.domain.Shop;
import com.jiateng.retrofit.api.ShopApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.DefaultValueData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @ViewInject(R.id.search_bar)
    private TextView searchBar;
    private HomeFragmentAdapter adapter;
    private ShopApi shopRequest;
    private ArrayList<Shop> shops;
    @ViewInject(R.id.home_progressBar)
    private ProgressBar progressBar;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_home, null);
        ViewUtils.inject(this, view);
        shopRequest = RetrofitManager.getInstance().getApiService("/shop", ShopApi.class);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        shopRequest.getShopList().enqueue(new Callback<ResponseResult<ArrayList<Shop>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<Shop>>> call, Response<ResponseResult<ArrayList<Shop>>> response) {
                shops = ResultUtil.getResult(response);
                if (shops == null) {
                    shops = DefaultValueData.getShopInfoList();
                }
                adapter = new HomeFragmentAdapter(context, shops);
                recyclerView.setAdapter(adapter);
                adapter.setMyOnClickListener((view, position) -> {
                    int index = position - 1;
                    Intent intent = new Intent(context, ShopActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("shop", shops.get(index));
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManager.setReverseLayout(false);
                recyclerView.setLayoutManager(linearLayoutManager);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<Shop>>> call, Throwable t) {

            }
        });
    }
}
