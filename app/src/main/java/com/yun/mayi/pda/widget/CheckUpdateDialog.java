package com.yun.mayi.pda.widget;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.network.HttpUtils;
import com.yun.mayi.pda.network.downLoad.FileCallBack;
import com.yun.mayi.pda.network.downLoad.FileSubsrciber;
import com.yun.mayi.pda.utils.FileUtils;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.UpdateUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者： wh
 * 时间：  2018/3/13
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class CheckUpdateDialog extends AlertDialog {

    private Context mContext;
    private UpdateDialog updateDialog;
    private String url;

    public CheckUpdateDialog(Context context) {
        super(context);
        this.mContext = context;
        updateDialog = new UpdateDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_update);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

    public void setDownLoadUrl(String url) {
        this.url = url;
    }

    @OnClick(R.id.tv_conform)
    public void onViewClicked() {
        dismiss();
        if (url != null) {
            downLoadApk(url);
        }
    }

    private void downLoadApk(String url) {
        final String destFileDir = FileUtils.getApkFileLoadPath(mContext);
        final String destFileName = UpdateUtil.getSaveName(mContext);

        FileCallBack<ResponseBody> fileCallBack = new FileCallBack<ResponseBody>(destFileDir, destFileName) {
            @Override
            public void onNext(ResponseBody responseBody) {
                G.showToast(getContext(), "下载文件成功");
                UpdateUtil.installApk(getContext(), destFileDir);//下载完成之后安装
            }

            @Override
            public void progress(long progress, long total) {
                int max = (int) total;
                int mProgress = (int) progress;
                updateDialog.setProgressChange(max, mProgress);
            }

            @Override
            public void onStart() {
                G.showToast(getContext(), "开始下载");
                updateDialog.show();
            }

            @Override
            public void onCompleted() {
                updateDialog.dismiss();

            }

            @Override
            public void onError(String message) {
                G.showToast(getContext(), message);
            }
        };
        load(url, fileCallBack);
    }


    private void load(String url, final FileCallBack<ResponseBody> callBack) {
        HttpUtils.getDownloadManager().downLoadFile(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        callBack.saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubsrciber<>(callBack));
    }
}
