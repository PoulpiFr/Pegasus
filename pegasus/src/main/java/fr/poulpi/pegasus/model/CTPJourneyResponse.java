package fr.poulpi.pegasus.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pokito on 19/05/2014.
 */
public class CTPJourneyResponse implements Serializable {

    List<CTPJourney> journeys;

    public List<CTPJourney> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<CTPJourney> journeys) {
        this.journeys = journeys;
    }

}
