package fr.poulpi.pegasus.model;

public class OTPRequestParameters {
	String fromPlace;
	String toPlace;
	String intermediatePlaces;
	String intermediatePlacesOrdered;
	String date;
	String time;
	int routerId;
	boolean arriveBy;
	int maxWalkDistance;
	int walkSpeed;
	String optimize;
	String mode;
	int minTransferTime;
	int numItineraries;
	String preferredRoutes;
	String preferredAgencies;
	String unpreferredRoutes;
	String unpreferredAgencies;
	String bannedRoutes;
	String bannedAgencies;
	String bannedTrips;
	int transferPenalty;
	int maxTransfers;
}
