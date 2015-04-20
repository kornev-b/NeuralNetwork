package me.neuralnetwork.core;

import me.neuralnetwork.core.event.NeuralNetworkEvent;
import me.neuralnetwork.core.event.NeuralNetworkEventType;
import me.neuralnetwork.core.util.NeuronFactory;
import me.neuralnetwork.core.util.NeuronProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 23.03.2015, 2:23.
 */
public class Layer {
    /**
     * Parent neural network - to which this layer belongs
     */
    private NeuralNetwork parentNetwork;
    private String label;

    /**
     * Collection of neurons (Neuron instances)
     */
    protected List<Neuron> neurons;

    /**
     * Creates an instance of empty Layer
     */
    public Layer() {
        neurons = new ArrayList<>();
    }

    /**
     * Creates an instance of empty Layer for specified number of neurons
     * @param neuronsCount number of neurons in this layer
     */
    public Layer(int neuronsCount) {
        neurons = new ArrayList<>(neuronsCount);
    }

    /**
     * Creates an instance of Layer with the specified number of neurons with
     * specified neuron properties
     *
     * @param neuronsCount number of neurons in layer
     * @param neuronProperties properties of neurons in layer
     */
    public Layer(int neuronsCount, NeuronProperties neuronProperties) {
        neurons = new ArrayList<>(neuronsCount);

        for (int i = 0; i < neuronsCount; i++) {
            Neuron neuron = NeuronFactory.createNeuron(neuronProperties);
            this.addNeuron(neuron);
        }
    }

    public final void setParentNetwork(NeuralNetwork parent) {
        this.parentNetwork = parent;
    }

    public final NeuralNetwork getParentNetwork() {
        return this.parentNetwork;
    }

    public final Neuron[] getNeurons() {
        Neuron[] a = new Neuron[neurons.size()];
        return neurons.toArray(a);
    }

    public final void addNeuron(Neuron neuron) {
        // prevent adding null neurons
        if (neuron == null) {
            throw new IllegalArgumentException("Neuron cant be null!");
        }

        // set neuron's parent layer to this layer
        neuron.setParentLayer(this);

        // add new neuron at the end of the array
        neurons.add(neuron);
    }

    /**
     * Adds specified neuron to this layer, at specified index position
     *
     * Throws IllegalArgumentException if neuron is null, or index is
     * illegal value (index<0 or index>neuronsCount)
     *
     * @param neuron neuron to add
     * @param index index position at which neuron should be added
     */
    public final void addNeuron(int index, Neuron neuron) {
        // prevent adding null neurons
        if (neuron == null) {
            throw new IllegalArgumentException("Neuron cant be null!");
        }

        // add neuron to this layer
        neurons.add(index, neuron);

        // set neuron's parent layer to this layer
        neuron.setParentLayer(this);
    }

    public final void setNeuron(int index, Neuron neuron) {
        // make sure that neuron is not null
        if (neuron == null) {
            throw new IllegalArgumentException("Neuron cant be null!");
        }

        // new neuron at specified index position
        neurons.set(index, neuron);

        // set neuron's parent layer to this layer
        neuron.setParentLayer(this);

        // notify network listeners that neuron has been added
        if (parentNetwork != null)
            parentNetwork.fireNetworkEvent(new NeuralNetworkEvent(this, NeuralNetworkEventType.NEURON_ADDED));

    }

    /**
     * Removes neuron from layer
     *
     * @param neuron neuron to remove
     */
    public final void removeNeuron(Neuron neuron) {
        int index = indexOf(neuron);
        removeNeuronAt(index);
    }

    /**
     * Removes neuron at specified index position in this layer
     *
     * @param index index position of neuron to remove
     */
    public final void removeNeuronAt(int index) {
        Neuron neuron = neurons.get(index);
        neuron.setParentLayer(null);
        neuron.removeAllConnections(); // why we're doing this here? maybe we shouldnt
        neurons.remove(index);

        // notify listeners that neuron has been removed
        if (parentNetwork != null)
            parentNetwork.fireNetworkEvent(new NeuralNetworkEvent(this, NeuralNetworkEventType.NEURON_REMOVED));
    }

    public final void removeAllNeurons() {
        neurons.clear();

        // notify listeners that neurons has been removed
        if (parentNetwork != null)
            parentNetwork.fireNetworkEvent(new NeuralNetworkEvent(this, NeuralNetworkEventType.NEURON_REMOVED));
    }

    /**
     * Returns neuron at specified index position in this layer
     *
     * @param index neuron index position
     * @return neuron at specified index position
     */
    public Neuron getNeuronAt(int index) {
        return neurons.get(index);
    }

    /**
     * Returns the index position in layer for the specified neuron
     *
     * @param neuron neuron object
     * @return index position of specified neuron
     */
    public int indexOf(Neuron neuron) {
        return neurons.indexOf(neuron);
    }

    /**
     * Returns number of neurons in this layer
     *
     * @return number of neurons in this layer
     */
    public int getNeuronsCount() {
        return neurons.size();
    }

    /**
     * Performs calculaton for all neurons in this layer
     */
    public void calculate() {

        //neuronCalculators = parentNetwork.getNeuronCalculators();
        //   neuronCalculators = new CalculatorThread[4];
//                if (neuronCalculators == null) {
        for (Neuron neuron : neurons) { // use directly underlying array since its faster
            neuron.calculate();
        }
    }

    /**
     * Resets the activation and input levels for all neurons in this layer
     */
    public void reset() {
        for (Neuron neuron : this.neurons) {
            neuron.reset();
        }
    }

    /**
     * Initialize connection weights for the whole layer to to specified value
     *
     * @param value the weight value
     */
    public void initializeWeights(double value) {
        for (Neuron neuron : this.neurons) {
            neuron.initializeWeights(value);
        }
    }

    /**
     * Get layer label
     *
     * @return layer label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set layer label
     *
     * @param label layer label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isEmpty() {
        return neurons.isEmpty();
    }
}
