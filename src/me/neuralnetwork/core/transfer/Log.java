package me.neuralnetwork.core.transfer;

/**
 * <pre>
 * Log neuron transfer function.
 * 
 * output = log(input)
 * </pre>
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class Log extends TransferFunction {

    @Override
    public double getOutput(double net) {
        return Math.log(net);
    }
    
    @Override
    public double getDerivative(double net) {
	return (1/net);
    }       
        
}
