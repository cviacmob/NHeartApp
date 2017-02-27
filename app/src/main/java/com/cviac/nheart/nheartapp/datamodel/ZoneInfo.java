package com.cviac.nheart.nheartapp.datamodel;

public class ZoneInfo {

    private String name;

    private String zone_id;

    public ZoneInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    @Override
    public String toString() {
        return name;
    }
}
