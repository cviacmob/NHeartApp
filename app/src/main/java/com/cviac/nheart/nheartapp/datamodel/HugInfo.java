package com.cviac.nheart.nheartapp.datamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HugInfo extends Model implements Serializable {


    public HugInfo(int imgUrl, String mob, String description, Date date, String title) {
        this.imgUrl = imgUrl;
        this.mob = mob;
        this.description = description;

        this.title = title;
    }

    private int imgUrl;



   private  String mob;

    private String description;

    private String date;

    private String title;



    public HugInfo(int imgUrl,String mob, String description, String date, String title) {
        this.imgUrl = imgUrl;
        this.mob=mob;
        this.description = description;
        this.date = date;
        this.title = title;
    }


    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
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

  /*  public String getformatteddate() {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return timeFormatter.format(date);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "YESTERDAY";
        } else {
            DateFormat dateform = new SimpleDateFormat("dd/MM/yy");
            return dateform.format(date);
        }
*/
    }


