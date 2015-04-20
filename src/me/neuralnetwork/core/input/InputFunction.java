package me.neuralnetwork.core.input;

import me.neuralnetwork.core.Connection;

/**
 * <pre>
 * Neuron's input function. It has two subcomponents:
 *
 * weightsFunction - which performs operation with input and weight vector
 * summingFunction - which performs operation with the resulting vector from weightsFunction
 *
 * InputFunction implements the following behaviour:
 * output = summingFunction(weightsFunction(inputs))
 *
 * Different neuron input functions can be created by setting different weights and summing functions.
 * </pre>
 */
abstract public class InputFunction {

    /**
     * Returns output value of this input function for the given neuron inputs
     *
     * @param inputConnections neuron's input connections
     * @return input total net input
     */
    abstract public double getOutput(Iterable<Connection> inputConnections);

}
