package fr.poulpi.pegasus.model;

import java.io.Serializable;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPPoiType implements Serializable {

    //Identifier of the poi type
    String id;
    //Name of the poi type
    String name;

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
}
