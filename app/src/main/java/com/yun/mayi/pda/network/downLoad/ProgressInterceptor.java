package com.yun.mayi.pda.network.downLoad;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作者： wh
 * 时间：  2018/3/14
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ProgressInterceptor  implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }

}
//car类中发现在其构造方法上加了一个@Inject，于Activity Car类的声明呼应。我们再往下看


