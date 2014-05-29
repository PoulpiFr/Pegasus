package fr.poulpi.pegasus.model;

/**
 * Created by paul-henri on 3/18/14.
 */
public class GoogleAPIResultPrediction {

    String description;
    String id;
    GoogleAPIResultMatchedSubstrings[] matched_substrings;
    public String reference;
    GoogleAPIResultiTerms[] terms;
    String[] types;

    public String getDescription(){

        return description;

    }
}
