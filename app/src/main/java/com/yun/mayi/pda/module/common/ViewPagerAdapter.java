package com.yun.mayi.pda.module.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yun.mayi.pda.base.BaseFragment;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2017/8/21
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public List<BaseFragment> fragmentList;
//    private FragmentManager fm;
    private List<String> tags;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
      //  tags = new ArrayList<>();
  //      this.fm = fm;
        this.fragmentList = fragmentList;
    }
/*
    public void setNewFragments(List<BaseFragment> fragments) {
        if (this.tags != null) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            for (int i = 0; i < tags.size(); i++) {
                fragmentTransaction.remove(fm.findFragmentByTag(tags.get(i)));
            }
            fragmentTransaction.commit();
            fm.executePendingTransactions();
            tags.clear();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tags.add(makeFragmentName(container.getId(), getItemId(position)));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fm.beginTransaction().show(fragment).commit();
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragmentList.get(position);
        fm.beginTransaction().hide(fragment).commit();
    }
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }*/
   /* @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }*/

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position );
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
