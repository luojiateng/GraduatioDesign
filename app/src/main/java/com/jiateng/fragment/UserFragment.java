package com.jiateng.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.jiateng.R;
import com.jiateng.activity.AddressActivity;
import com.jiateng.activity.CollectActivity;
import com.jiateng.activity.FeedbackActivity;
import com.jiateng.activity.MoreActivity;
import com.jiateng.activity.SettingActivity;
import com.jiateng.activity.UserInfoActivity;
import com.jiateng.activity.login.ui.login.LoginActivity;
import com.jiateng.base.BaseFragment;
import com.jiateng.domain.User;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.PicassoUtil;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.utils.ToastUtil;
import com.jiateng.widget.UserItemView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Description:
 * @Title: UserFragment
 * @ProjectName: orderFood
 * @date: 2023/1/10 17:44
 * @author: 骆家腾
 */
public class UserFragment extends BaseFragment {
    @ViewInject(R.id.user_item_address)
    private UserItemView address;
    @ViewInject(R.id.user_item_star)
    private UserItemView start;
    @ViewInject(R.id.user_item_more)
    private UserItemView more;
    @ViewInject(R.id.user_item_feedback)
    private UserItemView report;
    @ViewInject(R.id.user_item_setting)
    private UserItemView setting;
    @ViewInject(R.id.loginOrSingup)
    private TextView loginOrSingup;
    @ViewInject(R.id.user_avatar_image)
    private AppCompatImageView avatarImage;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_user, null);
        ViewUtils.inject(this, view);
        more.setVisibility(View.GONE);
        return view;
    }

    @OnClick({R.id.user_item_setting,
            R.id.user_item_address,
            R.id.user_item_star,
            R.id.user_item_more,
            R.id.user_item_feedback,
            R.id.user_item_login,
            R.id.user_avatar_image})
    private void changeActivity(View view) {
        String token = null;
        switch (view.getId()) {
            case R.id.user_item_setting:
                startActivityForResult(new Intent(context, SettingActivity.class), 1);
                break;
            case R.id.user_item_address:
                token = SharedPreferencesUtil.getString(context, "token", "null");
                if (token == "null") {
                    ToastUtil.ToastShow("请先登录");
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserInfo().enqueue(new Callback<ResponseResult<User>>() {
                        @Override
                        public void onResponse(Call<ResponseResult<User>> call, Response<ResponseResult<User>> response) {
                            User user = ResultUtil.getResult(response);
                            Intent intent = new Intent(context, AddressActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ResponseResult<User>> call, Throwable t) {
                            ToastUtil.intenetErrorNotification();
                        }
                    });
                }
                break;
            case R.id.user_item_star:
                token = SharedPreferencesUtil.getString(context, "token", "null");
                if (token == "null") {
                    ToastUtil.ToastShow("请先登录");
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserInfo().enqueue(new Callback<ResponseResult<User>>() {
                        @Override
                        public void onResponse(Call<ResponseResult<User>> call, Response<ResponseResult<User>> response) {
                            User user = ResultUtil.getResult(response);
                            Intent intent = new Intent(context, CollectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ResponseResult<User>> call, Throwable t) {
                            ToastUtil.intenetErrorNotification();
                        }
                    });
                }
                break;
            case R.id.user_item_more:
                startActivity(new Intent(context, MoreActivity.class));
                break;
            case R.id.user_item_feedback:
                startActivity(new Intent(context, FeedbackActivity.class));
                break;
            case R.id.user_avatar_image:
            case R.id.user_item_login:
                token = SharedPreferencesUtil.getString(context, "token", "null");
                if (token == "null") {
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserInfo().enqueue(new Callback<ResponseResult<User>>() {
                        @Override
                        public void onResponse(Call<ResponseResult<User>> call, Response<ResponseResult<User>> response) {
                            User user = ResultUtil.getResult(response);
                            Intent intent = new Intent(context, UserInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ResponseResult<User>> call, Throwable t) {
                            ToastUtil.intenetErrorNotification();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SharedPreferencesUtil.hasToken()) {
            loginOrSingup.setText("登录/注册");
            avatarImage.setImageResource(R.drawable.back_logo);

        } else {
            User user = SharedPreferencesUtil.getUser();
            if (!"null".equals(user.getNickName())) {
                loginOrSingup.setText(user.getNickName());
            }
            if (!"null".equals(user.getAvatarUrl())) {
                PicassoUtil.setImage(user.getAvatarUrl(), avatarImage);
            }
            RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserInfo().enqueue(new Callback<ResponseResult<User>>() {
                @Override
                public void onResponse(Call<ResponseResult<User>> call, Response<ResponseResult<User>> response) {
                    User user = ResultUtil.getResult(response);
                    if (user != null) {
                        SharedPreferencesUtil.putUserInfo(user);
                        if (!"null".equals(user.getNickName())) {
                            loginOrSingup.setText(user.getNickName());
                        }
                        if (!"null".equals(user.getAvatarUrl())) {
                            PicassoUtil.setImage(user.getAvatarUrl(), avatarImage);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseResult<User>> call, Throwable t) {

                }
            });
//            Thread t1 = new Thread(() -> {
//                try {
//                    Response<ResponseResult<User>> response = RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserInfo().execute();
//                    User user = ResultUtil.getResult(response);
//                    if (user != null) {
//                        SharedPreferencesUtil.putUserInfo(user);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            t1.start();
//            try {
//                t1.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
