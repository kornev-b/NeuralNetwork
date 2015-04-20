package me.neuralnetwork.core.transfer;

import me.neuralnetwork.core.util.Properties;

/**
 * <pre>
 * Sigmoid neuron transfer function.
 *
 * output = 1/(1+ e^(-slope*input))
 * </pre>
 */
public class Sigmoid extends TransferFunction {

    /**
     * The slope parameter of the sigmoid function
     */
    private double slope = 1d;

    /**
     * Creates an instance of Sigmoid neuron transfer function with default
     * slope=1.
     */
    public Sigmoid() {
    }

    /**
     * Creates an instance of Sigmoid neuron transfer function with specified
     * value for slope parameter.
     *
     * @param slope the slope parameter for the sigmoid function
     */
    public Sigmoid(double slope) {
        this.slope = slope;
    }

    /**
     * Creates an instance of Sigmoid neuron transfer function with the
     * specified properties.
     *
     * @param properties properties of the sigmoid function
     */
    public Sigmoid(Properties properties) {
        try {
            this.slope = (Double) properties.getProperty("transferFunction.slope");
        } catch (NullPointerException e) {
            // if properties are not set just leave default values
        } catch (NumberFormatException e) {
            System.err.println("Invalid transfer function properties! Using default values.");
        }
    }

    /**
     * Returns the slope parameter of this function
     *
     * @return slope parameter of this function
     */
    public double getSlope() {
        return this.slope;
    }

    /**
     * Sets the slope parameter for this function
     *
     * @param slope value for the slope parameter
     */
    public void setSlope(double slope) {
        this.slope = slope;
    }

    @Override
    public double getOutput(double net) {
        // conditional logic helps to avoid NaN
        if (net > 100) {
            return 1.0;
        } else if (net < -100) {
            return 0.0;
        }

        double den = 1d + Math.exp(-this.slope * net);
        this.output = (1d / den);

        return this.output;
    }

    @Override
    public double getDerivative(double net) {
        // remove net parameter? maybe we don't need it since we use cached output value
        // +0.1 is fix for flat spot see http://www.heatonresearch.com/wiki/Flat_Spot
        return this.slope * this.output * (1d - this.output) + 0.1;
    }
}
