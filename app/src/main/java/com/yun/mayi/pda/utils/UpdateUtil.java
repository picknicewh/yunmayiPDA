package com.yun.mayi.pda.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.yun.mayi.pda.network.MD5;

import java.io.File;

/**
 * 作者： wh
 * 时间：  2018/3/13
 * 名称：版本更新工具类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class UpdateUtil {
    /**
     * 安装APK文件
     *
     * @param context  上线文本
     * @param savePath 保存路径
     */
    public static boolean installApk(Context context, String savePath) {
        File apkFile = new File(savePath, getSaveName(context));
        if (!apkFile.exists()) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
        context.startActivity(intent);
        G.log("xxxxx"+apkFile.getPath());
        return true;
    }
    /**
     * apk的保存名字
     * @param context 上下文本
     */
    public static String getSaveName(Context context) {
        String apkName = PackageUtils.getApkName(context);
        String version = MD5.MD5Encode(String.valueOf(PackageUtils.getVersionCode(context)));
        return apkName + version + ".apk";
    }
}
