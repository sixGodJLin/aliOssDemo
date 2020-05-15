package com.jlin.aliossdemo;

import java.io.Serializable;

/**
 * @author JLin.
 * @date 2019/11/20
 * @describe aliOssUtils 阿里云oss上传实体类
 */
public class CommonBean implements Serializable {
    private static final long serialVersionUID = 1701542225159547681L;

    /**
     * appMobileModel : ZK-R322A
     * appVersion : 1.0.0
     * channelId : CKDS_ANDROID
     * channelNo : CKDS
     * channelPassword : CKDS
     * exchangeTime : 2019-12-23 14:09:00
     */

    private String appMobileModel;
    private String appVersion;
    private String channelId = "CKDS_ANDROID";
    private String channelNo = "CKDS";
    private String channelPassword = "CKDS";
    private String exchangeTime;

    public String getAppMobileModel() {
        return appMobileModel;
    }

    public void setAppMobileModel(String appMobileModel) {
        this.appMobileModel = appMobileModel;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelPassword() {
        return channelPassword;
    }

    public void setChannelPassword(String channelPassword) {
        this.channelPassword = channelPassword;
    }

    public String getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(String exchangeTime) {
        this.exchangeTime = exchangeTime;
    }
}
