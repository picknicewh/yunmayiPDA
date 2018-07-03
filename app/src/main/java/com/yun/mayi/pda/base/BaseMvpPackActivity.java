package com.yun.mayi.pda.base;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.di.component.DaggerActivityComponent;
import com.yun.mayi.pda.di.module.ActivityModule;
import com.yun.mayi.pda.utils.DateUtil;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2017/10/10
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public abstract class BaseMvpPackActivity<T extends BasePresenter> extends BaseActivity {
    @Nullable
    @Inject
    protected T mPresenter;
    /**
     * 开始时间
     */
    @BindView(R.id.tv_start_time)
    public TextView tvStartTime;
    /**
     * 结束时间
     */
    @BindView(R.id.tv_end_time)
    public TextView tvEndTime;
    /**
     * 搜索字
     */
    @BindView(R.id.et_sign)
    public ClearEditText etSign;
    @BindView(R.id.ll_search)
    public LinearLayout llSearch;
    /**
     * 记录选中开始日期
     */
    private Calendar startCalendar = Calendar.getInstance();
    /**
     * 记录选中结束日期
     */
    private Calendar endCalendar = Calendar.getInstance();
    /**
     * 开始时间选择
     */
    private TimePickerView startTimePickerView;
    /**
     * 结束时间选择
     */
    private TimePickerView endTimePickerView;
    /**
     * 开始时间
     */
    public Date startDate;
    /**
     * 结束时间
     */
    public Date endDate;
    /**
     * 依赖注册
     */
    protected DaggerActivityComponent mDaggerActivityComponent;
    public String keyword="";

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
    public void initView() {
         keyword = getClass().getName().substring(getClass().getName().lastIndexOf(".")+1);
        if (YunmayiApplication.getStartDate(keyword)==null || YunmayiApplication.getEndDate(keyword)==null){
            startDate = DateUtil.getNeedTime(16, 0, 0, -1);
            endDate = DateUtil.getNeedTime(16, 0, 0, 0);
            YunmayiApplication.putEndDate(keyword,endDate);
            YunmayiApplication.putStartDate(keyword,startDate);
        }
        startDate = YunmayiApplication.getStartDate(keyword);
        endDate = YunmayiApplication.getEndDate(keyword);
        tvStartTime.setText(DateUtil.getFormatHourDate(startDate));    //默认日期
        tvEndTime.setText(DateUtil.getFormatHourDate(endDate));  //默认日期的后7天
        startCalendar.setTime(startDate);//默认日期选中的开始时间
        endCalendar.setTime(endDate);
        startTimePickerView = DateUtil.getTimePickerView("开始时间", this, startCalendar, startListener);
        endTimePickerView = DateUtil.getTimePickerView("结束时间", this, endCalendar, endListener);
    }

    public void setSearchGone() {
        llSearch.setVisibility(View.GONE);
    }

    private TimePickerView.OnTimeSelectListener startListener = new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            YunmayiApplication.putStartDate(keyword,date);
            startCalendar.setTime(date);
            startDate = date;
            String mDate = DateUtil.getFormatHourDate(date);
            tvStartTime.setText(mDate);
            onDataChange();
            onDataChangeListener(date,endDate);

        }
    };
    private TimePickerView.OnTimeSelectListener endListener = new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            YunmayiApplication.putEndDate(keyword,date);
            endCalendar.setTime(date);
            endDate = DateUtil.geteEndDate(date);
            String mDate = DateUtil.getFormatHourDate(date);
            tvEndTime.setText(mDate);
            onDataChange();
            onDataChangeListener(startDate,date);
        }
    };


    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                startTimePickerView.show();
                break;
            case R.id.tv_end_time:
                endTimePickerView.show();
                break;
        }

    }
    public  void onDataChangeListener(Date startDate,Date endDate){

    }
    public abstract void onDataChange();

    @Override
    protected void onDestroy() {
        mPresenter.unSubscribe();
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
