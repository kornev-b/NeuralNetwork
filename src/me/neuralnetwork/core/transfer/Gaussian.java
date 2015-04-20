package me.neuralnetwork.core.transfer;

import me.neuralnetwork.core.util.Properties;

/**
 * <pre>
 * Gaussian neuron transfer function.
 *             -(x^2) / (2 * sigma^2)
 *  f(x) =    e
 * </pre>
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class Gaussian extends TransferFunction {
	
	/**
	 * The sigma parametetar of the gaussian function
	 */	
	private double sigma = 0.5d;

	/**
	 * Creates an instance of Gaussian neuron transfer
	 */	
	public Gaussian() {
	}

	/**
	 * Creates an instance of Gaussian neuron transfer function with the
	 * specified properties.
	 * @param properties properties of the Gaussian function
	 */	
	public Gaussian(Properties properties) {
		try {
			this.sigma = (Double)properties.getProperty("transferFunction.sigma");
		} catch (NullPointerException e) {
			// if properties are not set just leave default values
		} catch (NumberFormatException e) {
			System.err.println("Invalid transfer function properties! Using default values.");
		}
	}

        @Override
	public double getOutput(double net) {
            output = Math.exp(-Math.pow(net, 2) / (2*Math.pow(sigma, 2)));
              //  output = Math.exp(-0.5d * Math.pow(net, 2));
            return output;
	}
	
	@Override
	public double getDerivative(double net) {
		// TODO: check if this is correct
        return output * ( -net / (sigma*sigma) );
	}	

	/**
	 * Returns the sigma parametar of this function
	 * @return  sigma parametar of this function 
	 */	
	public double getSigma() {
		return this.sigma;
	}

	/**
	 * Sets the sigma parametar for this function
	 * @param sigma value for the slope parametar
	 */	
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

}
