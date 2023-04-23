package com.jiateng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jiateng.R;
import com.jiateng.widget.AppTitleView;
import com.jiateng.domain.User;
import com.jiateng.domain.UserDTO;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingupActivity extends AppCompatActivity {

    @ViewInject(R.id.singup_title)
    private AppTitleView titleView;
    @ViewInject(R.id.singup_btn)
    private Button singupBtn;
    @ViewInject(R.id.singup_phone)
    private EditText phoneEditText;
    @ViewInject(R.id.singup_password)
    private EditText passwdEditText;
    @ViewInject(R.id.singup_password_repeat)
    private EditText passwdRepeatEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        ViewUtils.inject(this);
        userSingup();
    }

    private void userSingup() {
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            /**
             * 检查用户名账户是否有效
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                DataChanged(phoneEditText.getText().toString().trim(),
                        passwdEditText.getText().toString().trim(),
                        passwdRepeatEditText.getText().toString().trim());
            }
        };
        phoneEditText.addTextChangedListener(afterTextChangedListener);
        passwdEditText.addTextChangedListener(afterTextChangedListener);
        passwdRepeatEditText.addTextChangedListener(afterTextChangedListener);
        singupBtn.setOnClickListener(v -> {
            User user = new User(phoneEditText.getText().toString(), passwdEditText.getText().toString());
            RetrofitManager.getInstance().getApiService("/user", UserApi.class).singup(user).enqueue(new Callback<ResponseResult<UserDTO>>() {
                @Override
                public void onResponse(Call<ResponseResult<UserDTO>> call, Response<ResponseResult<UserDTO>> response) {
                    ResponseResult<UserDTO> body = response.body();
                    if ((body.getCode() == 400)) {
                        ToastUtil.ToastShow(body.getMsg());
                    } else {
                        UserDTO userDTO = ResultUtil.getResult(response);
                        SharedPreferencesUtil.putInt(SingupActivity.this, "userId", userDTO.getUserId());
                        SharedPreferencesUtil.putString(SingupActivity.this, "token", userDTO.getToken());
                        SharedPreferencesUtil.putString(SingupActivity.this, "phoneNumber", userDTO.getPhoneNumber());
                        SharedPreferencesUtil.putString(SingupActivity.this, "nickName", userDTO.getPhoneNumber());
                        Intent intent = new Intent();
                        intent.putExtra("singupReturn", "yes");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseResult<UserDTO>> call, Throwable t) {
                    ToastUtil.intenetErrorNotification();
                }
            });
        });
        titleView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("singupReturn", "no");
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void DataChanged(String phoneNumber, String password, String passwordRepeat) {
        if (!isPhoneNumberValid(phoneNumber)) {
            ToastUtil.ToastShow("手机号无效");
        } else if (!isPasswordValidAndEquals(password, passwordRepeat)) {
            ToastUtil.ToastShow("密码不一致");
        } else {
        }
    }

    private Boolean isPhoneNumberValid(String phoneNumber) {
        if ("".equals(phoneNumber) || phoneNumber == null) {
            return false;
        }
        return phoneNumber.length() == 11;
    }

    private Boolean isPasswordValidAndEquals(String password, String passwordRepeat) {
        if (password == null || "".equals(password) || passwordRepeat == null || "".equals(passwordRepeat)) {
            ToastUtil.ToastShow("密码为空");
            return false;
        } else {
            return password.equals(passwordRepeat);
        }
    }


}