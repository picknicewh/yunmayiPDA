package com.yun.mayi.pda.base;

import android.support.annotation.Nullable;

import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.di.component.DaggerActivityComponent;
import com.yun.mayi.pda.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * 作者： wh
 * 时间：  2017/10/10
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {
    @Nullable
    @Inject
    protected T mPresenter;
    /**
     * 依赖注册
     */
    protected DaggerActivityComponent mDaggerActivityComponent;



    @Override
    public void beforeInit() {
        super.beforeInit();
        mDaggerActivityComponent = (DaggerActivityComponent) DaggerActivityComponent.builder().
                appComponent(YunmayiApplication.getDaggerAppComponent()).
                activityModule(new ActivityModule(this)).build();
        initInject();
        attachView();
    }

    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        if (mPresenter!=null){
            mPresenter.unSubscribe();
        }
        detachView();
        super.onDestroy();
    }


    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
