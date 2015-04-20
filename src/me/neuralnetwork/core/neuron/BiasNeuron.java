package me.neuralnetwork.core.neuron;

import me.neuralnetwork.core.Connection;
import me.neuralnetwork.core.Neuron;

/**
 * Neuron with constant high output (1), used as bias
 *
 * @see Neuron
 */
public class BiasNeuron extends Neuron {

    /**
     * Creates an instance of BiasedNeuron.
     */
    public BiasNeuron() {
        super();
    }

    @Override
    public double getOutput() {
        return 1;
    }

    @Override
    public void addInputConnection(Connection connection) {

    }

    @Override
    public void addInputConnection(Neuron fromNeuron, double weightVal) {

    }

    @Override
    public void addInputConnection(Neuron fromNeuron) {

    }
}
