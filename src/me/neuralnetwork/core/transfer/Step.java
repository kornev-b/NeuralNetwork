package me.neuralnetwork.core.transfer;

import me.neuralnetwork.core.util.Properties;

/**
 * Step neuron transfer function.
 * y = yHigh, x > 0
 * y = yLow, x <= 0
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class Step extends TransferFunction {

	/**
	 * Output value for high output level
	 */
	private double yHigh = 1d;
	
	/**
	 * Output value for low output level
	 */
	private double yLow = 0d;

	/**
	 * Creates an instance of Step transfer function
	 */
	public Step() {
	}

	/**
	 * Creates an instance of Step transfer function with specified properties
	 */	
	public Step(Properties properties) {
		try {
			this.yHigh = (Double)properties.getProperty("transferFunction.yHigh");
			this.yLow = (Double)properties.getProperty("transferFunction.yLow");
		} catch (NullPointerException e) {
			// if properties are not set just leave default values
		} catch (NumberFormatException e) {
			System.err.println("Invalid transfer function properties! Using default values.");
		}
	}

        @Override
	public double getOutput(double net) {
		if (net > 0d)
			return yHigh;
		else
			return yLow;
	}

	/**
	 * Returns output value for high output level 
	 * @return output value for high output level 
	 */
	public double getYHigh() {
		return this.yHigh;
	}
	
	/**
	 * Set output value for the high output level 
	 * @param yHigh value for the high output level 
	 */
	public void setYHigh(double yHigh) {
		this.yHigh = yHigh;
	}

	/**
	 * Returns output value for low output level 
	 * @return output value for low output level 
	 */	
	public double getYLow() {
		return this.yLow;
	}

	/**
	 * Set output value for the low output level 
	 * @param yLow value for the low output level
	 */	
	public void setYLow(double yLow) {
		this.yLow = yLow;
	}

	/**
	 * Returns the properties of this function
	 * @return properties of this function
	 */
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("transferFunction.yHigh", Double.toString(yHigh));
		properties.setProperty("transferFunction.yLow", Double.toString(yLow));
		return properties;
	}

}
