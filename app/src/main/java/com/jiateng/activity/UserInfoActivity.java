package com.jiateng.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jiateng.R;
import com.jiateng.widget.AppTitleView;
import com.jiateng.domain.User;
import com.jiateng.retrofit.api.UserApi;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.GlideEngine;
import com.jiateng.utils.PicassoUtil;
import com.jiateng.utils.SharedPreferencesUtil;
import com.jiateng.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private View bottomSheet;
    @ViewInject(R.id.userinfoBottomSheet)
    private BottomSheetLayout bottomSheetLayout;
    @ViewInject(R.id.title_userInfo)
    private AppTitleView titleView;

    @ViewInject(R.id.setUserNameLinear)
    private LinearLayout setUsernameLinear;

    @ViewInject(R.id.UserAvatarLinear)
    private LinearLayout setAvatarLinear;

    @ViewInject(R.id.setUserGenderLinear)
    private LinearLayout setGenderLinear;

    @ViewInject(R.id.modifyPasswordLinear)
    private LinearLayout modifyPasswordLinear;

    @ViewInject(R.id.setUserAvatar)
    private AppCompatImageView avatar;
    @ViewInject(R.id.user_gender)
    private TextView tv_gender;
    @ViewInject(R.id.tv_username)
    private TextView tv_username;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ViewUtils.inject(this);
        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable("user");
        titleView.setOnClickListener(v -> {
            finish();
        });
        tv_gender.setText(user.getSex());
        tv_username.setText(user.getNickName());
        tv_phone.setText(user.getPhoneNumber());
        PicassoUtil.setImage(user.getAvatarUrl(), avatar);
        setUsernameLinear.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, ModifyUserInfoActivity.class);
            Bundle b1 = new Bundle();
            b1.putString("type", "设置用户名");
            intent.putExtras(b1);
            startActivity(intent);
        });
        setAvatarLinear.setOnClickListener(v -> {

        });
        setGenderLinear.setOnClickListener(v -> {
            showBottomSheet();
        });
        modifyPasswordLinear.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, ModifyPasswordActivity.class);
            Bundle b1 = new Bundle();
            b1.putString("type", "更改密码");
            intent.putExtras(b1);
            startActivity(intent);
        });
        setAvatarLinear.setOnClickListener(v -> {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .imageEngine(GlideEngine.createGlideEngine())
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
                    SharedPreferencesUtil.putString(UserInfoActivity.this, "avatar", result.get(0).getPath());
                    String realPath = result.get(0).getRealPath();
                    PicassoUtil.setImage(result.get(0).getPath(), avatar);
                    uploadFile(realPath);
                    break;
                default:
                    break;
            }
        }
    }

    private void uploadFile(String filePath) {
        UserApi service = RetrofitManager.getInstance().getApiService("/user", UserApi.class);
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<ResponseBody> call = service.uploadUserAvatar(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if (ResultUtil.getResultMsg(response)) {
                    ToastUtil.ToastShow("上传成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }


    /**
     * 从底部滑入
     */
    private void showBottomSheet() {
        bottomSheet = createBottomSheetView();
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            bottomSheetLayout.showWithSheetView(bottomSheet);
        }
    }


    /**
     * 从底部弹出的子布局
     *
     * @return
     */
    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_gender, (ViewGroup) getWindow().getDecorView(), false);
        RadioGroup genderGroup = view.findViewById(R.id.genderRadioGroup);
        RadioButton man = view.findViewById(R.id.gender_man);
        RadioButton woman = view.findViewById(R.id.gender_womam);
        genderGroup.setOnCheckedChangeListener(this);
        String gender = SharedPreferencesUtil.getString(UserInfoActivity.this, "sex", "");
        if ("男".equals(gender)) {
            man.setChecked(true);
        } else {
            woman.setChecked(true);
        }
        man.setOnClickListener(v -> {
            SharedPreferencesUtil.putString(UserInfoActivity.this, "sex", "男");
            bottomSheetLayout.dismissSheet();
            tv_gender.setText("男");
            updateUserSex("男");
        });
        woman.setOnClickListener(v -> {
            SharedPreferencesUtil.putString(UserInfoActivity.this, "sex", "女");
            bottomSheetLayout.dismissSheet();
            tv_gender.setText("女");
            updateUserSex("女");
        });
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        String nickName = SharedPreferencesUtil.getUser().getNickName();
        tv_username.setText(nickName);
    }

    private void updateUserSex(String gender) {
        User user = SharedPreferencesUtil.getUser();
        user.setSex(gender);
        RetrofitManager.getInstance().getApiService("/user", UserApi.class).updateUserInfo(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (ResultUtil.getResultMsg(response)) {
                    ToastUtil.ToastShow("修改成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            //在这里补充一下因为现在的数据格式是LocalMedia需要进行转化不能强转这样会找不到路径的
            showSelectPic(selectList);
        }
    }

    private void showSelectPic(List<LocalMedia> result) {
        ArrayList<Object> fileList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            String path;
            //判断是否10.0以上
            if (Build.VERSION.SDK_INT >= 29) {
                path = result.get(i).getAndroidQToPath();
            } else {
                path = result.get(i).getPath();
            }
            String newPath = BitmapUtil.compressImage(path);//压缩
            fileList.add(new File(newPath));//将路径放到File集合里面去传到接口
            Log.e(TAG, "图片链接: " + path);
//            //请求网络上传图片
//            RetrofitManager.getInstance().getApiService().postMoreImage(urls, headmap, map, fileList, new okRE.NetCallBack() {
//                @Override
//                public void onSuccess(String string) {
//                    Toast.makeText(MainActivity.this, string + "", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFail(String string) {
//                    Toast.makeText(MainActivity.this, string + "", Toast.LENGTH_SHORT).show();
//
//                }
//            });
        }

    }*/
}