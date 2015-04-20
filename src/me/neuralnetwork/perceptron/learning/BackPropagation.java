package me.neuralnetwork.perceptron.learning;

import me.neuralnetwork.core.*;
import me.neuralnetwork.core.learning.SupervisedLearning;
import me.neuralnetwork.core.transfer.TransferFunction;

/**
 * Created by Bogdan Kornev
 * on 23.03.2015, 14:16.
 */
public class BackPropagation extends SupervisedLearning {
    @Override
    protected void updateNetworkWeights(double[] outputError) {
        calculateErrorAndUpdateOutputNeurons(outputError);
        calculateErrorAndUpdateHiddenNeurons();
    }

    /**
     * This method implements weights update procedure for the output neurons
     * Calculates delta/error using formula
     * delta = learningRate * f'(net) * (desiredOutput - actualOutput) where
     *      f'(net) - derivative of input function in a neuron (weighted sum of input values)
     *      if we use sigmoid as activation function:
     *          1/1+e^-slope*net
     *          where slope is pre-defined constant then its derivative is simple
     *          f'(net) = slope * f(net)(1 - f(net))
     * and calls updateNeuronWeights to update neuron's weights
     * for each output neuron
     *
     * @param outputError
     *            error vector for output neurons
     */
    protected void calculateErrorAndUpdateOutputNeurons(double[] outputError) {
        for (int i = 0; i < neuralNetwork.getOutputsCount(); i++) {
            if (outputError[i] == 0) {
                continue;
            }
            Neuron outputNeuron = neuralNetwork.getOutputNeurons()[i];
            TransferFunction tf = outputNeuron.getTransferFunction();
            double delta = learningRate * tf.getDerivative(outputNeuron.getNetInput()) * outputError[i];
            outputNeuron.setError(delta);
            updateNeuronWeights(outputNeuron);
        }
    }

    /**
     * This method implements weights adjustment for the hidden layers
     */
    protected void calculateErrorAndUpdateHiddenNeurons() {
        for (int i = neuralNetwork.getLayersCount() - 2; i > 0; i--) {
            Layer layer = neuralNetwork.getLayerAt(i);
            for (Neuron hidden : layer.getNeurons()) {
                double hiddenDelta = calculateHiddenNeuronError(hidden);
                hidden.setError(hiddenDelta);
                updateNeuronWeights(hidden);
            }
        }
    }

    /**
     * This method implements calculating error of a neuron in a hidden layer.
     * Calculates delta/error using formula
     * delta = f'(net) * sum (wK * deltaK) where
     *      wK - weight of output connection k
     *      deltaK - error of neuron which connection k goes to
     */
    protected double calculateHiddenNeuronError(Neuron neuron) {
        double deltaSum = 0;
        for (Connection connection : neuron.getOutConnections()) {
            double delta = connection.getToNeuron().getError() * connection.getWeight().getValue();
            deltaSum += delta;
        }

        TransferFunction tf = neuron.getTransferFunction();
        double derivative = tf.getDerivative(neuron.getNetInput());
        return derivative * deltaSum;
    }

    /**
     * This method implements weights update procedure for the single neuron
     * It iterates through all neuron's input connections, and calculates/set weight change for each weight
     * using formula
     *      deltaWeight = learningRate * neuronError * input
     *
     * where neuronError is calculated in previous steps by using gradient descent
     * and backprop.
     *
     * Backpropagation updates an MLP’s weights based on gradient descent of the error
     * function.
     *
     * @param neuron
     *            neuron to update weights
     *
     */
    public void updateNeuronWeights(Neuron neuron) {
        // tanh can be used to minimise the impact of big error values, which can cause network instability
        // suggested at https://sourceforge.net/tracker/?func=detail&atid=1107579&aid=3130561&group_id=238532
        // double neuronError = Math.tanh(neuron.getError());
        for (Connection connection : neuron.getInputConnections()) {
            double input = connection.getInput();
            double weightChange = learningRate * neuron.getError() * input;
            Weight weight = connection.getWeight();
            // if the learning is in online mode (not batch) apply the weight change immediately
            if (!isInBatchMode()) {
                weight.setWeightChange(weightChange);
                // weight change can be negative
                // so that's ok to call increase
                weight.increaseValue(weightChange);
            } else {
            // otherwise its in batch mode, so sum the weight changes and apply them later,
            // after the current epoch (see SupervisedLearning.doLearningEpoch method)
                weight.increaseWeightChange(weightChange);
            }
        }
    }
}
