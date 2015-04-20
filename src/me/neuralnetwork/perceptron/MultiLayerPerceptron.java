package me.neuralnetwork.perceptron;

import me.neuralnetwork.core.Layer;
import me.neuralnetwork.core.NeuralNetwork;
import me.neuralnetwork.core.input.WeightedSum;
import me.neuralnetwork.core.neuron.BiasNeuron;
import me.neuralnetwork.core.neuron.InputNeuron;
import me.neuralnetwork.core.transfer.Linear;
import me.neuralnetwork.core.util.*;
import me.neuralnetwork.core.util.random.NguyenWidrowRandomizer;
import me.neuralnetwork.perceptron.learning.BackPropagation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 25.03.2015, 11:10.
 */
public class MultiLayerPerceptron extends NeuralNetwork<BackPropagation> {
    /**
     * Creates new MultiLayerPerceptron with specified number of neurons in layers
     *
     * @param neuronsInLayers collection of neuron number in layers
     */
    public MultiLayerPerceptron(List<Integer> neuronsInLayers) {
        // init neuron settings
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty("useBias", true);
        neuronProperties.setProperty("transferFunction", TransferFunctionType.SIGMOID);
        neuronProperties.setProperty("inputFunction", WeightedSum.class);

        this.createNetwork(neuronsInLayers, neuronProperties);
    }

    public MultiLayerPerceptron(int... neuronsInLayers) {
        // init neuron settings
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty("useBias", true);
        neuronProperties.setProperty("transferFunction",
                TransferFunctionType.SIGMOID);
        neuronProperties.setProperty("inputFunction", WeightedSum.class);

        List<Integer> neuronsInLayersVector = new ArrayList<>();
        for (int neuronsInLayer : neuronsInLayers) {
            neuronsInLayersVector.add(neuronsInLayer);
        }

        this.createNetwork(neuronsInLayersVector, neuronProperties);
    }

    public MultiLayerPerceptron(TransferFunctionType transferFunctionType, int... neuronsInLayers) {
        // init neuron settings
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty("useBias", true);
        neuronProperties.setProperty("transferFunction", transferFunctionType);
        neuronProperties.setProperty("inputFunction", WeightedSum.class);


        List<Integer> neuronsInLayersVector = new ArrayList<>();
        for (int neuronsInLayer : neuronsInLayers) {
            neuronsInLayersVector.add(neuronsInLayer);
        }

        this.createNetwork(neuronsInLayersVector, neuronProperties);
    }

    public MultiLayerPerceptron(List<Integer> neuronsInLayers, TransferFunctionType transferFunctionType) {
        // init neuron settings
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty("useBias", true);
        neuronProperties.setProperty("transferFunction", transferFunctionType);

        this.createNetwork(neuronsInLayers, neuronProperties);
    }

    /**
     * Creates new MultiLayerPerceptron net with specified number neurons in
     * getLayersIterator
     *
     * @param neuronsInLayers  collection of neuron numbers in layers
     * @param neuronProperties neuron properties
     */
    public MultiLayerPerceptron(List<Integer> neuronsInLayers, NeuronProperties neuronProperties) {
        this.createNetwork(neuronsInLayers, neuronProperties);
    }

    /**
     * Creates MultiLayerPerceptron Network architecture - fully connected
     * feed forward with specified number of neurons in each layer
     *
     * @param neuronsInLayers  collection of neuron numbers in getLayersIterator
     * @param neuronProperties neuron properties
     */
    private void createNetwork(List<Integer> neuronsInLayers, NeuronProperties neuronProperties) {
        // set network type
        this.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);

        // create input layer
        NeuronProperties inputNeuronProperties = new NeuronProperties(InputNeuron.class, Linear.class);
        Layer layer = LayerFactory.createLayer(neuronsInLayers.get(0), inputNeuronProperties);

        boolean useBias = true; // use bias neurons by default
        if (neuronProperties.hasProperty("useBias")) {
            useBias = (Boolean) neuronProperties.getProperty("useBias");
        }

        if (useBias) {
            layer.addNeuron(new BiasNeuron());
        }

        this.addLayer(layer);

        // create layers
        Layer prevLayer = layer;

        //for(Integer neuronsNum : neuronsInLayers)
        for (int layerIdx = 1; layerIdx < neuronsInLayers.size(); layerIdx++) {
            Integer neuronsNum = neuronsInLayers.get(layerIdx);
            // createLayer layer
            layer = LayerFactory.createLayer(neuronsNum, neuronProperties);

            if (useBias && (layerIdx < (neuronsInLayers.size() - 1))) {
                layer.addNeuron(new BiasNeuron());
            }

            // add created layer to network
            this.addLayer(layer);
            // createLayer full connectivity between previous and this layer
            ConnectionFactory.fullConnect(prevLayer, layer);

            prevLayer = layer;
        }

        // set input and output cells for network
        NeuralNetworkFactory.setDefaultIO(this);

        // set learnng rule
        this.setLearningRule(new BackPropagation());

        this.randomizeWeights(new NguyenWidrowRandomizer(-0.7, 0.7));
    }

    public void connectInputsToOutputs() {
        // connect first and last layer
        ConnectionFactory.fullConnect(getLayerAt(0), getLayerAt(getLayersCount() - 1), false);
    }
}
