package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * Performs max function on input vector
 *
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class Max extends InputFunction {

    @Override
    public double getOutput(Iterable<Connection> inputConnections) {
        double max = Double.MIN_VALUE;

        for (Connection connection : inputConnections) {
            max = Math.max(max, connection.getWeightedInput());
        }

        return max;
    }
}
