package com.cviac.nheart.nheartapp.datamodel;

/**
 * Created by user on 12/15/2016.
 */


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.List;


public class Songs implements Serializable {



    private int event_img;
    private String event_title;

    private String event_album;

    private String event_duration;

    private String event_id;



    public int getEvent_img() {
        return event_img;
    }

    public void setEvent_img(int event_img) {
        this.event_img = event_img;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_album() {
        return event_album;
    }

    public void setevent_album(String event_album) {
        this.event_album = event_album;
    }

    public String getEvent_duration() {
        return event_duration;
    }

    public void setEvent_duration(String event_duration) {
        this.event_duration = event_duration;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }





}