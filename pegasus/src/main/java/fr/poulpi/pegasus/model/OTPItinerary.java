package fr.poulpi.pegasus.model;

import java.util.List;

public class OTPItinerary {

	double duration;
	long startTime;
	long endTime;
	int walkTime;
	int transitTime;
	int waitingTime;

	double walkDistance;
	boolean walkLimitExceeded;

	double elevationLost;
	double elevationGained;

	int transfers;
	//fare

	List<OTPLegs> legs;

}
