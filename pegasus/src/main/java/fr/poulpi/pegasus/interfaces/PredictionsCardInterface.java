package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.GoogleAPIPredictionsResponse;

/**
 * Created by paul-henri on 3/25/14.
 */
public interface PredictionsCardInterface {

    void refreshCard(GoogleAPIPredictionsResponse response);

}
