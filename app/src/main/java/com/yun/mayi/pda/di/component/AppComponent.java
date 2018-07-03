package com.yun.mayi.pda.di.component;

import android.content.Context;

import com.yun.mayi.pda.di.module.AppModule;
import com.yun.mayi.pda.di.scope.ContextLife;
import com.yun.mayi.pda.di.scope.PerApp;

import dagger.Component;

/**
 * 作者： wh
 * 时间：  2018/3/20
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    Context getApplicationContext();
}
