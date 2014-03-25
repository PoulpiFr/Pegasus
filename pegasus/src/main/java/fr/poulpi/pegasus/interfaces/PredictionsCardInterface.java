package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.ApiPredictionsResponse;

/**
 * Created by paul-henri on 3/25/14.
 */
public interface PredictionsCardInterface {

    void refreshCard(ApiPredictionsResponse response);

}
