package com.yun.mayi.pda.network.downLoad;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 作者： wh
 * 时间：  2018/3/14
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;

    private BufferedSource bufferedSource;
    public ProgressResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                //实时发送当前已读取的字节和总字节
                RxBus.getInstance().post(new FileLoadEvent(contentLength(), bytesReaded));
                return bytesRead;
            }
        };
    }
}
