package me.neuralnetwork.core.event;

import me.neuralnetwork.core.Layer;
import me.neuralnetwork.core.NeuralNetwork;

/**
 * This class holds information about the source and type of some neural network event.
 */
public class NeuralNetworkEvent extends java.util.EventObject {
    
    NeuralNetworkEventType eventType;
    
    public NeuralNetworkEvent(NeuralNetwork source, NeuralNetworkEventType eventType) {
        super(source);
        this.eventType = eventType;
    }
    
    public NeuralNetworkEvent(Layer source, NeuralNetworkEventType eventType) {
        super(source);
        this.eventType = eventType;
    }    

    public NeuralNetworkEventType getEventType() {
        return eventType;
    }
    
    
        
}