package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Performs summing of all input vector elements.
 *
 */
public class Sum extends InputFunction {

    @Override
    public double getOutput(Iterable<Connection> inputConnections) {
        double output = 0d;

        for (Connection connection : inputConnections) {
            output += connection.getInput();
        }

        return output;
    }
}