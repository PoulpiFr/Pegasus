package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.ApiPredictionsResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by paul-henri on 3/21/14.
 */

public interface ApiPredictionsInterface {
    @Headers("Accept: application/json")
    @GET("/autocomplete/json")

    void response(
            @Query("sensor") String sensor,
            @Query("key") String key,
            @Query("components") String components,
            @Query("input") String input,
            Callback<ApiPredictionsResponse> cb
    );
}

