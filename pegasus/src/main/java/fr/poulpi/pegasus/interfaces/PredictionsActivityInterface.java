package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.ResultApiPrediction;

/**
 * Created by paul-henri on 3/25/14.
 */
public interface PredictionsActivityInterface {

    public void googleAPIRequestPredictions(String str);

    public void googleAPISelectFromPrediction(ResultApiPrediction result);

    public int getFromToState();

    public void googleAPISelectToPrediction(ResultApiPrediction result);
}
