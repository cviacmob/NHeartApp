package com.cviac.nheart.nheartapp.datamodel;

/**
 * Created by user on 1/19/2017.
 */

public class PairStatus {

    public PairStatus() {
    }

    private int code;
    private int id;
    private String desc;
    private String status;




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
