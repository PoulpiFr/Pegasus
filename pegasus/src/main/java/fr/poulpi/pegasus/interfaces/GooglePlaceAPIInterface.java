package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.GoogleAPIPredictionsResponse;
import fr.poulpi.pegasus.model.GoogleAPIDetailsPlace;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by paul-henri on 3/21/14.
 */

public interface GooglePlaceAPIInterface {

    @GET("/autocomplete/json")
    void response(
            @Query("sensor") String sensor,
            @Query("key") String key,
            // "lat,lon"
            @Query("location") String location,
            // "1000" = 1km
            @Query("radius") String radius,
            @Query("language") String language,
            @Query("components") String components,
            @Query("types") String types,
            @Query("input") String input,
            Callback<GoogleAPIPredictionsResponse> cb
    );

    @GET("/details/json")
    void details(
            @Query("sensor") String sensor,
            @Query("key") String key,
            @Query("reference") String reference,
            @Query("language") String language,
            Callback<GoogleAPIDetailsPlace> cb
    );
}

