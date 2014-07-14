package fr.poulpi.pegasus.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pokito on 31/05/2014.
 */
public class CTPGeoJson implements Serializable {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<CTPGeoJsonProperties> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<CTPGeoJsonProperties> properties) {
        this.properties = properties;
    }

    public ArrayList<ArrayList<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    String type;
    ArrayList<CTPGeoJsonProperties> properties;
    ArrayList<ArrayList<Double>> coordinates;
}
