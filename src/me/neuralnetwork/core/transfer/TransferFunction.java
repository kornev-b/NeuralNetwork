package me.neuralnetwork.core.transfer;

/**
 * Abstract base class for all neuron tranfer functions.
 */
abstract public class TransferFunction {

    /**
     * Output result
     */
    protected double output; // cached output value to avoid double calculation for derivative

    /**
     * Returns the ouput of this function.
     *
     * @param net net input
     */
    abstract public double getOutput(double net);

    /**
     * Returns the first derivative of this function.
     * Note: this method should be abstract too
     *
     * @param net net input
     */
    public double getDerivative(double net) {
        return 1d;
    }

}
