package com.yun.mayi.pda.module.home;

import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseActivity;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.ViewPagerAdapter;
import com.yun.mayi.pda.module.home.message.MessageFragment;
import com.yun.mayi.pda.module.home.setting.SettingFragment;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.widget.CheckDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, CheckDialog.onPassWordCatchListener {
    /**
     * 主页面
     */
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    /**
     * 消息
     */
    @BindView(R.id.rb_message)
    RadioButton rbMessage;
    /**
     * 工作台
     */
    @BindView(R.id.rb_work_bar)
    RadioButton rbWorkBar;
    /**
     * 设置
     */
    @BindView(R.id.rb_setting)
    RadioButton rbSetting;
    /**
     * 群组
     */
    @BindView(R.id.rg_nav)
    RadioGroup rgNav;
    /**
     * 页面列表
     */
    private List<BaseFragment> fragmentList;
    /**
     * 适配器
     */
    private ViewPagerAdapter adapter;
    /**
     * 防止意外退出
     */
    private CheckDialog checkDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
      //  setTitleTextId(R.string.workBar);
        setTitleText("工作台"+"("+ UserMessage.getInstance().getUsername()+")");
        initFragment();
        getWindow().setFlags(0x80000000,0x80000000);
        rgNav.setOnCheckedChangeListener(this);
        checkDialog = new CheckDialog(this);
        checkDialog.setOnPassWordCatchListener(this);

    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(WorkBarFragment.newInstance("workbar"));
        fragmentList.add(MessageFragment.newInstance("message"));
        fragmentList.add(SettingFragment.newInstance("setting"));
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpMain.setAdapter(adapter);
        vpMain.setCurrentItem(Constants.HOME_WORKBAR);
        vpMain.addOnPageChangeListener(this);

    }

    @Override
    public void onAttachedToWindow() {

        super.onAttachedToWindow();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_message:
                vpMain.setCurrentItem(Constants.HOME_MESSAGE);
                setTitleTextId(R.string.message);

                break;
            case R.id.rb_setting:
                vpMain.setCurrentItem(Constants.HOME_SETTIG);
                setTitleTextId(R.string.setting);
                break;
            case R.id.rb_work_bar:
                vpMain.setCurrentItem(Constants.HOME_WORKBAR);
               // setTitleTextId(R.string.workBar);
                setTitleText("工作台"+"("+ UserMessage.getInstance().getUsername()+")");
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case Constants.HOME_WORKBAR:
                rbWorkBar.setChecked(true);
                rbMessage.setChecked(false);
                rbSetting.setChecked(false);
                setTitleText("工作台"+"("+ UserMessage.getInstance().getUsername()+")");

                break;
            case Constants.HOME_SETTIG:
                rbWorkBar.setChecked(false);
                rbMessage.setChecked(false);
                rbSetting.setChecked(true);
                setTitleTextId(R.string.setting);
                break;
            case Constants.HOME_MESSAGE:
                rbWorkBar.setChecked(false);
                rbMessage.setChecked(true);
                rbSetting.setChecked(false);

                setTitleTextId(R.string.message);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        G.log("--xxxxxxx"+"`1121232232");
        G.log("--xxxxxxx"+event.getKeyCode());
        if ((keyCode == KeyEvent.KEYCODE_BACK) ||keyCode==KeyEvent.KEYCODE_HOME ) {
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


}
