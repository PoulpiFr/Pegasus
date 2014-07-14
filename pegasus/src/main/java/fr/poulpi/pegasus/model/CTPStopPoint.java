package fr.poulpi.pegasus.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPStopPoint implements Serializable {

    //Identifier of the line
    String id;
    //Name of the line
    String name;
    //Coordinates of the stop point
    CTPCoord coord;
    //Administrative regions of the stop point in which is the stop point
    List<CTPAdminstrativeRegion> adminstrative_regions;
    //Equipments of the stop point
    List<String> equipments;
    //Stop Area containing this stop point
    CTPStopArea stop_area;

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

    public List<CTPAdminstrativeRegion> getAdminstrative_regions() {
        return adminstrative_regions;
    }

    public void setAdminstrative_regions(List<CTPAdminstrativeRegion> adminstrative_regions) {
        this.adminstrative_regions = adminstrative_regions;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    public CTPStopArea getStop_area() {
        return stop_area;
    }

    public void setStop_area(CTPStopArea stop_area) {
        this.stop_area = stop_area;
    }
}
