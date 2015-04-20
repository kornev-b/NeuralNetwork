package me.neuralnetwork.core.transfer;

import me.neuralnetwork.core.util.Properties;

/**
 * Linear neuron transfer function.
 *
 */
public class Linear extends TransferFunction {
	
	/**
	 * The slope parametetar of the linear function
	 */	
	private double slope = 1d;

	/**
	 * Creates an instance of Linear transfer function
	 */	
	public Linear() {
	}

	/**
	 * Creates an instance of Linear transfer function with specified value
	 * for getSlope parametar.
	 */	
	public Linear(double slope) {
		this.slope = slope;
	}

	/**
	 * Creates an instance of Linear transfer function with specified properties
	 */		
	public Linear(Properties properties) {
		try {
			this.slope = (Double)properties.getProperty("transferFunction.slope");
		} catch (NullPointerException e) {
			// if properties are not set just leave default values
		} catch (NumberFormatException e) {
			System.err.println("Invalid transfer function properties! Using default values.");
		}
	}

	/**
	 * Returns the slope parametar of this function
	 * @return  slope parametar of this function 
	 */	
	public double getSlope() {
		return this.slope;
	}

	/**
	 * Sets the slope parametar for this function
	 * @param slope value for the slope parametar
	 */	
	public void setSlope(double slope) {
		this.slope = slope;
	}

        @Override
	public double getOutput(double net) {
		return slope * net;
	}

	@Override
	public double getDerivative(double net) {
		return this.slope;
	}
}
