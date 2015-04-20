package me.neuralnetwork.core.learning;

import me.neuralnetwork.core.Connection;
import me.neuralnetwork.core.Layer;
import me.neuralnetwork.core.Neuron;
import me.neuralnetwork.core.Weight;
import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.core.data.DataSetRow;
import me.neuralnetwork.core.learning.error.ErrorFunction;
import me.neuralnetwork.core.learning.error.MeanSquaredError;
import me.neuralnetwork.core.learning.stop.MaxErrorStop;

import java.util.Iterator;

/**
 * Base class for all supervised learning algorithms.
 * It extends IterativeLearning, and provides general supervised learning principles.

 */
abstract public class SupervisedLearning extends IterativeLearning {

    protected double totalNetworkError;
    protected double totalSquaredErrorSum;
    protected double previousEpochError;
    protected double maxError = 0.01d;
    /**
     * Stopping condition: training stops if total network error change is smaller than minErrorChange
     * for minErrorChangeIterationsLimit number of iterations
     */
    private double minErrorChange = Double.POSITIVE_INFINITY;
    /**
     * Stopping condition: training stops if total network error change is smaller than minErrorChange
     * for minErrorChangeStopIterations number of iterations
     */
    private int minErrorChangeIterationsLimit = Integer.MAX_VALUE;
    /**
     * Count iterations where error change is smaller then minErrorChange
     */
    private  int minErrorChangeIterationsCount;
    /**
     * Setting to determine if learning (weights update) is in batch mode
     * False by default.
     */
    private boolean batchMode = false;
    
    private ErrorFunction errorFunction;
    private int trainingSetSize;

    public SupervisedLearning() {
        super();
    }

    /**
     * Trains network for the specified training set and number of iterations
     * @param trainingSet training set to learn
     * @param maxError maximum number of iterations to learn
     *
     */
    public void learn(DataSet trainingSet, double maxError) {
        this.maxError = maxError;
        this.learn(trainingSet);
    }

    /**
     * Trains network for the specified training set and number of iterations
     * @param trainingSet training set to learn
     * @param maxIterations maximum number of learning iterations
     *
     */
    public void learn(DataSet trainingSet, double maxError, int maxIterations) {
        this.maxError = maxError;
        this.setMaxIterations(maxIterations);
        this.learn(trainingSet);
    }

    @Override
    protected void onStart() {
        super.onStart(); // reset iteration counter
        this.minErrorChangeIterationsCount = 0;
        this.totalNetworkError = 0d;
        this.previousEpochError = 0d;

        this.trainingSetSize = getTrainingSet().size();        
        this.errorFunction = new MeanSquaredError(trainingSetSize);
        
        // create stop condition structure based on settings               
        this.stopConditions.add(new MaxErrorStop(this));
    }
    
    @Override
    protected void beforeEpoch() {
        this.previousEpochError = this.totalNetworkError;
        this.totalNetworkError = 0d;
        this.totalSquaredErrorSum = 0d;  
        this.errorFunction.reset();
    }
    
    @Override
    protected void afterEpoch() {
        // calculate abs error change and count iterations if its below specified min error change (used for stop condition)
        double absErrorChange = Math.abs(previousEpochError - totalNetworkError);
        if (absErrorChange <= this.minErrorChange) {
            this.minErrorChangeIterationsCount++;
        } else {
            this.minErrorChangeIterationsCount = 0;
        }
        
        // if learning is performed in batch mode, apply accumulated weight changes from this epoch        
        if (batchMode) {
            doBatchWeightsUpdate();
        }        
    }

    /**
     * This method implements basic logic for one learning epoch for the
     * supervised learning algorithms. Epoch is the one pass through the
     * training set. This method  iterates through the training set
     * and trains network for each element. It also sets flag if conditions 
     * to stop learning has been reached: network error below some allowed
     * value, or maximum iteration count 
     * 
     * @param trainingSet
     *            training set for training network
     */
    @Override
    public void doLearningEpoch(DataSet trainingSet) {
        // feed network with all elements from training set
        Iterator<DataSetRow> iterator = trainingSet.iterator();
        while (iterator.hasNext() && !isStopped()) {
            DataSetRow dataSetRow = iterator.next();
            // learn current input/output pattern defined by SupervisedTrainingElement
            this.learnPattern(dataSetRow); 
        }
        this.totalNetworkError = errorFunction.getTotalError();
    }

    /**
     * Trains network with the input and desired output pattern from the specified training element
     * 
     * @param trainingElement
     *            supervised training element which contains input and desired
     *            output
     */
    protected void learnPattern(DataSetRow trainingElement) {
        double[] input = trainingElement.getInput();
        this.neuralNetwork.setInput(input);
        this.neuralNetwork.calculate();
        double[] output = this.neuralNetwork.getOutput();
        double[] desiredOutput = trainingElement.getDesiredOutput();
        double[] outputError = this.calculateOutputError(desiredOutput, output);
        //this.addToSquaredErrorSum(outputError);
        errorFunction.addOutputError(outputError);
        this.updateNetworkWeights(outputError);
    }
    
