package com.jiateng.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.adapter.ShopAdapter;
import com.jiateng.domain.Shop;
import com.jiateng.retrofit.api.ShopApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.ToastUtil;
import com.jiateng.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends Activity {
    private ArrayList<Shop> shopArrayList;
    @ViewInject(R.id.title_searchHistory)
    private AppTitleView appTitleView;

    @ViewInject(R.id.tip_hiden_search)
    private TextView hidenText;
    @ViewInject(R.id.search_result)
    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;

    @ViewInject(R.id.progre)
    private ProgressBar progressBar;
    private String searchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ViewUtils.inject(this);
        Bundle bundle = getIntent().getExtras();
        searchName = bundle.getString("searchName");
        progressBar.setVisibility(View.VISIBLE);
        appTitleView.onClickTitleListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        RetrofitManager.getInstance().getApiService("/shop", ShopApi.class).searchShop(searchName).enqueue(new Callback<ResponseResult<ArrayList<Shop>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<Shop>>> call, Response<ResponseResult<ArrayList<Shop>>> response) {
                ArrayList<Shop> shopArrayList = ResultUtil.getResult(response);
                if (shopArrayList != null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
                    shopAdapter = new ShopAdapter(shopArrayList, SearchResultActivity.this);
                    shopAdapter.setMyOnClickListener((view, position) -> {
                        Intent intent = new Intent(SearchResultActivity.this, ShopActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("shop", shopArrayList.get(position));
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(shopAdapter);
                }
                progressBar.setVisibility(View.GONE);
                if (shopArrayList.size() == 0) {
                    hidenText.setVisibility(View.VISIBLE);
                } else {
                    hidenText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<Shop>>> call, Throwable t) {
                ToastUtil.intenetErrorNotification();
            }
        });
    }
}