package me.neuralnetwork.core.util;

import me.neuralnetwork.core.Layer;
import me.neuralnetwork.core.NeuralNetwork;
import me.neuralnetwork.core.Neuron;
import me.neuralnetwork.core.neuron.BiasNeuron;
import me.neuralnetwork.perceptron.MultiLayerPerceptron;
import me.neuralnetwork.perceptron.learning.BackPropagation;

import java.util.ArrayList;

/**
 * Provides methods to create various neural networks.
 */
public class NeuralNetworkFactory {
    /**
     * Creates and returns a new instance of Multi Layer Perceptron
     *
     * @param layersStr            space separated number of neurons in layers
     * @param transferFunctionType transfer function type for neurons
     * @return instance of Multi Layer Perceptron
     */
    public static MultiLayerPerceptron createMLPerceptron(String layersStr, TransferFunctionType transferFunctionType) {
        ArrayList<Integer> layerSizes = VectorParser.parseInteger(layersStr);
        return new MultiLayerPerceptron(layerSizes,
                transferFunctionType);
    }

    /**
     * Creates and returns a new instance of Multi Layer Perceptron
     *
     * @param layersStr            space separated number of neurons in layers
     * @param transferFunctionType transfer function type for neurons
     * @return instance of Multi Layer Perceptron
     */
    public static MultiLayerPerceptron createMLPerceptron(String layersStr, TransferFunctionType transferFunctionType,
                                                          Class learningRule, boolean useBias, boolean connectIO) {
        ArrayList<Integer> layerSizes = VectorParser.parseInteger(layersStr);
        NeuronProperties neuronProperties = new NeuronProperties(transferFunctionType, useBias);
        MultiLayerPerceptron nnet = new MultiLayerPerceptron(layerSizes, neuronProperties);

        // set learning rule - TODO: use reflection here
        if (learningRule.getName().equals(BackPropagation.class.getName())) {
            nnet.setLearningRule(new BackPropagation());
        }

        // connect io
        if (connectIO) {
            nnet.connectInputsToOutputs();
        }

        return nnet;
    }

    /**
     * Sets default input and output neurons for network (first layer as input,
     * last as output)
     */
    public static void setDefaultIO(NeuralNetwork nnet) {
        ArrayList<Neuron> inputNeuronsList = new ArrayList<>();
        Layer firstLayer = nnet.getLayerAt(0);
        for (Neuron neuron : firstLayer.getNeurons()) {
            if (!(neuron instanceof BiasNeuron)) {  // don't set input to bias neurons
                inputNeuronsList.add(neuron);
            }
        }

        Neuron[] inputNeurons = new Neuron[inputNeuronsList.size()];
        inputNeurons = inputNeuronsList.toArray(inputNeurons);
        Neuron[] outputNeurons = ((Layer) nnet.getLayerAt(nnet.getLayersCount() - 1)).getNeurons();

        nnet.setInputNeurons(inputNeurons);
        nnet.setOutputNeurons(outputNeurons);
    }
}
