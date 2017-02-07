package com.cviac.nheart.nheartapp.restapi;

/**
 * Created by user on 2/7/2017.
 */
public class VerifyOTPResponse {

    private int code;
    private String desc;

    public VerifyOTPResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
