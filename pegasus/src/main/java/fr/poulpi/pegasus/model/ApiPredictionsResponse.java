package fr.poulpi.pegasus.model;

import java.util.List;

/**
 * Created by paul-henri on 3/21/14.
 */
public class ApiPredictionsResponse {

    private List<ResultApiPrediction> predictions;

    public List<ResultApiPrediction> getPredictions(){
        return predictions;
    }

    public void setPredictions(List<ResultApiPrediction> tmp){
        this.predictions = tmp;
    }

}

