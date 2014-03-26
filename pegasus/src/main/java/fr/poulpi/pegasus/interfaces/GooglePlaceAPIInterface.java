package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.ApiPredictionsResponse;
import fr.poulpi.pegasus.model.GoogleAPIDetailsPlace;
import fr.poulpi.pegasus.model.GoogleAPIGeometry;
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
            @Query("components") String components,
            @Query("input") String input,
            Callback<ApiPredictionsResponse> cb
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

