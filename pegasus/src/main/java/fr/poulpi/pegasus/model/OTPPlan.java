package fr.poulpi.pegasus.model;

import java.util.List;

public class OTPPlan {

	long date;
	OTPPoint from;
	OTPPoint to;
	List<OTPItinerary> itineraries;
}