package fr.poulpi.pegasus.model;

import java.util.ArrayList;

/**
 * Created by paul-henri on 3/18/14.
 */
public class GoogleAPIResultPrediction {

    String description;
    String id;
    GoogleAPIResultMatchedSubstrings[] matched_substrings;
    String reference;
    ArrayList<GoogleAPIResultiTerms> terms;
    ArrayList<String> types;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GoogleAPIResultMatchedSubstrings[] getMatched_substrings() {
        return matched_substrings;
    }

    public void setMatched_substrings(GoogleAPIResultMatchedSubstrings[] matched_substrings) {
        this.matched_substrings = matched_substrings;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public ArrayList<GoogleAPIResultiTerms> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<GoogleAPIResultiTerms> terms) {
        this.terms = terms;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getDescription(){
        return description;
    }
}
