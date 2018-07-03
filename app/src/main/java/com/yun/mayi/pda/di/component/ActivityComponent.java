package com.yun.mayi.pda.di.component;

import android.content.Context;

import com.yun.mayi.pda.di.module.ActivityModule;
import com.yun.mayi.pda.di.scope.ContextLife;
import com.yun.mayi.pda.di.scope.PerActivity;
import com.yun.mayi.pda.module.drawer.gather.GatheringActivity;
import com.yun.mayi.pda.module.drawer.manager.OrderManagerActivity;
import com.yun.mayi.pda.module.drawer.manager.OrderManagerDetailActivity;
import com.yun.mayi.pda.module.join.loadpack.WaitPackCarActivity;
import com.yun.mayi.pda.module.join.loadpack.WaitPackCarDetailActivity;
import com.yun.mayi.pda.module.drawer.scan.DrawerScanActivity;
import com.yun.mayi.pda.module.drawer.tallying.TallyingActivity;
import com.yun.mayi.pda.module.drawer.tallying.TallyingDetailActivity;
import com.yun.mayi.pda.module.join.conform.ConformGoodsActivity;
import com.yun.mayi.pda.module.join.goodspack.GoodsPackActivity;
import com.yun.mayi.pda.module.join.goodspack.GoodsParkDetailActivity;
import com.yun.mayi.pda.module.join.loadscan.LoadScanCodeActivity;
import com.yun.mayi.pda.module.drawer.depart.DepartActivity;
import com.yun.mayi.pda.module.join.manager.PackManagerActivity;
import com.yun.mayi.pda.module.join.manager.PackManagerDetailActivity;
import com.yun.mayi.pda.module.join.waitpack.WaitPackedActivity;
import com.yun.mayi.pda.module.join.waitpack.WaitPackedDetailActivity;
import com.yun.mayi.pda.module.join.reject.RejectCheckDetailActivity;
import com.yun.mayi.pda.module.join.scan.OrderScanActivity;
import com.yun.mayi.pda.module.join.search.SupportSearchActivity;
import com.yun.mayi.pda.module.login.LoginActivity;
import com.yun.mayi.pda.module.support.corder.CancleOrderActivity;
import com.yun.mayi.pda.module.support.lesspark.LessPackActivity;
import com.yun.mayi.pda.module.support.lesspark.LessParkDetailActivity;
import com.yun.mayi.pda.module.support.packbox.PackBoxDetailActivity;
import com.yun.mayi.pda.module.support.packbox.ParkBoxManagerActivity;
import com.yun.mayi.pda.module.support.packed.HasPackActivity;
import com.yun.mayi.pda.module.support.packed.HasPackDetailActivity;
import com.yun.mayi.pda.module.support.packing.WaitPackActivity;
import com.yun.mayi.pda.module.support.packing.WaitPackDetailActivity;
import com.yun.mayi.pda.module.support.packtotal.TotalHasPackActivity;
import com.yun.mayi.pda.module.support.sendnote.TotalWaitPackActivity;

import dagger.Component;

/**
 * 作者： wh
 * 时间：  2018/3/20
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    @ContextLife("Activity")
    Context getActivityContext();

    void inject(WaitPackActivity waitPackActivity);

    void inject(WaitPackDetailActivity waitPackDetailActivity);

    void inject(HasPackActivity hasPackActivity);

    void inject(HasPackDetailActivity hasPackDetailActivity);

    void inject(PackBoxDetailActivity packBoxDetailActivity);

    void inject(ParkBoxManagerActivity parkBoxManagerActivity);

    void inject(LoginActivity loginActivity);

    void inject(LessPackActivity lessPackActivity);

    void inject(LessParkDetailActivity lessParkDetailActivity);

    void inject(CancleOrderActivity cancleOrderActivity);

    void inject(SupportSearchActivity rejectCheckActivity);

    void inject(RejectCheckDetailActivity rejectCheckDetailActivity);

    void inject(TotalWaitPackActivity sendNoteActivity);

    void inject(TotalHasPackActivity packTotalActivity);

    void inject(ConformGoodsActivity goodsActivity);

    void inject(OrderScanActivity orderScanActivity);

    void inject(WaitPackedActivity waitPackActivity);

    void inject(GoodsPackActivity goodsPackActivity);

    void inject(WaitPackedDetailActivity waitPackedDetailActivity);

    void inject(GoodsParkDetailActivity goodsParkDetailActivity);

    void inject(PackManagerActivity packManagerActivity);

    void inject(PackManagerDetailActivity packManagerDetailActivity);

    void inject(LoadScanCodeActivity pickCardActivity);

    void inject(WaitPackCarActivity waitPackCarActivity);

    void inject(WaitPackCarDetailActivity packCarActivity);

    void inject(TallyingActivity tallyingActivity);

    void inject(TallyingDetailActivity tallyingDetailActivity);

    void inject(GatheringActivity gatheringActivity);

    void inject(DrawerScanActivity drawerScanActivity);

    void inject(DepartActivity departActivity);

    void inject(OrderManagerActivity orderManagerActivity);

    void inject(OrderManagerDetailActivity orderManagerDetailActivity);
}
