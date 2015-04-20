package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Performs logic AND operation on input vector.
 */
public class And extends InputFunction {
    public double getOutput(Iterable<Connection> inputConnections) {
        if (!inputConnections.iterator().hasNext()) {
            return 0d;
        }
        boolean output = true;

        for (Connection connection : inputConnections) {
            output = output && (connection.getInput() >= 0.5d);
        }

        return output ? 1d : 0d;
    }

}
