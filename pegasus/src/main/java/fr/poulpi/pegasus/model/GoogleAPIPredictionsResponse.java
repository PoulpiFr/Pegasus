package fr.poulpi.pegasus.model;

import java.util.List;

/**
 * Created by paul-henri on 3/21/14.
 */
public class GoogleAPIPredictionsResponse {

    private List<GoogleAPIResultPrediction> predictions;

    public List<GoogleAPIResultPrediction> getPredictions(){
        return predictions;
    }

    public void setPredictions(List<GoogleAPIResultPrediction> tmp){
        this.predictions = tmp;
    }

}

