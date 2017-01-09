package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 1/3/2017.
 */

public class SkezoInfo implements Serializable{

    private int imgUrl;

    private String description;

    private String date;

    private String title;



    public SkezoInfo(int imgUrl, String description, String date, String title) {
        this.imgUrl = imgUrl;
        this.description = description;
        this.date = date;
        this.title = title;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
