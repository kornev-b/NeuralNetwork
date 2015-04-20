package me.neuralnetwork.core.learning.error;

import java.io.Serializable;

public class MeanSquaredError implements ErrorFunction, Serializable {
    private transient double totalSquaredErrorSum;
    private transient double n;

    public MeanSquaredError(double n) {
        this.n = n;
    }
    
    @Override
    public void reset() {
        totalSquaredErrorSum = 0;
    }
        
    @Override
    public double getTotalError() {
        return totalSquaredErrorSum/n;
    }

    @Override
    public void addOutputError(double[] outputError) {
        double outputErrorSqrSum = 0;
        for (double error : outputError) {
            outputErrorSqrSum += (error * error) * 0.5;
        }

        this.totalSquaredErrorSum += outputErrorSqrSum;
    }
    
}
