package fr.poulpi.pegasus.model;

import java.io.Serializable;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPCoord implements Serializable {

    //Longitude
    float lon;
    //Latitude
    float lat;

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
