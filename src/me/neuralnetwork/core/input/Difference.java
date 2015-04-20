package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;
import me.neuralnetwork.core.Neuron;
import me.neuralnetwork.core.Weight;

/**
 * Performs the vector difference operation on input and
 * weight vector.
 */
public class Difference extends InputFunction {
    @Override
    public double getOutput(Iterable<Connection> inputConnections) {
        double output = 0d;

        double sum = 0d;
        for (Connection connection : inputConnections) {
            Neuron neuron = connection.getFromNeuron();
            Weight weight = connection.getWeight();
            double diff = neuron.getOutput() - weight.getValue();
            sum += diff * diff;
        }

        output = Math.sqrt(sum);

        return output;
    }

    public double[] getOutput(double[] inputs, double[] weights) {
        double[] output = new double[inputs.length];

        for (int i = 0; i < inputs.length; i++) {
            output[i] = inputs[i] - weights[i];
        }

        return output;
    }

}
