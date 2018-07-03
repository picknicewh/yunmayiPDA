package com.yun.mayi.pda.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 作者： wh
 * 时间：  2018/3/20
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
