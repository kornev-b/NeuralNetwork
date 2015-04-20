package me.neuralnetwork.core.learning;

import me.neuralnetwork.core.NeuralNetwork;
import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.core.event.LearningEvent;
import me.neuralnetwork.core.event.LearningEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 23.03.2015, 2:39.
 */
public abstract class LearningRule {
    protected NeuralNetwork neuralNetwork;
    private transient DataSet trainingSet;
    private transient volatile boolean stopLearning = false;
    /**
     * List of learning rule listeners
     */
    protected transient List<LearningEventListener> listeners = new ArrayList<>();

    public void setTrainingSet(DataSet trainingSet) {
        this.trainingSet = trainingSet;
    }

    public DataSet getTrainingSet() {
        return trainingSet;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    /**
     * Prepares the learning rule to run by setting stop flag to false
     * If you override this method make sure you call parent method first
     */
    protected void onStart() {
        stopLearning = false;
    }

    /**
     * Invoked after the learning has stopped
     */
    protected void onStop() {
    }

    public void stopLearning() {
        stopLearning = true;
    }

    public boolean isStopped() {
        return stopLearning;
    }

    // This methods allows classes to register for LearningEvents
    public synchronized void addListener(LearningEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener is null!");

        listeners.add(listener);
    }

    // This methods allows classes to unregister for LearningEvents
    public synchronized void removeListener(LearningEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener is null!");

        listeners.remove(listener);
    }

    // This private class is used to fire LearningEvents
    protected synchronized void fireLearningEvent(LearningEvent evt) {
        for (LearningEventListener listener : listeners) {
            listener.handleLearningEvent(evt);
        }
    }

    /**
     * Override this method to implement specific learning procedures
     *
     * @param trainingSet training set
     */
    abstract public void learn(DataSet trainingSet);
}
