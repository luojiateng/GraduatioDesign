package com.jiateng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jiateng.R;
import com.jiateng.widget.AppTitleView;
import com.jiateng.domain.User;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPasswordActivity extends AppCompatActivity {

    @ViewInject(R.id.modifyPassword)
    private AppTitleView titleView;

    @ViewInject(R.id.oldPassword)
    private EditText oldPassword;
    @ViewInject(R.id.newPassword)
    private EditText newPassword;
    @ViewInject(R.id.confirmPassword)
    private EditText confirmPassword;
    @ViewInject(R.id.save_username)
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ViewUtils.inject(this);
        titleView.onClickTitleListener(this::onClick);
        button.setOnClickListener(v -> {
            String oldP = oldPassword.getText().toString().trim();
            String newP = newPassword.getText().toString().trim();
            String confirm = newPassword.getText().toString().trim();
            String password = SharedPreferencesUtil.getString(ModifyPasswordActivity.this, "password", "");
            if (!password.equals(oldP)) {
                ToastUtil.ToastShow("旧密码错误");
            } else {
                if (newP.equals(confirm)) {
                    User user = new User(null,newP);
                    RetrofitManager.getInstance().getApiService("/user", UserApi.class).updateUserInfo(user).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (ResultUtil.getResultMsg(response)) {
                                SharedPreferencesUtil.putString(ModifyPasswordActivity.this, "password", newP);
                                ToastUtil.ToastShow("密码修改成功");
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ToastUtil.ToastShow("请检查网络");
                        }
                    });
                } else {
                    ToastUtil.ToastShow("请检查密码一致");
                }
            }
        });
    }

    private void onClick(View v) {
        finish();
    }
}