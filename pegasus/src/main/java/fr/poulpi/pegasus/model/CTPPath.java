package fr.poulpi.pegasus.model;

import java.io.Serializable;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPPath implements Serializable {

    int duration;
    int length;
    int direction;
    String name;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
