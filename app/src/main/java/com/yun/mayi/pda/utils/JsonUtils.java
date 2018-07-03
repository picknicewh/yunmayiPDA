package com.yun.mayi.pda.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者： wh
 * 时间：  2018/5/31
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class JsonUtils {
    /**
     * 非正确的json串
     */
    public static final int TYPE_ERROR_JSON = 0;
    /**
     * 一个json对象
     */
    public static final int TYPE_OBJECT_JSON = 1;
    /**
     * 一个json数组
     */
    public static final int TYPE_ARRAY_JSON = 2;

    /**
     * 获取的JSONArray类型数据转换成List<Map<String, Object>>类型
     *
     * @param jsonArray 替换的jsonArray
     */
    public static List<Map<String, Object>> jsonArrayToMap(JSONArray jsonArray) throws JSONException, IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            mapList.addAll(jsonObjectToMapList(jsonObject));
        }
        return mapList;
    }

    /**
     * 把json串转换成为map列表
     *
     * @param json 转换的json串
     */
    public static List<Map<String, Object>> jsonToMapList(String json, int type) throws JSONException, IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        switch (type) {
            case TYPE_OBJECT_JSON:
                JSONObject jsonObject = new JSONObject(json);
                mapList.addAll(jsonObjectToMapList(jsonObject));
                break;
            case TYPE_ARRAY_JSON:
                JSONArray jsonArray = new JSONArray(json);
                mapList.addAll(jsonArrayToMap(jsonArray));
                break;
        }

        return mapList;
    }

    /**
     * 获取的jsonObject类型数据转换成map类型
     *
     * @param jsonObject 替换的jsonObject
     */
    private static Map<String, Object> jsonObjectToMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = jsonObject.get(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }

    /**
     * 获取的jsonObject类型数据转换成map类型
     *
     * @param jsonObject 替换的jsonObject
     */
    private static Map<String, Object> jsonObjectToMap(JSONObject jsonObject, String value) throws JSONException {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            resultMap.put(key, value);
        }
        return resultMap;
    }

    /**
     * 获取的json类型数据转换成map类型
     *
     * @param json 替换的json
     */
    private static Map<String, Object> jsonToMap(String json) throws JSONException {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        JSONObject jsonObject = new JSONObject(json);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = jsonObject.get(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }


    /**
     * 获取的jsonObject类型数据转换成map类型
     *
     * @param jsonObject 替换的jsonObject
     */
    public static List<Map<String, Object>> jsonObjectToMapList(JSONObject jsonObject) throws IOException, JSONException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = jsonObject.get(key);
            mapList.addAll(getJsonItemMapLength(jsonObject, value));
        }
        G.log("===xxxxxxx==>jsonObjectToMap" + mapList.toString());
        return mapList;
    }

    /**
     * map的第几个元素替换
     *
     * @param position w替换位置
     * @param map      键值对map
     */
    private static String replacePosition(int position, Map<String, Object> map) {
        Map<String, Object> orgMap = new LinkedHashMap<>();
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

    public static List<Map<String, Object>> getJsonItemMapLength(JSONObject jsonObject, Object value) throws JSONException, IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        int length = 1;
        String valueString = value.toString();
        int type = isJson(valueString);
        switch (type) {
            case TYPE_ERROR_JSON:
                Map<String, Object> resultMap = jsonObjectToMap(jsonObject);
                mapList.add(resultMap);
                break;
            case TYPE_OBJECT_JSON:
              //  length = getJsonObjectItemLength(valueString);
               // mapList.addAll(jsonToMapList(valueString,TYPE_OBJECT_JSON));
                break;
            case TYPE_ARRAY_JSON:
                length = getJsonArrayItemLength(valueString);
                JSONArray jsonArray = new JSONArray(valueString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    for (int j=0;j<length;j++){
                        String text = replacePosition(j, jsonObjectToMap(object));
                        mapList.add(jsonObjectToMap(jsonObject, text));
                    }
                }
                break;
        }
        return mapList;
    }

    /**
     * 获取某个json的item数量
     *
     * @param json json串
     */
    public static int getJsonObjectItemLength(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        G.log("===xxxxxxx==>getJsonObjectItemLength" + jsonObject.toString());
        Iterator it = jsonObject.keys();
        int i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
        }
        G.log("===xxxxxxx==>getJsonObjectItemLength--" + i);
        return i;
    }

    /**
     * 获取某个json的item数量
     *
     * @param json json串
     */
    public static int getJsonArrayItemLength(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        int count = 0;
        G.log("===xxxxxxx==>getJsonArrayItemLength" + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
           /* ;
            int length = getJsonObjectItemLength(jsonObject.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                for (int j=0;j<length;j++){
                    String text = replacePosition(j, jsonObjectToMap(object));
                    mapList.add(jsonObjectToMap(jsonObject, text));
                }
            }*/
            count += getJsonObjectItemLength(jsonObject.toString());
        }
        return count;
    }

    /**
     * 判断是否为json串
     */
    public static int isJson(String json) {
        int type = 0;
        try {
            new JSONObject(json);
            type = 1;
        } catch (JSONException e) {
            try {
                new JSONArray(json);
                type = 2;
            } catch (JSONException e1) {
                type = 0;
            }
        }
        return type;
    }
}
