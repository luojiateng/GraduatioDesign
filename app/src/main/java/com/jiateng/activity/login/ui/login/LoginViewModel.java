package com.jiateng.activity.login.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jiateng.R;
import com.jiateng.activity.login.data.LoginRepository;
import com.jiateng.activity.login.data.Result;
import com.jiateng.activity.login.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // 可以在异步过程工作，此处调用登录方法
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    /**
     * 账号密码有效性检查
     *
     * @param username
     * @param password
     */
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // 账号检查
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.trim().length() == 11) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
//todo 更改账号校验规则             return true;
        } else {
            return !username.trim().isEmpty();
        }
    }

    // 密码检查
    private boolean isPasswordValid(String password) {
//todo 更改密码长度校验规则        return password != null && password.trim().length() >= 5;
        return password != null && password.trim().length() >= 1;
    }
}