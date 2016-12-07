package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;

/**
 * Created by admin1 on 12/2/2016.
 */

public class ProductDetail implements Serializable{

    private String product_id;
    private String name;
    private String description;
    private String thumb;
    private String rating;
    private String href;
   // private String price;
//    private String special;
//    private String images;
//    private String seo_h1;
//    private  String options;
//    private String minimum;
//    private String manufacturer;
//    private String model;
//    private String discounts;
//    private String reward;
//    private String points;
//    private String attribute_groups;


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getThumb(){
        return thumb;
    }
    public void setThumb(String thumb){
        this.thumb=thumb;
    }
    public String getRating(){
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getHref(){
        return href;
    }
    public void setHref(String href){
        this.href=href;
    }
   // public String getPrice(){
//        return price;
//    }
//    public void setPrice(String price)
//    {
//        this.price=price;
//    }
 //   public String getSpecial(){
//        return special;
//    }
//   public void setSpecial(String special)
//   {
//       this.special=special;
//   }
//    public String getImages(){
//        return images;
//    }
//    public void setImages(String images)
//    {
//        this.images=images;
//    }
//    public String getSeo_h1(){
//        return seo_h1;
//    }
//    public void setSeo_h1(String seo_h1)
//    {
//        this.seo_h1=seo_h1;
//    }
//    public String getOptions(){
//        return options;
//    }
//    public void setOptions(String options)
//    {
//        this.options=options;
//    }
//
//    public String getMinimum(){
//        return minimum;
//    }
//    public void setMinimum(String minimum)
//    {
//        this.minimum=minimum;
//    }
//    public String getManufacturer(){
//        return manufacturer;
//    }
//    public void setManufacturer(String manufacturer)
//    {
//        this.manufacturer=manufacturer;
//    }
//    public String getModel(){
//        return model;
//    }
//    public void setModel(String model)
//    {
//        this.model=model;
//    }
//    public String getDiscounts(){
//        return discounts;
//    }
//    public void setDiscounts(String discounts)
//    {
//        this.discounts=discounts;
//    }
//    public String getReward(){
//        return reward;
//    }
//    public void setReward(String reward)
//    {
//        this.reward=reward;
//    }
//    public String getPoints(){
//        return points;
//    }
//    public void setPoints(String points)
//    {
//        this.points=points;
//    }
//    public String getAttribute_groups(){
//        return attribute_groups;
//    }
//    public void setAttribute_groups(String attribute_groups)
//    {
//        this.attribute_groups=attribute_groups;
   }

