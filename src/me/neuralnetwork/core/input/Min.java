package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Performs min function on input vector
 *
 */
public class Min extends InputFunction {

    @Override
    public double getOutput(Iterable<Connection> inputConnections) {
        double min = Double.MAX_VALUE;

        for (Connection connection : inputConnections) {
            min = Math.min(min, connection.getWeightedInput());
        }
        
        return min;
    }
}
