package com.jiateng.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.adapter.AddressAdapter;
import com.jiateng.domain.AddressInfo;
import com.jiateng.retrofit.api.AddressApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.address_title)
    private AppTitleView addressTitle;
    @ViewInject(R.id.address_recyclerView)
    private RecyclerView addressListView;
    private AddressAdapter adapter;
    @ViewInject(R.id.insert_address_btn)
    private Button insertAddress;
    private ArrayList<AddressInfo> addressInfos;

    @ViewInject(R.id.pro)
    private ProgressBar progressBar;
    @ViewInject(R.id.tip_hiden)
    private TextView hidenText;
    private ArrayList<AddressInfo> result;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        ViewUtils.inject(this);
        progressBar.setVisibility(View.VISIBLE);
        addressTitle.onClickTitleListener(v -> {
            finish();
        });

    }

    @SneakyThrows
    @Override
    protected void onResume() {
        super.onResume();
        RetrofitManager.getInstance().getApiService("/address", AddressApi.class).getUserAddress().enqueue(new Callback<ResponseResult<ArrayList<AddressInfo>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<AddressInfo>>> call, Response<ResponseResult<ArrayList<AddressInfo>>> response) {
                result = ResultUtil.getResult(response);
                addressInfos = result;
                adapter = new AddressAdapter(AddressActivity.this, result);
                addressListView.setLayoutManager(new LinearLayoutManager(AddressActivity.this));
                adapter.setMyOnClickListener(new AddressAdapter.AddressOnClickListener() {
                    @Override
                    public void itemClickListener(View view, int position) {
                        Bundle extras = getIntent().getExtras();
                        String fromPostFragment = extras.getString("fromPostFragment");
                        Intent intent = new Intent(AddressActivity.this, UserInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("addressInfo", addressInfos);
                        intent.putExtras(bundle);
                        int index = 0;
                        for (int i = 0; i < addressInfos.size(); i++) {
                            if (addressInfos.get(i).getAddressId() == addressInfos.get(position).getAddressId()) {
                                index = i;
                                break;
                            }
                        }
                        int userId = SharedPreferencesUtil.getInt(AddressActivity.this, "userId", 0);
                        SharedPreferencesUtil.putInt(AddressActivity.this, "currentUserDefaultIAddressIndex-" + userId, index);
                        setResult(RESULT_OK, intent);
                        if ("yes".equals(fromPostFragment)) {
                            finish();
                        }
                    }

                    @Override
                    public void editListener(View view, int position) {
                        Intent intent = new Intent(AddressActivity.this, AddressSetUpActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("address", addressInfos.get(position));
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 2);
                    }
                });
                addressListView.setAdapter(adapter);
                insertAddress.setOnClickListener(v1 -> {
                    Intent intent = new Intent(AddressActivity.this, AddressSetUpActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("type", "insert");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 2);
                });
                progressBar.setVisibility(View.GONE);
                if (result.size() != 0) {
                    hidenText.setVisibility(View.GONE);
                } else {
                    hidenText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<AddressInfo>>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}