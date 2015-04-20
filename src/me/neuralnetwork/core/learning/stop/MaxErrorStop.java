package me.neuralnetwork.core.learning.stop;

import me.neuralnetwork.core.learning.SupervisedLearning;

public class MaxErrorStop implements StopCondition {

    private final SupervisedLearning learningRule;
    
    public MaxErrorStop(SupervisedLearning learningRule) {
        this.learningRule = learningRule;
    }
    
    @Override
    public boolean isReached() {
        return learningRule.getTotalNetworkError() < learningRule.getMaxError();

    }
           
}
