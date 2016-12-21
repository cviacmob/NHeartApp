package com.cviac.nheart.nheartapp.datamodel;

/**
 * Created by Cviac on 21/12/2016.
 */

public class MusicInfo {

    private String title;

    private String imgUrl;

    private String singers;

    private String duration;

    public MusicInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSingers() {
        return singers;
    }

    public void setSingers(String singers) {
        this.singers = singers;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
