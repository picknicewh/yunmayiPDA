package com.yun.mayi.pda.module.join.conform;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yun.mayi.pda.bean.AgentInfo;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：按平台查询收货列表的数据适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConformGoodsPageAdapter extends FragmentPagerAdapter {
    private List<AgentInfo> agentInfoList;
    private List<ConformGoodsFragment> conformGoodsFragments;

    public ConformGoodsPageAdapter(FragmentManager fm, List<AgentInfo> agentInfoList,    List<ConformGoodsFragment> conformGoodsFragments) {
        super(fm);
        this.agentInfoList = agentInfoList;
        this.conformGoodsFragments = conformGoodsFragments;

    }

    @Override
    public Fragment getItem(int position) {
        return conformGoodsFragments.get(position);
    }

    @Override
    public int getCount() {
        return agentInfoList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return agentInfoList.get(position).getAgentName();
    }
}
