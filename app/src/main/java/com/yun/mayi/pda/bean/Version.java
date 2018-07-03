package com.yun.mayi.pda.bean;

import java.io.Serializable;

/**
 * Created by ys on 2017/6/24.
 * 版本信息
 */

public class Version implements Serializable {

    /**
     * id : 14
     * name : 蚂蚁掌柜
     * version : 1.2.0
     * size : 3.54MB
     * publish_date : 2017-06-24
     * logo : http://pifa.yunmayi.com/upload/2017/06/24/103e7506a2e539184d49a34fb90d4d7d.png
     * download_url : http://pifa.yunmayi.com/upload/2017/06/24/a34179213db640eb2f4f5a2f4fa267ce.apk
     * content : 手机管理APP
     * introduce : 介绍介绍
     * type : 2
     * package_name : com.yunmayi.app.manage
     */

    private int id;
    private String name;
    private String version;
    private String size;
    private String publish_date;
    private String logo;
    private String download_url;
    private String content;
    private String introduce;
    private int type;
    private String package_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}
