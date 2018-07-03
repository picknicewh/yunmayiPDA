package com.yun.mayi.pda.module.login;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.LoginInfo;
import com.yun.mayi.pda.bean.Version;
import com.yun.mayi.pda.db.UserAction;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.LocationService;
import com.yun.mayi.pda.module.home.HomeActivity;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.utils.PackageUtils;
import com.yun.mayi.pda.utils.PermissionsChecker;
import com.yun.mayi.pda.widget.CheckDialog;
import com.yun.mayi.pda.widget.CheckUpdateDialog;
import com.yun.mayi.pda.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/04
 * 名称：登录页
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View, CheckDialog.onPassWordCatchListener {

    /**
     * 登录icon
     */
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    /**
     * 用户名
     */
    @BindView(R.id.et_username)
    ClearEditText etUsername;
    /**
     * 密码
     */
    @BindView(R.id.et_password)
    ClearEditText etPassword;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 防止意外退出
     */
    private CheckDialog checkDialog;
    private CheckUpdateDialog checkUpdateDialog;
    private  String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
    };


    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        checkDialog = new CheckDialog(this);
        checkDialog.setOnPassWordCatchListener(this);
        checkUpdateDialog = new CheckUpdateDialog(this);
        userName = UserMessage.getInstance().getUsername();
        password = UserMessage.getInstance().getPassword();
        etPassword.setText(password);
        etUsername.setText(userName);
        //动态申请权限
        PermissionsChecker checker = PermissionsChecker.getInstance(this);
        if (checker.lacksPermissions(PERMISSIONS)) {
            checker.getPerMissions(PERMISSIONS);
            return;
        }
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        hideTitleLayout(true);
        if (NetworkUtil.isNetworkAvailable()) {
            if (mPresenter != null) {
                mPresenter.getVersion();
            }
        }
    }

    @OnClick(R.id.tv_login)
    public void login() {
        userName = etUsername.getText().toString();
        password = etPassword.getText().toString();
        if (G.isEmteny(userName) || G.isEmteny(password)) {
            G.showToast(this, "用户名或密码不能为空！");
            return;
        }
        if (NetworkUtil.isNetworkAvailable()) {
            mPresenter.login();
        } else {
            G.showToast(this, "请连接网络..");
        }
    }

    /**
     * 开启定位服务
     */
    private Intent serviceIntent;

    public void startLocation() {
        serviceIntent = new Intent(this, LocationService.class);
        serviceIntent.setAction(Constants.START_LOCATIONS_ERVICE);
        startService(serviceIntent);
    }

    @Override
    public String getUserName() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void setLoginInfo(LoginInfo loginInfo) {
        UserAction.saveLoginMessage(userName, password);
        UserAction.saveLoginInfo(loginInfo);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        G.log("fdadaaa" + loginInfo.getType());
        if (loginInfo.getType() == 3) {
            startLocation();
        }
    }

    @Override
    public void setVersion(Version version) {
        String versionName = PackageUtils.getVersionName(this);
        if (versionName.compareTo(version.getVersion()) < 0) {
            //UserMessage.getInstance().setUpdate(true);
            checkUpdateDialog.show();
            checkUpdateDialog.setDownLoadUrl(version.getDownload_url());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            checkDialog.show();
            checkDialog.clean();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onCatch(String password) {
        if (password.equals("1234")) {
            YunmayiApplication.exitApp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceIntent!=null){
            stopService(serviceIntent);
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }
}
