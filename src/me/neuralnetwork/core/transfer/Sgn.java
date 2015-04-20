package me.neuralnetwork.core.transfer;

import me.neuralnetwork.core.util.TransferFunctionType;

import java.util.Properties;

/**
 * Sgn neuron transfer function.
 */
public class Sgn extends TransferFunction {

	/**
	 *  y = 1, x > 0  
	 *  y = -1, x <= 0
	 */

	public double getOutput(double net) {
		if (net > 0d)
			return 1d;
		else
			return -1d;
	}

	/**
	 * Returns the properties of this function
	 * @return properties of this function
	 */	
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("transferFunction", TransferFunctionType.SGN.toString());
		return properties;
	}

}
