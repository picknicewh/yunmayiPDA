package com.yun.mayi.pda.network;

import com.yun.mayi.pda.utils.G;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by smile on 2017/5/6.
 */
public class Signature {
    //获取时间戳
    public static long currentTimeSecond() {
        long timeMillis = System.currentTimeMillis();
        String timeStr = String.valueOf(timeMillis / 1000);
        return Long.valueOf(timeStr);
    }

    /**
     * 获取 sign
     * @param map
     * @param secret
     * @return
     */
    public static String getSign(Map<String, Object> map, String secret) {
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        String[] array2sort = list.toArray(new String[list.size()]);
        Arrays.sort(array2sort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder("");
        for (String value : array2sort) {
            sb.append(value);
        }
        sb.append("app_secret=" + secret);
        G.log("xxxxx:sign="+sb.toString());
        return MD5.MD5Encode(sb.toString()).toUpperCase();
    }

}
