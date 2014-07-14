package fr.poulpi.pegasus.model;

import java.io.Serializable;

/**
 * Created by pokito on 31/05/2014.
 */
public class CTPPlace implements Serializable {

    //The id of the embedded object
    String name;
    //The name of the embedded object
    String id;
    //The type of the embedded object
    String embedded_type;
    //Embedded Stop point
    CTPStopPoint stop_point;
    //Embedded Stop area
    CTPStopArea stop_area;
    //Embedded address
    CTPAddress address;
    //Embedded poi
    CTPPoi	poi;
    //Embedded adminstrative region
    CTPAdminstrativeRegion adminstrative_region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmbedded_type() {
        return embedded_type;
    }

    public void setEmbedded_type(String embedded_type) {
        this.embedded_type = embedded_type;
    }

    public CTPStopPoint getStop_point() {
        return stop_point;
    }

    public void setStop_point(CTPStopPoint stop_point) {
        this.stop_point = stop_point;
    }

    public CTPStopArea getStop_area() {
        return stop_area;
    }

    public void setStop_area(CTPStopArea stop_area) {
        this.stop_area = stop_area;
    }

    public CTPAddress getAddress() {
        return address;
    }

    public void setAddress(CTPAddress address) {
        this.address = address;
    }

    public CTPPoi getPoi() {
        return poi;
    }

    public void setPoi(CTPPoi poi) {
        this.poi = poi;
    }

    public CTPAdminstrativeRegion getAdminstrative_region() {
        return adminstrative_region;
    }

    public void setAdminstrative_region(CTPAdminstrativeRegion adminstrative_region) {
        this.adminstrative_region = adminstrative_region;
    }
}


