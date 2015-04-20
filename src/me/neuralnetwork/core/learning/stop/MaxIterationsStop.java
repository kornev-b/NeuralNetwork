package me.neuralnetwork.core.learning.stop;

import me.neuralnetwork.core.learning.IterativeLearning;

public class MaxIterationsStop implements StopCondition {

    private IterativeLearning learningRule;
    
    public MaxIterationsStop(IterativeLearning learningRule) {
        this.learningRule = learningRule;
    }
        
    @Override
    public boolean isReached() {
        return learningRule.getCurrentIteration() >= learningRule.getMaxIterations();

    }
}