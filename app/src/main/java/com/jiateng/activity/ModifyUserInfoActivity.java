package com.jiateng.activity;

import android.os.Bundle;
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

public class ModifyUserInfoActivity extends AppCompatActivity {

    @ViewInject(R.id.modifyUsername)
    private AppTitleView titleView;

    @ViewInject(R.id.usernameEdit)
    private EditText usernameEdit;

    @ViewInject(R.id.update_username)
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_info);
        ViewUtils.inject(this);
        titleView.setOnClickListener(v -> {
            finish();
        });
        String nickName = SharedPreferencesUtil.getString(ModifyUserInfoActivity.this, "nickName", "");
        usernameEdit.setText(nickName);
        button.setOnClickListener(v -> {
            if (!nickName.equals(usernameEdit.getText().toString().trim())) {
                User user = SharedPreferencesUtil.getUser();
                user.setNickName(usernameEdit.getText().toString().trim());
                RetrofitManager.getInstance().getApiService("/user", UserApi.class).updateUserInfo(user).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (ResultUtil.getResultMsg(response)) {
                            ToastUtil.ToastShow("名字更新成功");
                            SharedPreferencesUtil.putString(ModifyUserInfoActivity.this, "nickName", usernameEdit.getText().toString().trim());
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            } else {
                ToastUtil.ToastShow("请输入新的名字");
            }
        });
    }
}