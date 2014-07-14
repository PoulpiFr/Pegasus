package fr.poulpi.pegasus.model;

import java.io.Serializable;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPAdminstrativeRegion implements Serializable {

    //Identifier of the line
    String id;
    //Name of the line
    String name;
    //Coordinates of the stop point
    CTPCoord coord;
    //Level of the admin
    int level;
    //Zip code of the admin
    String zip_code;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
