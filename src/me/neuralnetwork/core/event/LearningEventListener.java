package me.neuralnetwork.core.event;

/**
 * This interface is implemented by classes who are listening to learning events (iterations, error etc.)
 * LearningEvent class holds the information about event.
 */
public interface LearningEventListener extends  java.util.EventListener {
    
    /**
     * This method gets executed when LearningRule fires LearningEvent which some class is listening to.
     * For example, if you want to print current iteration, error etc.
     * @param event holds the information about event tha occured
     */
    public void handleLearningEvent(LearningEvent event);
}
