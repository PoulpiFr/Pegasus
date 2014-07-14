package fr.poulpi.pegasus.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPDisplay_informations implements Serializable {

    //The name of the network
    String network;
    //A direction
    String direction;
    //The commercial mode
    String commercial_mode;
    //The physical mode
    String physical_mode;
    //The label of the object
    String label;
    //The hexadecimal code of the line
    String color;
    //The code of the line
    String code;
    //A description
    String description;
    List<String> equipments;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCommercial_mode() {
        return commercial_mode;
    }

    public void setCommercial_mode(String commercial_mode) {
        this.commercial_mode = commercial_mode;
    }

    public String getPhysical_mode() {
        return physical_mode;
    }

    public void setPhysical_mode(String physical_mode) {
        this.physical_mode = physical_mode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }
}
