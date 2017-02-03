package com.cviac.nheart.nheartapp.datamodel;

import java.lang.ref.SoftReference;

/**
 * Created by user on 1/19/2017.
 */

public class Invitationinfo {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTo_mobile() {
        return to_mobile;
    }

    public void setTo_mobile(String to_mobile) {
        this.to_mobile = to_mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Invitationinfo(String name, String mobile, String to_mobile, String email) {
        this.name = name;
        this.mobile = mobile;
        this.to_mobile = to_mobile;
        this.email = email;
    }

    private String name;
    private String mobile;
    private String to_mobile;
    private String email;
}
