package fr.poulpi.pegasus.model;

public class OTPLegs {

	long startTime;
	long endTime;

	int departureDelay;
	int arrivalDelay;

	boolean realTime;
	//isNonExactFrequency
	//headway

	double distance;
	String mode;
	String route;
	String agencyName;
	String agencyUrl;
	String agencyTimeZoneOffset;

	String routeColor;
	String routeType;
	String routeId;

	String routeTextColor;
	boolean interlineWithPreviousLeg;

	String tripShortName;
	int tripBlockId;
	//headsign
	String agencyId;
	String tripId;
	long serviceDate;

	OTPPoint from;
	OTPPoint to;

	OTPLegGeometry OTPLegGeometry;

	//notes
	//alerts
    public String routeShortName;
	public String routeLongName;
	//boardRule
	//alightRule
	boolean rentedBike;
	double duration;
	String transitLeg;
	String intermediateStops;
	OTPStep[] OTPSteps;


	// route
	// agen

	int[] pathTimes;
	int renderingTime;
	int totalTime;
	boolean timedOut;
}
