package fr.poulpi.pegasus.model;

import java.util.ArrayList;

/**
 * Created by pokito on 31/05/2014.
 */
public class CTPGeoJson {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CTPGeoJsonProperties getProperties() {
        return properties;
    }

    public void setProperties(CTPGeoJsonProperties properties) {
        this.properties = properties;
    }

    public ArrayList<ArrayList<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    String type;
    CTPGeoJsonProperties properties;
    ArrayList<ArrayList<Double>> coordinates;
}
