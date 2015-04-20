package me.neuralnetwork.core.transfer;

import me.neuralnetwork.core.util.Properties;

/**
 * <pre>
 * Tanh neuron transfer function.
 *
 * output = ( e^(2*input)-1) / ( e^(2*input)+1 )
 * </pre>
 */
public class Tanh extends TransferFunction {

    /**
     * The slope parametetar of the Tanh function
     */
    private double slope = 2d;

    /**
     * Creates an instance of Tanh neuron transfer function with default
     * slope=1.
     */
    public Tanh() {
    }

    /**
     * Creates an instance of Tanh neuron transfer function with specified
     * value for slope parametar.
     *
     * @param slope the slope parametar for the Tanh function
     */
    public Tanh(double slope) {
        this.slope = slope;
    }

    /**
     * Creates an instance of Tanh neuron transfer function with the
     * specified properties.
     *
     * @param properties properties of the Tanh function
     */
    public Tanh(Properties properties) {
        try {
            this.slope = (Double) properties.getProperty("transferFunction.slope");
        } catch (NullPointerException e) {
            // if properties are not set just leave default values
        } catch (NumberFormatException e) {
            System.err.println("Invalid transfer function properties! Using default values.");
        }
    }

    @Override
    final public double getOutput(double net) {
        // conditional logic helps to avoid NaN
        if (net > 100) {
            return 1.0;
        } else if (net < -100) {
            return -1.0;
        }

        double E_x = Math.exp(this.slope * net);
        this.output = (E_x - 1d) / (E_x + 1d);

        return this.output;
    }

    @Override
    final public double getDerivative(double net) {
        return (1d - output * output);
    }

    /**
     * Returns the slope parametar of this function
     *
     * @return slope parametar of this function
     */
    public double getSlope() {
        return this.slope;
    }

    /**
     * Sets the slope parametar for this function
     *
     * @param slope value for the slope parametar
     */
    public void setSlope(double slope) {
        this.slope = slope;
    }
}
