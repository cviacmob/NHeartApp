package com.cviac.nheart.nheartapp.datamodel;

public class CountryInfo {
    private String name;
    private String country_id;

    public CountryInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public String toString() {
        return name;
    }
}
