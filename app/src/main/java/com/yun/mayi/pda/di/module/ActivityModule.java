package com.yun.mayi.pda.di.module;

import android.app.Activity;
import android.content.Context;

import com.yun.mayi.pda.di.scope.ContextLife;
import com.yun.mayi.pda.di.scope.PerActivity;

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
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    @ContextLife("Activity")
    Context provideActivityContext() {
        return activity;
    }

    @PerActivity
    @Provides
    Activity provideActivity() {
        return activity;
    }


}
