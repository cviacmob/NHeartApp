package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;

/**
 * Created by admin1 on 11/24/2016.
 */

public class Category implements Serializable {
    private String category_id;
    private String parent_id;
    private String name;
    private String image;
    private String href;
    private String categories;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getImage(){
        return image;
    }
    public void setImagemage(String image){
        this.image=image;
    }
    public String getHref(){
        return href;
    }
    public void setHref(String href){
        this.href=href;
    }
    public String getCategories(){
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
}
