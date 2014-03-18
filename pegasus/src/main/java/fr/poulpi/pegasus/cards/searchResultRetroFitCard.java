package fr.poulpi.pegasus.cards;

/**
 * Created by paul-henri on 3/18/14.
 */

import java.util.List;

import fr.poulpi.pegasus.model.ResultApiPredictions;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;


public class searchResultRetroFitCard {

    private static final String API_URL = "https://maps.googleapis.com/maps/api/place";

    static class Response {
        List<ResultApiPredictions> predictions;
    }

    interface OpenTripPlanner {
        @Headers("Accept: application/json")
        @GET("/autocomplete/json")

        Response response(
            @Query("sensor") String sensor,
            @Query("key") String key,
            @Query("components") String components,
            @Query("input") String input
        );
    }

    public static void main(String... args) {

        // debug purpose only, to get the messages
        RestAdapter.Log log = new RestAdapter.Log(){
            public void log(String msg){
                System.out.println(msg);
            }
        };

        // Create a very simple REST adapter which points the GitHub API endpoint.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLog(log)
                .setLogLevel(LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        // Create an instance of our API interface.
        OpenTripPlanner test = restAdapter.create(OpenTripPlanner.class);

        Response rep = test.response("false","AIzaSyDayrc8izwz8IG8OiA48tUJcFObFW0WLYw","country:fr","pari");



        System.out.println("");
    }
}
