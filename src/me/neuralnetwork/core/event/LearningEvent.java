package me.neuralnetwork.core.event;

import me.neuralnetwork.core.learning.LearningRule;

public class LearningEvent extends java.util.EventObject {
    LearningEventType eventType;
    
    public LearningEvent(LearningRule source, LearningEventType eventType) {
        super(source);
        this.eventType = eventType;
    }

    public LearningEventType getEventType() {
        return eventType;
    }
                
}