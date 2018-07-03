package com.yun.mayi.pda.di.module;

import android.content.Context;

import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.di.scope.ContextLife;
import com.yun.mayi.pda.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * 作者： wh
 * 时间：  2018/3/20
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@Module
public class AppModule {
    private YunmayiApplication application;

    public AppModule(YunmayiApplication application) {
        this.application = application;
    }

    @PerApp
    @Provides
    @ContextLife("Application")
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }
}
