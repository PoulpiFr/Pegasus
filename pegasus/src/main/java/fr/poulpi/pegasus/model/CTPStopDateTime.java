package fr.poulpi.pegasus.model;

import java.util.Date;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPStopDateTime {

    //A date time
    Date date_time;
    //A stop point
    CTPStopPoint stop_point;

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public CTPStopPoint getStop_point() {
        return stop_point;
    }

    public void setStop_point(CTPStopPoint stop_point) {
        this.stop_point = stop_point;
    }
}
