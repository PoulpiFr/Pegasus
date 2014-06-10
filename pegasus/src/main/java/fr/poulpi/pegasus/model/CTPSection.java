package fr.poulpi.pegasus.model;

import java.util.Date;
import java.util.List;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPSection {

    //Type of the section, it can be : PUBLIC_TRANSPORT, STREET_NETWORK, WAITING, TRANSFER
    String type;
    //Mode of the street network : Walking, Bike, Car
    String mode;
    //Duration of this section
    int duration;
   //Origin place of this section
    CTPPlace from;
    //Destination place of this section
    CTPPlace to;
    //Links related to this section
    //List<CTPLink> links;
    //Usefull information to display
    CTPDisplay_informations display_informations;
    //Other informations : TODO
    String additionnal_informations;
    //GeoJson <http://www.geojson.org>
    CTPGeoJson geojson;
    //The path of this section
    List<CTPPath> path;
    //The type of this transfer it can be : WALKING, GUARANTEED, EXTENSION
    String transfer_type;
    //List of the stop times of this section
    List<CTPStopDateTime> stop_date_times;
    //Date and time of departure
    Date departure_date_time;
    //Date and time of arrival
    Date arrival_date_time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CTPPlace getFrom() {
        return from;
    }

    public void setFrom(CTPPlace from) {
        this.from = from;
    }

    public CTPPlace getTo() {
        return to;
    }

    public void setTo(CTPPlace to) {
        this.to = to;
    }

    public CTPDisplay_informations getDisplay_informations() {
        return display_informations;
    }

    public void setDisplay_informations(CTPDisplay_informations display_informations) {
        this.display_informations = display_informations;
    }

    public String getAdditionnal_informations() {
        return additionnal_informations;
    }

    public void setAdditionnal_informations(String additionnal_informations) {
        this.additionnal_informations = additionnal_informations;
    }

    public List<CTPPath> getPath() {
        return path;
    }

    public void setPath(List<CTPPath> path) {
        this.path = path;
    }

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public List<CTPStopDateTime> getStop_date_times() {
        return stop_date_times;
    }

    public void setStop_date_times(List<CTPStopDateTime> stop_date_times) {
        this.stop_date_times = stop_date_times;
    }

    public Date getDeparture_date_time() {
        return departure_date_time;
    }

    public void setDeparture_date_time(Date departure_date_time) {
        this.departure_date_time = departure_date_time;
    }

    public Date getArrival_date_time() {
        return arrival_date_time;
    }

    public void setArrival_date_time(Date arrival_date_time) {
        this.arrival_date_time = arrival_date_time;
    }

    public CTPGeoJson getGeojson() {
        return geojson;
    }

    public void setGeojson(CTPGeoJson geojson) {
        this.geojson = geojson;
    }
}
