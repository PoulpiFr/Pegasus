package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.OTPResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by paul-henri on 3/25/14.
 */
public interface OTPOpenTripPlanner {
    @Headers("Accept: application/json")
    @GET("/plan")
    void response(
            @Query("fromPlace") String fromPlace,
            @Query("toPlace") String toPlace,
            //@Query("intermediatePlaces") String intermediatePlaces,
            //@Query("intermediatePlacesOrdered") String intermediatePlacesOrdered,
            @Query("date") String date,
            Callback<OTPResponse> cb
    );

    @Headers("Accept: application/json")
    @GET("/plan")
    void complexResponse(
            @Query("fromPlace") String fromPlace,
            @Query("toPlace") String toPlace,
            @Query("intermediatePlaces") String intermediatePlaces,
            @Query("intermediatePlacesOrdered") String intermediatePlacesOrdered,
            @Query("date") String date,
            @Query("time") String time,
            @Query("routerId") int routerId,
            @Query("arriveBy") boolean arriveBy,
            @Query("maxWalkDistance") int maxWalkDistance,
            @Query("walkSpeed") int walkSpeed,
            @Query("optimize") String optimize,
            @Query("mode") String mode,
            @Query("minTransferTime") int minTransferTime,
            @Query("numItineraries") int numItineraries,
            @Query("preferredRoutes") String preferredRoutes,
            @Query("preferredAgencies") String preferredAgencies,
            @Query("unpreferredRoutes") String unpreferredRoutes,
            @Query("unpreferredAgencies") String unpreferredAgencies,
            @Query("bannedRoutes") String bannedRoutes,
            @Query("bannedAgencies") String bannedAgencies,
            @Query("bannedTrips") String bannedTrips,
            @Query("transferPenalty") int transferPenalty,
            @Query("maxTransfers") int maxTransfers,
            Callback<OTPResponse> cb
    );
}
