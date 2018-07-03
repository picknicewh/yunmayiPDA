package com.yun.mayi.pda.utils;

/**
 * 作者： wh
 * 时间：  2018/5/30
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.yun.mayi.pda.utils.JsonUtils.TYPE_ARRAY_JSON;
import static com.yun.mayi.pda.utils.JsonUtils.TYPE_ERROR_JSON;
import static com.yun.mayi.pda.utils.JsonUtils.TYPE_OBJECT_JSON;

/**
 * #test='{"xhr":[{"id":8,"req":"GET /rest/parentrest/api/pc/student/getStudentLevelExamByStudentId?studentId=1653585","start":1524226703728,"du":4949,"cb":0,"status":200,"err":0,"rec":626,"send":0}],"password":"136646","username":"wh"}'
 * #test='{"username":[{"id":8,"passwd":"123456"}]}'
 * #test='{"username":[{"id":8}]}'
 * #test='{"username":"admin","passwd":"123456"}'
 * <p>
 * <p>
 * 4个场景的son字符串，需要把json字符串的value值逐一替换为 “test”.
 * <p>
 * eg:#test='{"username":"admin","passwd":"123456"}'
 * 替换之后
 * <p>
 * '{"username”:”\”test\””,”passwd":"123456"}'
 * <p>
 * '{"username”:”admin”,”passwd”:”\”test\””}’
 * <p>
 * <p>
 * {"username":"admin","result":[{"id":"8","du":4949,"rec":626,"err":0}]}
 * <p>
 * {"username":"替换","result":[{"id":"8","du":4949,"rec":626,"err":0}]}
 * <p>
 * {"username":"admin","result":[{"id":"替换","du":4949,"rec":626,"err":0}]}
 * <p>
 * {"username":"admin","result":[{"id":"8","du":替换,"rec":626,"err":0}]}
 * <p>
 * {"username":"admin","result":[{"id":"8","du":4949,"rec":替换,"err":0}]}
 * <p>
 * {"username":"admin","result":[{"id":"8","du":4949,"rec":626,"err":替换}]}
 */

public class TextUtils {
    public static final String TAG = TextUtils.class.getSimpleName();

    /**
     * 替换字符串
     *
     * @param json 输入字符串
     */
    public static String textReplace(String json) throws IOException, JSONException {
        int type = JsonUtils.isJson(json);
        StringBuffer stringBuffer = new StringBuffer();
        List<Map<String, Object>> mapList = null;
        switch (type) {
            case TYPE_ERROR_JSON:
                stringBuffer.append("非正确的json串");
                break;
            case TYPE_OBJECT_JSON:
                mapList = JsonUtils.jsonToMapList(json, type);
                for (int i = 0; i < mapList.size(); i++) {
                    Map<String, Object> map = mapList.get(i);
                    for (int j = 0; j < map.size(); j++) {
                        String result = replacePosition(j, map);
                        stringBuffer.append(result).append("\n");
                    }
                }
                break;
            case TYPE_ARRAY_JSON:
                mapList = JsonUtils.jsonToMapList(json, type);
                for (int i = 0; i < mapList.size(); i++) {
                    Map<String, Object> map = mapList.get(i);
                    for (int j = 0; j < map.size(); j++) {
                        String result = replacePosition(j, map);
                        stringBuffer.append(result).append("\n");
                    }
                }
                break;
        }
        G.log(TAG + stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * 把每个value的进行json字符串判断，如果是字符串则，转换成map
     *
     * @param map 所有的map键值对
     */
    private static List<Map<String, Object>> getValueMapList(Map<String, Object> map) throws IOException, JSONException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String value = entry.getValue().toString();
            if (value.contains("[") && value.contains("]")) {
                JSONArray jsonArray = new JSONArray(value.toString());
                if (jsonArray != null) {
                    //     List<Map<String, Object>> maps = JsonUtils.jsonArrayToMapList(jsonArray);
                    //   mapList.addAll(maps);
                } else {
                    continue;
                }
            } else {
                if (value.contains(":")) {
                 //   JSONObject jsonObject = new JSONObject(value);
                 //   Map<String, Object> objectMap = JsonUtils.jsonObjectToMap(jsonObject);
              //      mapList.add(objectMap);
                } else {
                    continue;
                }
            }
        }
        return mapList;
    }

    /**
     * 获取的json数据转换成map
     *
     * @param json 替换的json字符串
     */
    private static Map<String, Object> jsonToMap(String json) throws IOException, JSONException {
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject obj = new JSONObject(json);
        Iterator it = obj.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = obj.get(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }

    /**
     * map的第几个元素替换
     *
     * @param position w替换位置
     * @param map      键值对map
     */
    private static String replacePosition(int position, Map<String, Object> map) {
        Map<String, Object> orgMap = new HashMap<>();
        Set<Map.Entry<String, Object>> set = map.entrySet();
        String replaceString = "\"test\"";
        int i = 0;
        for (Map.Entry<String, Object> entry : set) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (position == i) {
                orgMap.put(key, replaceString);
            } else {
                orgMap.put(key, value);
            }
            i++;
        }
        JSONObject obj = new JSONObject(orgMap);
        return obj.toString();
    }


}

















































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































