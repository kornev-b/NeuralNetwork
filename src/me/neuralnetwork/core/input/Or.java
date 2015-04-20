
package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Performs logic OR operation on input vector.
 */
public class Or extends InputFunction {
    public double getOutput(Iterable<Connection> inputConnections) {
        boolean output = false;

        for (Connection connection : inputConnections) {
            output = output || (connection.getInput() >= 0.5d);
        }

        return output ? 1d : 0d;
    }
}
