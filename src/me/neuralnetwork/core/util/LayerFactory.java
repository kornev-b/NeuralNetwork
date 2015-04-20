package me.neuralnetwork.core.util;

import me.neuralnetwork.core.Layer;
import me.neuralnetwork.core.Neuron;
import me.neuralnetwork.core.transfer.TransferFunction;

import java.util.List;

/**
 * Provides methods to create instance of a Layer with specifed number of neurons and neuron's properties.
 */
public class LayerFactory {

    public static Layer createLayer(int neuronsCount, NeuronProperties neuronProperties) {
        return new Layer(neuronsCount, neuronProperties);
    }

    public static Layer createLayer(int neuronsCount, TransferFunctionType transferFunctionType) {
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty("transferFunction", transferFunctionType);
        return new Layer(neuronsCount, neuronProperties);
    }

    public static Layer createLayer(int neuronsCount, Class<? extends TransferFunction> transferFunctionClass) {
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty("transferFunction", transferFunctionClass);
        return new Layer(neuronsCount, neuronProperties);
    }

    public static Layer createLayer(List<NeuronProperties> neuronPropertiesVector) {
        Layer layer = new Layer();

        for (NeuronProperties neuronProperties : neuronPropertiesVector) {
            Neuron neuron = NeuronFactory.createNeuron(neuronProperties);
            layer.addNeuron(neuron);
        }

        return layer;
    }

}