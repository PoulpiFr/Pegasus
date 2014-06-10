package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;

/**
 * Created by paul-henri on 3/25/14.
 */
public interface ItinarySearchCardInterface {

    public void refreshCard(GoogleAPIResultPrediction from, GoogleAPIResultPrediction to);

    public int getFromToState();

}
