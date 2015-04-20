package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Optimized version of weighted input function
 *
 */
public class WeightedSum extends InputFunction {

    @Override
    public double getOutput(Iterable<Connection> inputConnections) {
        double output = 0d;

        for (Connection connection : inputConnections) {
            output += connection.getWeightedInput();
        }

        return output;
    }

    public static double[] getOutput(double[] inputs, double[] weights) {
        double[] output = new double[inputs.length];

        for (int i = 0; i < inputs.length; i++) {
            output[i] += inputs[i] * weights[i];
        }

        return output;
    }
}
