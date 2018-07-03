package com.yun.mayi.pda.network.downLoad;

/**
 * 作者： wh
 * 时间：  2018/3/14
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class FileLoadEvent {
    long total;
    long bytesLoaded;

    public long getBytesLoaded() {
        return bytesLoaded;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadEvent(long total, long bytesLoaded) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
    }


}
