package fr.poulpi.pegasus.interfaces;

import fr.poulpi.pegasus.model.ResultApiPrediction;

/**
 * Created by paul-henri on 3/25/14.
 */
public interface ItinarySearchCardInterface {

    public void refreshCard(ResultApiPrediction from, ResultApiPrediction to);

    public int getFromToState();

}
