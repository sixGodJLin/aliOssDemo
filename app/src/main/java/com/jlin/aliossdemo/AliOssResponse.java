package com.jlin.aliossdemo;

import java.io.Serializable;

/**
 * @author JLin.
 * @date 2019/11/20
 * @describe aliOssUtils 阿里云OSS获取token响应实体类
 */
public class AliOssResponse implements Serializable {
    private static final long serialVersionUID = -2039515339828920677L;
    /**
     * code : 0
     * message : null
     * data : {"platformId":"CKCORE","responseCode":0,"responseMsg":"通信正常,处理成功","responseTime":"2019-12-24 08:56:22","fromChannelId":"CKDS_ANDROID","accessKeyId":"STS.NUPZgtybkpbXL6pPysgUDJRWm","accessKeySecret":"8aqy4cSCa4iFXMxR9Vc5WewFb3pPctg4CTQyWjNL1czY","securityToken":"CAIS5wF1q6Ft5B2yfSjIr5blEd3AlL1Kx6CzThDBtHkma9popZf8jzz2IHtLfHhqAuwYs/k3nmhU6/cSlqNyTIQAWUHfcZMptlOPY8c6Jtivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgS0DMis/vm+KDGtEqC1m+d4/QOuoH8LqKja8RRJ5plW7+3prctKfKQinUMtUUXrP0u0PEVqS2iucqGRkVc+AnDKe3Q/82w42wagAEYTdU++iZ6Tjb4K9r+GEQPoqJH8lT/WQdfmL6LNJk8r/JM0b0Pugo/VA/YbmgXfR0PwE/5JWQa9pPAez1GQEexeIT/9BVaqnpfJGqui8/+7InShOG+6xKPtBVwZ2jqWV/HFKWi2nFUqrSADO645esKVV7oitT9KNYIsxKAA8Pxcw==","expiration":"2019-12-24T01:56:23Z"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean implements Serializable {
        private static final long serialVersionUID = 1771859950854875196L;
        /**
         * platformId : CKCORE
         * responseCode : 0
         * responseMsg : 通信正常,处理成功
         * responseTime : 2019-12-24 08:56:22
         * fromChannelId : CKDS_ANDROID
         * accessKeyId : STS.NUPZgtybkpbXL6pPysgUDJRWm
         * accessKeySecret : 8aqy4cSCa4iFXMxR9Vc5WewFb3pPctg4CTQyWjNL1czY
         * securityToken : CAIS5wF1q6Ft5B2yfSjIr5blEd3AlL1Kx6CzThDBtHkma9popZf8jzz2IHtLfHhqAuwYs/k3nmhU6/cSlqNyTIQAWUHfcZMptlOPY8c6Jtivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgS0DMis/vm+KDGtEqC1m+d4/QOuoH8LqKja8RRJ5plW7+3prctKfKQinUMtUUXrP0u0PEVqS2iucqGRkVc+AnDKe3Q/82w42wagAEYTdU++iZ6Tjb4K9r+GEQPoqJH8lT/WQdfmL6LNJk8r/JM0b0Pugo/VA/YbmgXfR0PwE/5JWQa9pPAez1GQEexeIT/9BVaqnpfJGqui8/+7InShOG+6xKPtBVwZ2jqWV/HFKWi2nFUqrSADO645esKVV7oitT9KNYIsxKAA8Pxcw==
         * expiration : 2019-12-24T01:56:23Z
         */

        private String platformId;
        private String responseCode;
        private String responseMsg;
        private String responseTime;
        private String fromChannelId;
        private String accessKeyId;
        private String accessKeySecret;
        private String securityToken;
        private String expiration;

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseMsg() {
            return responseMsg;
        }

        public void setResponseMsg(String responseMsg) {
            this.responseMsg = responseMsg;
        }

        public String getResponseTime() {
            return responseTime;
        }

        public void setResponseTime(String responseTime) {
            this.responseTime = responseTime;
        }

        public String getFromChannelId() {
            return fromChannelId;
        }

        public void setFromChannelId(String fromChannelId) {
            this.fromChannelId = fromChannelId;
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }
    }
}
