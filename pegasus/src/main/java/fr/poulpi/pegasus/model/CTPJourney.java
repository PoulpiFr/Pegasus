package fr.poulpi.pegasus.model;

import java.util.Date;
import java.util.List;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPJourney {

    //Duration of the journey
    int duration;
    //Number of transfers in the journey
    int nb_transfers;
    //Departure date and time of the journey
    Date departure_date_time;
    //Requested date and time of the journey
    Date requested_date_time;
    //Arrival date and time of the journey
    Date arrival_date_time;
    //All the sections of the journey
    List<CTPSection> sections;
    //The place from where the journey starts
    CTPPlace from;
    //The place from where the journey starts
    CTPPlace to;
    //Links related to this journey
    //CTPLink link;
    //Use to qualified a journey can be comfort, rapid, healthy
    String type;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNb_transfers() {
        return nb_transfers;
    }

    public void setNb_transfers(int nb_transfers) {
        this.nb_transfers = nb_transfers;
    }

    public Date getDeparture_date_time() {
        return departure_date_time;
    }

    public void setDeparture_date_time(Date departure_date_time) {
        this.departure_date_time = departure_date_time;
    }

    public Date getRequested_date_time() {
        return requested_date_time;
    }

    public void setRequested_date_time(Date requested_date_time) {
        this.requested_date_time = requested_date_time;
    }

    public Date getArrival_date_time() {
        return arrival_date_time;
    }

    public void setArrival_date_time(Date arrival_date_time) {
        this.arrival_date_time = arrival_date_time;
    }

    public List<CTPSection> getSections() {
        return sections;
    }

    public void setSections(List<CTPSection> sections) {
        this.sections = sections;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
