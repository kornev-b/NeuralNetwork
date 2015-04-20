package me.neuralnetwork.core.neuron;

import me.neuralnetwork.core.Neuron;
import me.neuralnetwork.core.input.WeightedSum;
import me.neuralnetwork.core.transfer.Linear;

/**
 * Provides input neuron behaviour - neuron with input externally set, which just
 * transfer that input to output without change. Its purpose is to distribute its input
 * to all neurons it is connected to. It has no input connections
 */
public class InputNeuron extends Neuron {

    /**
     * Creates a new instance of InputNeuron with linear transfer function
     */
    public InputNeuron() {
        super(new WeightedSum(), new Linear());
    }

    /**
     * Calculate method of this type of neuron just transfers its externally set
     * input (with setNetInput) to its output
     */
    @Override
    public void calculate() {
        this.output = this.netInput;
    }
}
