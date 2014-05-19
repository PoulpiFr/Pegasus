package fr.poulpi.pegasus.model;

import java.util.List;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPStopArea {

    //Identifier of the line
    String id;
    //Name of the line
    String name;
    //Coordinates of the stop point
    CTPCoord coord;
    //Administrative regions of the stop point in which is the stop area
    List<CTPAdminstrativeRegion> adminstrative_regions;
    //Equipments of the stop point
    List<String> equipments;
    //Stop points contained in this stop area
    List<CTPStopPoint> stop_points;

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

    public List<CTPStopPoint> getStop_points() {
        return stop_points;
    }

    public void setStop_points(List<CTPStopPoint> stop_points) {
        this.stop_points = stop_points;
    }
}
