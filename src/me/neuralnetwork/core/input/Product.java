package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Performs multiplication of all input vector elements.
 *
 */
public class Product extends InputFunction {

    @Override
    public double getOutput(Iterable<Connection> inputConnections) {
        if (!inputConnections.iterator().hasNext()) {
            return 0d;
        }

        double output = 1d;

        for (Connection connection : inputConnections) {
            output *= connection.getInput();
        }

        return output;
    }
}
