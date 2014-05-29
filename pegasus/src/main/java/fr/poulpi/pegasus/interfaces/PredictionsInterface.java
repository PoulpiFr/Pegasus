package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;

/**
 * Created by paul-henri on 3/25/14.
 */
public interface PredictionsInterface {

    public void googleAPIRequestPredictions(String str);

    public void googleAPISelectFromPrediction(GoogleAPIResultPrediction result);

    public void googleAPISelectToPrediction(GoogleAPIResultPrediction result);

    public int getFromToState();


}
