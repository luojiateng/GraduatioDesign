package com.jiateng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.jiateng.R;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.utils.ToastUtil;
import com.jiateng.widget.AppTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.setting_title_id)
    private AppTitleView settingTitle;
    @ViewInject(R.id.logout)
    private TextView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ViewUtils.inject(this);
        //为返回键设置监听事件
        settingTitle.onClickTitleListener(v -> {
            finish();
        });

        Boolean hasToken = SharedPreferencesUtil.hasToken();
        if (!hasToken) {
            logout.setVisibility(View.GONE);
        }
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);//this为上下文，如果在本类里显示，通常使用this
            builder.setTitle("提示");
            builder.setMessage("确定退出登录？");
            builder.setPositiveButton("确定", (dialogInterface, i) -> RetrofitManager.getInstance().getApiService("/user", UserApi.class).logout().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Boolean resultMsg = ResultUtil.getResultMsg(response);
                    if (resultMsg) {
//                        Intent intent = new Intent();
//                        intent.putExtra("logout", "yes");
//                        setResult(RESULT_OK, intent);
                        ToastUtil.ToastShow("登出成功！");
                        SharedPreferencesUtil.removeUserBaseInfo();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ToastUtil.intenetErrorNotification();
                }
            }));
            builder.setNeutralButton("取消", null);
            builder.show(); //调用show()方法来展示对话框


        });

    }

    @Override
    public void onClick(View v) {
    }

}