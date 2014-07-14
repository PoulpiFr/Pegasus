package fr.poulpi.pegasus.model;

import java.io.Serializable;

/**
 * Created by pokito on 31/05/2014.
 */
public class CTPGeoJsonProperties implements Serializable {

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    int length;

}
