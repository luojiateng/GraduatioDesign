package com.jiateng.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jiateng.R;
import com.jiateng.widget.AppTitleView;
import com.jiateng.domain.AddressInfo;
import com.jiateng.retrofit.api.AddressApi;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressSetUpActivity extends AppCompatActivity {

    @ViewInject(R.id.address_set_title)
    private AppTitleView titleView;
    @ViewInject(R.id.school)
    private EditText school;
    @ViewInject(R.id.build)
    private EditText build;
    @ViewInject(R.id.home_id)
    private EditText homeId;
    @ViewInject(R.id.address_set_username)
    private EditText addressSetUsername;
    @ViewInject(R.id.address_set_phoneNumber)
    private EditText addressSetPhoneNumber;
    @ViewInject(R.id.deleteAddress)
    private Button deleteAddress;
    @ViewInject(R.id.insertAddress)
    private Button insertAddress;
    private AddressInfo address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_set_up);
        ViewUtils.inject(this);
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type");
        if ("insert".equals(type)) {
            insertAddress.setOnClickListener(v -> {
                String schoolStr = school.getText().toString().trim();
                String buildStr = build.getText().toString().trim();
                String homeIdStr = homeId.getText().toString().trim();
                String usernameStr = addressSetUsername.getText().toString().trim();
                String phoneNumberStr = addressSetPhoneNumber.getText().toString().trim();
                Boolean dataOk = editCheck(schoolStr, buildStr, homeIdStr, usernameStr, phoneNumberStr);
                if (dataOk) {
                    AddressInfo addressInfo = new AddressInfo(0, schoolStr, buildStr, homeIdStr, usernameStr, phoneNumberStr);
                    RetrofitManager.getInstance().getApiService("/address", AddressApi.class).insertUserAddress(addressInfo).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Boolean resultMsg = ResultUtil.getResultMsg(response);
                            if (resultMsg) {
                                ToastUtil.ToastShow("新增地址成功");
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                }
            });
        } else {
            this.address = (AddressInfo) bundle.getSerializable("address");
            AddressInfo address = this.address;
            school.setText(address.getSchoolName());
            build.setText(address.getBuild());
            homeId.setText(address.getSpecificAddress());
            addressSetUsername.setText(address.getUserName());
            addressSetPhoneNumber.setText(address.getUserPhone());
            Boolean dataOk = editCheck(school.getText().toString().trim(),
                    build.getText().toString().trim(),
                    homeId.getText().toString().trim(),
                    addressSetUsername.getText().toString().trim(),
                    addressSetPhoneNumber.getText().toString().trim());
            insertAddress.setOnClickListener(v -> {
                if (dataOk) {
                    AddressInfo addressInfo = new AddressInfo(
                            address.getAddressId(),
                            school.getText().toString().trim(),
                            build.getText().toString().trim(),
                            homeId.getText().toString().trim(),
                            addressSetUsername.getText().toString().trim(),
                            addressSetPhoneNumber.getText().toString().trim());
                    RetrofitManager.getInstance().getApiService("/address", AddressApi.class).updateUserAddress(addressInfo).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Boolean resultMsg = ResultUtil.getResultMsg(response);
                            if (resultMsg) {
                                ToastUtil.ToastShow("地址更新成功");
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                }
            });
        }

        deleteAddress.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);//this为上下文，如果在本类里显示，通常使用this
            builder.setTitle("提示");
            builder.setMessage("确认删除这个地址？");
            builder.setPositiveButton("确定", (dialogInterface, i) -> RetrofitManager.getInstance().getApiService("/address", AddressApi.class).deleteAddressInfoByAddressId(address.getAddressId()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Boolean resultMsg = ResultUtil.getResultMsg(response);
                    if (resultMsg) {
                        ToastUtil.ToastShow("地址删除成功");
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            }));
            builder.setNeutralButton("取消", null);
            builder.show(); //调用show()方法来展示对话框
        });
        titleView.setOnClickListener(v -> {
            finish();
        });
    }

    public Boolean editCheck(String edSchool, String edBuild, String home, String username, String phoneNumber) {
        if (strIsNull(edSchool)) {
            ToastUtil.ToastShow("学校名不能为空");
            return false;
        } else if (strIsNull(edBuild)) {
            ToastUtil.ToastShow("宿舍楼不能为空");
            return false;
        } else if (strIsNull(home)) {
            ToastUtil.ToastShow("宿舍号不能为空");
            return false;
        } else if (strIsNull(username)) {
            ToastUtil.ToastShow("收货名不能为空");
            return false;
        } else if (strIsNull(phoneNumber)) {
            ToastUtil.ToastShow("电话号不能为空");
            return false;
        } else {
            return true;
        }
    }

    public Boolean strIsNull(String str) {
        return str == null || "".equals(str);
    }
}