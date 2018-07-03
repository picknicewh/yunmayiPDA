package com.yun.mayi.pda.network;

import com.google.gson.Gson;
import com.yun.mayi.pda.utils.G;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装用于网络请求的网络地址和网络地址, 并按照格式输出 注意自己保证数据的完整性
 */
public class RequestParameter {

    /** 保存网络请求的参数及值 */
    private Map<String,Object> mParams = null;
    /**
     * 网络请求的参数中默认自带某些接口参数
     * boolean isSetName 是否设置用户名
     */
    public RequestParameter() {
        this.mParams = new HashMap<String ,Object>();
    }

    /**
     * 设置用于网络请求的参数及参数值
     * @param name 参数名称
     * @param value 参数值
     */
    public void setParam(String name, Object value) {
        if(value instanceof String){
            if (!G.isEmteny((String)value)){
                mParams.put(name, value);
            }
        }else{
            mParams.put(name, value);
        }
    }
    /**
     * 设置公共参数
     */
    private void setCommonParams(){
        mParams.put("app_id", Configure.AppId);
        mParams.put("timestamp", Signature.currentTimeSecond());
        String sign = Signature.getSign(mParams,Configure.Secret);
        mParams.put("sign",sign);
    }

    /**
     * post参数
     * @return
     */
    public String postParams() {
        setCommonParams();
        String postInfoStr =  new Gson().toJson(mParams);
        return postInfoStr;
    }
    /**
     * get参数
     * @return
     */
    public Map getMapParams() {
        setCommonParams();
        return mParams;
    }
}
