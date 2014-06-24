package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.CTPJourneyResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by pokito on 19/05/2014.
 */
public interface NavitiaIoInterface {

    @GET("/journeys")
    void journey(
            @Header("Authorization") String myKey,
            @Query("from") String from,
            @Query("to") String to,
            @Query("datetime") String datetime,
            @Query("datetime_represents") String datetime_represents,
            @Query("min_nb_journeys") String min_nb_journeys,
            Callback<CTPJourneyResponse> cb
    );

}
