package me.neuralnetwork.core.learning;

import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.core.event.LearningEvent;
import me.neuralnetwork.core.event.LearningEventType;
import me.neuralnetwork.core.learning.stop.MaxIterationsStop;
import me.neuralnetwork.core.learning.stop.StopCondition;

import java.util.ArrayList;
import java.util.List;

abstract public class IterativeLearning extends LearningRule {
    protected double learningRate = 0.1d;
    protected int currentIteration = 0;
    private int maxIterations = Integer.MAX_VALUE;
    private boolean iterationsLimited = false;
    protected List<StopCondition> stopConditions;
    /**
     * Flag for indicating if learning thread is paused
     */
    private volatile boolean pausedLearning = false;

    public IterativeLearning() {
        super();
        this.stopConditions = new ArrayList<StopCondition>();
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMaxIterations(int maxIterations) {
        if (maxIterations > 0) {
            this.maxIterations = maxIterations;
            this.iterationsLimited = true;
        }
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public boolean isIterationsLimited() {
        return iterationsLimited;
    }

    public Integer getCurrentIteration() {
        return this.currentIteration;
    }

    public boolean isPausedLearning() {
        return pausedLearning;
    }

    public void pause() {
        this.pausedLearning = true;
    }

    public void resume() {
        this.pausedLearning = false;
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * This method is executed when learning starts, before the first epoch.
     * Used for initialisation.
     */
    @Override
    protected void onStart() {
        super.onStart();
        
        if (this.iterationsLimited) {
            this.stopConditions.add(new MaxIterationsStop(this));
        }

        this.currentIteration = 0;
    }

    protected void beforeEpoch() {
    }

    protected void afterEpoch() {
    }

    @Override
    final public void learn(DataSet trainingSet) {
        setTrainingSet(trainingSet); // set this field here su subclasses can access it 
        onStart();

        while (!isStopped()) {
            beforeEpoch();
            doLearningEpoch(trainingSet);
            this.currentIteration++;
            fireLearningEvent(new LearningEvent(this, LearningEventType.EPOCH_ENDED));
            afterEpoch();

            // now check if stop condition is satisfied
            if (hasReachedStopCondition()) {
                stopLearning();
            } else if (!iterationsLimited && (currentIteration == Integer.MAX_VALUE)) {
                // if counter has reached max value and iteration number is not limited restart iteration counter
                this.currentIteration = 1;
            }


            // Thread safe pause when learning is paused
            if (this.pausedLearning) {
                synchronized (this) {
                    while (this.pausedLearning) {
                        try {
                            this.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        onStop();
    }

    protected boolean hasReachedStopCondition() {
        for (StopCondition stop : stopConditions) {
            if (stop.isReached()) {
                return true;
            }
        }

        return false;
    }

    public void learn(DataSet trainingSet, int maxIterations) {
        this.setMaxIterations(maxIterations);
        this.learn(trainingSet);
    }

    public void doOneLearningIteration(DataSet trainingSet) {
        beforeEpoch();
        doLearningEpoch(trainingSet);
        afterEpoch();
    }

    /**
     * Override this method to implement specific learning epoch - one learning
     * iteration, one pass through whole training set
     *
     * @param trainingSet training set
     */
    abstract public void doLearningEpoch(DataSet trainingSet);
}