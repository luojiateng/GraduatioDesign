package com.jiateng.activity.login.data;

import static com.jiateng.utils.SharedPreferencesUtil.hasToken;

import com.jiateng.activity.login.data.model.LoggedInUser;
import com.jiateng.config.MainApplicationConfig;
import com.jiateng.db.impl.UserCollectDaoImpl;
import com.jiateng.domain.Shop;
import com.jiateng.domain.User;
import com.jiateng.domain.UserCollect;
import com.jiateng.domain.UserDTO;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        User user = new User(username, password);
        //此处请求后台接口
        try {
            AtomicReference<UserDTO> userDTO = new AtomicReference<>();
            Thread t1 = new Thread(() -> {
                Response<ResponseResult<UserDTO>> response = null;
                try {
                    response = RetrofitManager.getInstance().getApiService("/user", UserApi.class).loginUser(user).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                userDTO.set(ResultUtil.getResult(response));

            });
            t1.start();
            t1.join();
            if (userDTO.get() == null) {
                ToastUtil.ToastShow("请检查用户名或密码");
                return new Result.Error(new IOException("Error logging in"));
            } else {
                SharedPreferencesUtil.putString(MainApplicationConfig.getContext(), "password", password);
                SharedPreferencesUtil.putString(MainApplicationConfig.getContext(), "token", userDTO.get().getToken());
                SharedPreferencesUtil.putInt(MainApplicationConfig.getContext(), "userId", userDTO.get().getUserId());
                SharedPreferencesUtil.putString(MainApplicationConfig.getContext(), "nickName", userDTO.get().getNickName());
                LoggedInUser fakeUser = new LoggedInUser(userDTO.get().getPhoneNumber(), userDTO.get().getNickName());
                if (hasToken()) {
                    initUserCollect();
                }
                return new Result.Success<>(fakeUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
    }

    public void initUserCollect() {
        UserCollectDaoImpl instance = UserCollectDaoImpl.getInstance(MainApplicationConfig.getContext());
        RetrofitManager.getInstance().getApiService("/user", UserApi.class).getUserFavouriteShops().enqueue(new Callback<ResponseResult<ArrayList<Shop>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<Shop>>> call, Response<ResponseResult<ArrayList<Shop>>> response) {
                ArrayList<Shop> shops = ResultUtil.getResult(response);
                Integer userId = SharedPreferencesUtil.getInt(MainApplicationConfig.getContext(), "userId", 0);
                ArrayList<UserCollect> collects = (ArrayList<UserCollect>) shops.stream().map(shop -> new UserCollect(userId, shop.getShopId())).collect(Collectors.toList());
                instance.putUserCollectShops(collects);
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<Shop>>> call, Throwable t) {

            }
        });
    }
}