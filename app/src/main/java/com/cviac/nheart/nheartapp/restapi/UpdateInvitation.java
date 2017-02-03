package com.cviac.nheart.nheartapp.restapi;

/**
 * Created by user on 1/19/2017.
 */

public class UpdateInvitation {

    private String mobile;

    private String to_mobile;
    private String status;

    public String getTo_mobile() {
        return to_mobile;
    }

    public void setTo_mobile(String to_mobile) {
        this.to_mobile = to_mobile;
    }



    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public UpdateInvitation() {
    }



}
