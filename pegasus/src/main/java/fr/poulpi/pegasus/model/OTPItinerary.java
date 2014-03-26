package fr.poulpi.pegasus.model;

import java.util.List;

public class OTPItinerary {

	public double duration;
    public long startTime;
    public long endTime;
    public int walkTime;
    public int transitTime;
    public int waitingTime;

    public double walkDistance;
    public boolean walkLimitExceeded;

    public double elevationLost;
    public double elevationGained;

    public int transfers;
	//fare

    public List<OTPLegs> legs;

}
