package com.yun.mayi.pda.module.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.drawer.scan.DrawerScanActivity;
import com.yun.mayi.pda.module.join.loadscan.LoadScanCodeActivity;
import com.yun.mayi.pda.module.join.scan.OrderScanActivity;
import com.yun.mayi.pda.module.join.search.SupportSearchActivity;
import com.yun.mayi.pda.module.support.corder.CancleOrderActivity;
import com.yun.mayi.pda.module.support.lesspark.LessPackActivity;
import com.yun.mayi.pda.module.support.packbox.ParkBoxManagerActivity;
import com.yun.mayi.pda.module.support.packed.HasPackActivity;
import com.yun.mayi.pda.module.support.packing.WaitPackActivity;
import com.yun.mayi.pda.module.support.packtotal.TotalHasPackActivity;
import com.yun.mayi.pda.module.support.sendnote.TotalWaitPackActivity;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;


public class WorkBarFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    /**
     * 供应商
     */
    @BindView(R.id.ll_supply)
    LinearLayout llSupply;
    /**
     * 加盟商
     */
    @BindView(R.id.ll_join)
    LinearLayout llJoin;
    /**
     * 司机
     */
    @BindView(R.id.ll_drawer)
    LinearLayout llDrawer;
    private String mParam1;

    public static WorkBarFragment newInstance(String param1) {
        WorkBarFragment fragment = new WorkBarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void initView() {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        G.log("xxxxxxxxx" + UserMessage.getInstance().getType());
       /* llJoin.setVisibility(View.GONE);
        llSupply.setVisibility(View.GONE);
        llDrawer.setVisibility(View.VISIBLE);*/
        switch (UserMessage.getInstance().getType()) {
        case 1://供应商
            case 4://供应商--子账号
                llJoin.setVisibility(View.GONE);
                llSupply.setVisibility(View.VISIBLE);
                llDrawer.setVisibility(View.GONE);
                break;
            case 2://分拣员-加盟商操作---加盟商
            case 5://加盟商
                llJoin.setVisibility(View.VISIBLE);
                llSupply.setVisibility(View.GONE);
                llDrawer.setVisibility(View.GONE);
                break;
            case 3://司机
                llJoin.setVisibility(View.GONE);
                llSupply.setVisibility(View.GONE);
                llDrawer.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_work_bar;
    }


    @OnClick(R.id.tv_wait_picking_order)
    public void waitPickingOrder() {
        Intent intent = new Intent(getActivity(), WaitPackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_has_packing_order)
    public void hasPickingOrder() {
        Intent intent = new Intent(getActivity(), HasPackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_pick_manager)
    public void pickManager() {
        Intent intent = new Intent(getActivity(), ParkBoxManagerActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.tv_out_of_stock)
    public void outofStock() {
        Intent intent = new Intent(getActivity(), LessPackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_cancel_order)
    public void cancelOrder() {
        Intent intent = new Intent(getActivity(), CancleOrderActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.tv_send_note)
    public void sendNote() {
        Intent intent = new Intent(getActivity(), TotalWaitPackActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.tv_all_pack)
    public void allPack() {
        Intent intent = new Intent(getActivity(), TotalHasPackActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.tv_reject_check)
    public void rejectCheck() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_REJECT_CHECK);
        startActivity(intent);

    }

    @OnClick(R.id.tv_goods_conform)
    public void goodsConform() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_CONFORM_GOODS);
        startActivity(intent);

    }

    @OnClick(R.id.tv_order_scan)
    public void orderScan() {
        Intent intent = new Intent(getActivity(), OrderScanActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.tv_wait_pack)
    public void waitPack() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_WAIT_PACK);
        startActivity(intent);

    }

    @OnClick(R.id.tv_goods_pack)
    public void goodsPack() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_GOODS_PACK);
        startActivity(intent);

    }

    @OnClick(R.id.tv_pack_manager)
    public void packManager() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_MANAGER_PACK);
        startActivity(intent);
    }

    @OnClick(R.id.tv_scan_car)
    public void carScan() {
        Intent intent = new Intent(getActivity(), LoadScanCodeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_drawer_goods)
    public void drawerGoods() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_DRAWER_TALLYING);
        startActivity(intent);
    }

    @OnClick(R.id.tv_pack_car)
    public void packCar() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_LOAD_CAR);
        startActivity(intent);
    }

    @OnClick(R.id.tv_gathering)
    public void gathering() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_GATHERING);
        startActivity(intent);
    }
    @OnClick(R.id.tv_drawer_position)
    public void drawerPosition() {
   /*     Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivity(intent);*/
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_DEPART);
        startActivity(intent);

    }
    @OnClick(R.id.tv_drawer_scan)
    public void drawerScan() {
        Intent intent = new Intent(getActivity(), DrawerScanActivity.class);
        startActivity(intent);

    }
    @OnClick(R.id.tv_order_manager)
    public void orderManager() {
        Intent intent = new Intent(getActivity(), SupportSearchActivity.class);
        intent.putExtra("source", Constants.SOURE_ORDER_MANAGER);
        startActivity(intent);

    }

    private void init() {
        View dView = getActivity().getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot.png";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
    }


}
