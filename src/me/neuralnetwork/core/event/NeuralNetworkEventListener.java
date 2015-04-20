package me.neuralnetwork.core.event;

/**
 * This interface is implemented by classes who are listening to neural network events events (to be defined)
 * NeuralNetworkEvent class holds the information about event.
 */
public interface NeuralNetworkEventListener extends java.util.EventListener {
    public void handleNeuralNetworkEvent(NeuralNetworkEvent event);
}
