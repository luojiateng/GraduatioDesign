package com.jiateng.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.adapter.CollectAdapter;
import com.jiateng.db.impl.UserCollectDaoImpl;
import com.jiateng.domain.Shop;
import com.jiateng.domain.StoreBean;
import com.jiateng.retrofit.api.ShopApi;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.ToastUtil;
import com.jiateng.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.star_title)
    private AppTitleView starTitle;

    @ViewInject(R.id.userFavouriteShopView)
    private RecyclerView recyclerView;

    private CollectAdapter adapter;

    @ViewInject(R.id.clo_bar)
    private ProgressBar progressBar;

    @ViewInject(R.id.tip_hiden_collect)
    private TextView hidenText;
    private ArrayList<Shop> shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_star);
        ViewUtils.inject(this);
        progressBar.setVisibility(View.VISIBLE);
        starTitle.onClickTitleListener(v -> {
            finish();
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserFavouriteShops().enqueue(new Callback<ResponseResult<ArrayList<Shop>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<Shop>>> call, Response<ResponseResult<ArrayList<Shop>>> response) {
                shops = ResultUtil.getResult(response);
                recyclerView.setLayoutManager(new LinearLayoutManager(CollectActivity.this));
                adapter = new CollectAdapter(shops, CollectActivity.this);
                adapter.setMineOnClickListener(new CollectAdapter.MineOnClickListener() {
                    @Override
                    public void removeStarListener(View view, int position) {
                        removeStar(shops.get(position).getShopId());
                    }

                    @Override
                    public void itemClickListener(View view, int position) {
                        RetrofitManager.getInstance().getApiService("/shop", ShopApi.class).getShopGoods(shops.get(position).getShopId()).enqueue(new Callback<ResponseResult<ArrayList<StoreBean.Goods>>>() {
                            @Override
                            public void onResponse(Call<ResponseResult<ArrayList<StoreBean.Goods>>> call, Response<ResponseResult<ArrayList<StoreBean.Goods>>> response) {
                                Intent intent = new Intent(CollectActivity.this, ShopActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("shop", shops.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<ResponseResult<ArrayList<StoreBean.Goods>>> call, Throwable t) {

                            }
                        });
                    }
                });
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                if (shops.size() != 0) {
                    hidenText.setVisibility(View.GONE);
                } else {
                    hidenText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<Shop>>> call, Throwable t) {

            }
        });

    }

    public void removeStar(Integer shopId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);//this为上下文，如果在本类里显示，通常使用this
        builder.setMessage("确定移除这个收藏？");
        builder.setPositiveButton("确定", (dialogInterface, i) -> RetrofitManager.getInstance().getApiService("/user", UserApi.class).removeCollect(shopId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Boolean resultMsg = ResultUtil.getResultMsg(response);
                if (resultMsg) {
                    onResume();
                    UserCollectDaoImpl.getInstance(CollectActivity.this).deleteUserCollectShopByShopId(shopId);
                    ToastUtil.ToastShow("移除收藏成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        }));
        builder.setNeutralButton("取消", null);
        builder.show(); //调用show()方法来展示对话框

    }
}