    /**
     * This method updates network weights in batch mode - use accumulated weights change stored in Weight.deltaWeight
     * It is executed after each learning epoch, only if learning is done in batch mode.
     */
    protected void doBatchWeightsUpdate() {
        // iterate layers from output to input
        Layer[] layers = neuralNetwork.getLayers();
        for (int i = neuralNetwork.getLayersCount() - 1; i > 0; i--) {
            // iterate neurons at each layer
            for (Neuron neuron : layers[i].getNeurons()) {
                // iterate connections/weights for each neuron
                for (Connection connection : neuron.getInputConnections()) {
                    // for each connection weight apply accumulated weight change
                    Weight weight = connection.getWeight();
                    weight.setValue(weight.getWeightChange());// apply delta weight which is the sum of delta weights in batch mode
                    weight.setWeightChange(0); // reset deltaWeight
                }
            }
        }
    }

    /**
     * Calculates the network error for the current input pattern - diference between
     * desired and actual output
     * 
     * @param output
     *            actual network output
     * @param desiredOutput
     *            desired network output
     */
    protected double[] calculateOutputError(double[] desiredOutput, double[] output) {
        double[] outputError = new double[desiredOutput.length];
        
        for (int i = 0; i < output.length; i++) {
            outputError[i] = desiredOutput[i] - output[i];
        }
        
        return outputError;
    }
    
    /**
     * Returns true if learning is performed in batch mode, false otherwise
     * @return true if learning is performed in batch mode, false otherwise
     */
    public boolean isInBatchMode() {
        return batchMode;
    }

    /**
     * Sets batch mode on/off (true/false)
     * @param batchMode batch mode setting
     */
    public void setBatchMode(boolean batchMode) {
        this.batchMode = batchMode;
    }    

    /**
     * Sets allowed network error, which indicates when to stopLearning training
     * 
     * @param maxError
     *            network error
     */
    public void setMaxError(double maxError) {
        this.maxError = maxError;        
    }

    /**
     * Returns learning error tolerance - the value of total network error to stop learning.
     *
     * @return learning error tolerance
     */
    public double getMaxError() {
        return maxError;
    }

    /**
     * Returns total network error in current learning epoch
     * 
     * @return total network error in current learning epoch
     */
    public synchronized double getTotalNetworkError() {
        return totalNetworkError;
    }

    /**
     * Returns total network error in previous learning epoch
     *
     * @return total network error in previous learning epoch
     */
    public double getPreviousEpochError() {
        return previousEpochError;
    }

    /**
     * Returns min error change stopping criteria
     *
     * @return min error change stopping criteria
     */
    public double getMinErrorChange() {
        return minErrorChange;
    }

    /**
     * Sets min error change stopping criteria
     *
     * @param minErrorChange value for min error change stopping criteria
     */
    public void setMinErrorChange(double minErrorChange) {
        this.minErrorChange = minErrorChange;
    }

    /**
     * Returns number of iterations for min error change stopping criteria
     *
     * @return number of iterations for min error change stopping criteria
     */
    public int getMinErrorChangeIterationsLimit() {
        return minErrorChangeIterationsLimit;
    }

    /**
     * Sets number of iterations for min error change stopping criteria
     * @param minErrorChangeIterationsLimit number of iterations for min error change stopping criteria
     */
    public void setMinErrorChangeIterationsLimit(int minErrorChangeIterationsLimit) {
        this.minErrorChangeIterationsLimit = minErrorChangeIterationsLimit;
    }

    /**
     * Returns number of iterations count for for min error change stopping criteria
     *
     * @return number of iterations count for for min error change stopping criteria
     */
    public int getMinErrorChangeIterationsCount() {
        return minErrorChangeIterationsCount;
    }

    public ErrorFunction getErrorFunction() {
        return errorFunction;
    }

    public void setErrorFunction(ErrorFunction errorFunction) {
        this.errorFunction = errorFunction;
    }
    
    

    /**
     * Calculates and updates sum of squared errors for single pattern, and updates total sum of squared pattern errors
     *
     * @param outputError output error vector
     */
    // see: http://www.vni.com/products/imsl/documentation/CNL06/stat/NetHelp/default.htm?turl=multilayerfeedforwardneuralnetworks.htm
    protected void addToSquaredErrorSum(double[] outputError) {
        double outputErrorSqrSum = 0;
        for (double error : outputError) {
            outputErrorSqrSum += (error * error) * 0.5; // a;so multiply with 1/trainingSetSize  1/2n * (...)
        }

        this.totalSquaredErrorSum += outputErrorSqrSum;
    }

    /**
     * This method should implement the weights update procedure for the whole network
     * for the given output error vector.
     * 
     * @param outputError
     *            output error vector for some network input (aka. patternError, network error) 
     *            usually the difference between desired and actual output
     *
     * @see me.neuralnetwork.core.learning.SupervisedLearning#calculateOutputError(double[], double[])  calculateOutputError
     * @see me.neuralnetwork.core.learning.SupervisedLearning#addToSquaredErrorSum(double[])
     */
    abstract protected void updateNetworkWeights(double[] outputError);
}
