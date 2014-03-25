package fr.poulpi.pegasus.model;

/**
 * Created by paul-henri on 3/18/14.
 */
public class ResultApiPrediction {

    String description;
    String id;
    ResultApiMatchedSubstrings[] matched_substrings;
    String reference;
    ResultApiTerms[] terms;
    String[] types;

    public String getDescription(){

        return description;

    }
}
