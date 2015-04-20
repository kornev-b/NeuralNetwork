package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Calculates squared sum of all input vector elements.
 *
 */
public class SumSqr extends InputFunction {

    @Override
    public double getOutput(Iterable<Connection> inputConnections) {
        double output = 0d;

        for (Connection connection : inputConnections) {
            double input = connection.getInput();
            output += input * input;
        }

        return output;
    }
}
