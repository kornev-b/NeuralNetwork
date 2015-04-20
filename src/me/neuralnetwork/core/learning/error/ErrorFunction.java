
package me.neuralnetwork.core.learning.error;

/**
 * Interface for calculating total network error during the learning.
 * Custom error types  can be implemented.
 */
public interface ErrorFunction {
    public double getTotalError();
    
    public void addOutputError(double[] outputError);
    
    public void reset();
}
