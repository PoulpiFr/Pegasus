package fr.poulpi.pegasus.model;

import java.util.List;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPAddress {

    //Identifier of the address
    String id;
    //Name of the address
    String name;
    //Coordinates of the address
    CTPCoord coord;
    //House number of the address
    int house_number;
    //Administrative regions of the stop point in which is the stop area
    List<CTPAdminstrativeRegion> adminstrative_regions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CTPCoord getCoord() {
        return coord;
    }

    public void setCoord(CTPCoord coord) {
        this.coord = coord;
    }

    public int getHouse_number() {
        return house_number;
    }

    public void setHouse_number(int house_number) {
        this.house_number = house_number;
    }

    public List<CTPAdminstrativeRegion> getAdminstrative_regions() {
        return adminstrative_regions;
    }

    public void setAdminstrative_regions(List<CTPAdminstrativeRegion> adminstrative_regions) {
        this.adminstrative_regions = adminstrative_regions;
    }
}
