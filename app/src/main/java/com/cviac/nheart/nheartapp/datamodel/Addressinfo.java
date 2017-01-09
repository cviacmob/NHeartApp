package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;

/**
 * Created by admin1 on 1/4/2017.
 */

public class Addressinfo implements Serializable {
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFulname() {
        return fulname;
    }

    public void setFulname(String fulname) {
        this.fulname = fulname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlternatemobile() {
        return alternatemobile;
    }

    public void setAlternatemobile(String alternatemobile) {
        this.alternatemobile = alternatemobile;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public String pincode;

    public Addressinfo(String fulname, String shipping, String city, String district, String state, String pincode, String mobile, String alternatemobile, String landmark) {
        this.fulname = fulname;
        this.shipping = shipping;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.mobile = mobile;
        this.alternatemobile = alternatemobile;
        this.landmark = landmark;
    }

    public String district;
    public String city;
    public String state;
    public String fulname;
    public String mobile;
    public String alternatemobile;
    public String landmark;
    public String shipping;

}
