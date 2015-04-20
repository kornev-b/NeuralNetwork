package me.neuralnetwork.core;

import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.core.event.NeuralNetworkEvent;
import me.neuralnetwork.core.event.NeuralNetworkEventListener;
import me.neuralnetwork.core.event.NeuralNetworkEventType;
import me.neuralnetwork.core.exception.VectorSizeMismatchException;
import me.neuralnetwork.core.learning.IterativeLearning;
import me.neuralnetwork.core.learning.LearningRule;
import me.neuralnetwork.core.util.NeuralNetworkType;
import me.neuralnetwork.core.util.random.RangeRandomizer;
import me.neuralnetwork.core.util.random.WeightsRandomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bogdan Kornev
 * on 23.03.2015, 2:22.
 */
public class NeuralNetwork<L extends LearningRule> {
    private List<NeuralNetworkEventListener> listeners = new ArrayList<>();
    private NeuralNetworkType type;
    private List<Layer> layers;

    /**
     * Neural network output buffer
     */
    protected double[] output;

    /**
     * Reference to network input neurons
     */
    private List<Neuron> inputNeurons;

    /**
     * Reference to network output neurons
     */
    private List<Neuron> outputNeurons;

    /**
     * Learning rule for this network
     */
    private L learningRule; // learning algorithme
    /**
     * Separate thread for learning rule
     */
    private Thread learningThread; // thread for learning rule
    /**
     * Label for this network
     */
    private String label = "";

    public NeuralNetwork() {
        this.layers = new ArrayList<>();
        this.inputNeurons = new ArrayList<>();
        this.outputNeurons = new ArrayList<>();
    }

    public void addLayer(Layer layer) {
        // in case of null value throw exception to prevent adding null layers
        if (layer == null) {
            throw new IllegalArgumentException("Layer cannot be null!");
        }

        // add layer to layers collection
        layers.add(layer);

        // set parent network for added layer
        layer.setParentNetwork(this);

        // notify listeners that layer has been added
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_ADDED));
    }

    /**
     * Adds layer to specified index position in network
     *
     * @param index index position to add layer
     * @param layer layer to add
     */
    public void addLayer(int index, Layer layer) {
        // in case of null value throw exception to prevent adding null layers
        if (layer == null) {
            throw new IllegalArgumentException("Layer cant be null!");
        }

        // add layer to layers collection at specified position
        layers.add(index, layer);

        // set parent network for added layer
        layer.setParentNetwork(this);

        // notify listeners that layer has been added
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_ADDED));
    }

    public void removeLayer(Layer layer) {
        if (!layers.remove(layer)) {
            throw new RuntimeException("Layer not in Neural n/w");
        }

        // notify listeners that layer has been removed
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_REMOVED));
    }

    /**
     * Removes layer at specified index position from net
     *
     * @param index int value represents index postion of layer which should be
     *              removed
     */
    public void removeLayerAt(int index) throws ArrayIndexOutOfBoundsException {
        Layer layer = layers.get(index);
        layers.remove(index);

        // notify listeners that layer has been removed
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_REMOVED));
    }

    /**
     * Returns layers array
     *
     * @return array of layers
     */
    public final Layer[] getLayers() {
        Layer[] a = new Layer[layers.size()];
        return layers.toArray(a);
    }

    /**
     * Returns layer at specified index
     *
     * @param index layer index position
     * @return layer at specified index position
     */
    public Layer getLayerAt(int index) {
        return layers.get(index);
    }

    /**
     * Returns index position of the specified layer
     *
     * @param layer requested Layer object
     * @return layer position index
     */
    public int indexOf(Layer layer) {
        return layers.indexOf(layer);
    }

    /**
     * Returns number of layers in network
     *
     * @return number of layes in net
     */
    public int getLayersCount() {
        return layers.size();
    }

    /**
     * Sets network input. Input is an array of double values.
     *
     * @param inputVector network input as double array
     */
    public void setInput(double... inputVector) throws VectorSizeMismatchException {
        if (inputVector.length != inputNeurons.size()) {
            throw new VectorSizeMismatchException("Input vector size does not match network input dimension!");
        }

        int i = 0;
        for (Neuron neuron : this.inputNeurons) {
            neuron.setInput(inputVector[i]); // set input to the coresponding neuron
            i++;
        }

    }

    /**
     * Returns network output vector. Output vector is an array  collection of Double
     * values.
     *
     * @return network output vector
     */
    public double[] getOutput() {
        // double[] outputVector = new double[outputNeurons.length];// use attribute to avoid creating to arrays and avoid GC work
        for (int i = 0; i < outputNeurons.size(); i++) {
            output[i] = outputNeurons.get(i).getOutput();
        }

        return output;
    }

    /**
     * Performs calculation on whole network
     */
    public void calculate() {
        for (Layer layer : layers) {
            layer.calculate();
        }

        fireNetworkEvent(new NeuralNetworkEvent(this, NeuralNetworkEventType.CALCULATED));
    }

    /**
     * Resets the activation levels for whole network
     */
    public void reset() {
        for (Layer layer : this.layers) {
            layer.reset();
        }
    }

    /**
     * Learn the specified training set
     *
     * @param trainingSet set of training elements to learn
     */
    public void learn(DataSet trainingSet) {
        if (trainingSet == null) {
            throw new IllegalArgumentException("Training set is null!");
        }

        learningRule.learn(trainingSet);
    }

    /**
     * Learn the specified training set, using specified learning rule
     *
     * @param trainingSet  set of training elements to learn
     * @param learningRule instance of learning rule to use for learning
     */
    public void learn(DataSet trainingSet, L learningRule) {
        setLearningRule(learningRule);
        learningRule.learn(trainingSet);
    }

    /**
     * Starts learning in a new thread to learn the specified training set, and
     * immediately returns from method to the current thread execution
     *
     * @param trainingSet set of training elements to learn
     */
    public void learnInNewThread(final DataSet trainingSet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                learningRule.learn(trainingSet);
            }
        }).start();
    }

    /**
     * Starts learning with specified learning rule in new thread to learn the
     * specified training set, and immediately returns from method to the
     * current thread execution
     *
     * @param trainingSet  set of training elements to learn
     * @param learningRule learning algorithm
     */
    public void learnInNewThread(DataSet trainingSet, L learningRule) {
        setLearningRule(learningRule);
        learnInNewThread(trainingSet);
    }

    /**
     * Stops learning
     */
    public void stopLearning() {
        learningRule.stopLearning();
    }

    /**
     * Pause the learning - puts learning thread in wait state. Makes sense only
     * wen learning is done in new thread with learnInNewThread() method
     */
    public void pauseLearning() {
        if (learningRule instanceof IterativeLearning) {
            ((IterativeLearning) learningRule).pause();
        }
    }

    /**
     * Resumes paused learning - notifies the learning rule to continue
     */
    public void resumeLearning() {
        if (learningRule instanceof IterativeLearning) {
            ((IterativeLearning) learningRule).resume();
        }
    }

    /**
     * Randomizes connection weights for the whole network
     */
    public void randomizeWeights() {
        randomizeWeights(new WeightsRandomizer());
    }

    /**
     * Randomizes connection weights for the whole network within specified
     * value range
     */
    public void randomizeWeights(double minWeight, double maxWeight) {
        randomizeWeights(new RangeRandomizer(minWeight, maxWeight));
    }

    /**
     * Randomizes connection weights for the whole network using specified
     * random generator
     */
    public void randomizeWeights(Random random) {
        randomizeWeights(new WeightsRandomizer(random));
    }

    /**
     * Randomizes connection weights for the whole network using specified
     * randomizer
     *
     * @param randomizer random weight generator to use
     */
    public void randomizeWeights(WeightsRandomizer randomizer) {
        randomizer.randomize(this);
    }

    /**
     * Returns type of this network
     *
     * @return network type
     */
    public NeuralNetworkType getNetworkType() {
        return type;
    }

    /**
     * Sets type for this network
     *
     * @param type network type
     */
    public void setNetworkType(NeuralNetworkType type) {
        this.type = type;
    }

    /**
     * Returns input neurons
     *
     * @return input neurons
     */
    public Neuron[] getInputNeurons() {
        Neuron[] a = new Neuron[inputNeurons.size()];
        return this.inputNeurons.toArray(a);
    }

    /**
     * Gets number of input neurons
     *
     * @return number of input neurons
     */
    public int getInputsCount() {
        return this.inputNeurons.size();
    }

    /**
     * Sets input neurons
     *
     * @param inputNeurons array of input neurons
     */
    public void setInputNeurons(Neuron[] inputNeurons) {
        for (Neuron neuron : inputNeurons) {
            this.inputNeurons.add(neuron);
        }
    }

    /**
     * Returns output neurons
     *
     * @return array of output neurons
     */
    public Neuron[] getOutputNeurons() {
        Neuron[] a = new Neuron[outputNeurons.size()];
        return this.outputNeurons.toArray(a);
    }

    public int getOutputsCount() {
        return this.outputNeurons.size();
    }

    /**
     * Sets output neurons
     *
     * @param outputNeurons output neurons collection
     */
    public void setOutputNeurons(Neuron[] outputNeurons) {
        for (Neuron neuron : outputNeurons) {
            this.outputNeurons.add(neuron);
        }
        this.output = new double[outputNeurons.length];
    }

    /**
     * Sets labels for output neurons
     *
     * @param labels labels for output neurons
     */
    public void setOutputLabels(String[] labels) {
        for (int i = 0; i < outputNeurons.size(); i++) {
            outputNeurons.get(i).setLabel(labels[i]);
        }
    }

    /**
     * Returns the learning algorithm of this network
     *
     * @return algorithm for network training
     */
    public L getLearningRule() {
        return this.learningRule;
    }

    /**
     * Sets learning algorithm for this network
     *
     * @param learningRule learning algorithm for this network
     */
    public void setLearningRule(L learningRule) {
        if (learningRule == null) {
            throw new IllegalArgumentException("Learning rule can't be null!");
        }

        learningRule.setNeuralNetwork(this);
        this.learningRule = learningRule;
    }

    /**
     * Returns the current learning thread (if it is learning in the new thread
     * Check what happens if it learns in the same thread)
     */
    public Thread getLearningThread() {
        return learningThread;
    }

    /**
     * Returns all network weights as an double array
     *
     * @return network weights as an double array
     */
    public Double[] getWeights() {
        List<Double> weights = new ArrayList<>();
        for (Layer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                for (Connection conn : neuron.getInputConnections()) {
                    weights.add(conn.getWeight().getValue());
                }
            }
        }

        return weights.toArray(new Double[weights.size()]);
    }

    /**
     * Sets network weights from the specified double array
     *
     * @param weights array of weights to set
     */
    public void setWeights(double[] weights) {
        int i = 0;
        for (Layer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                for (Connection conn : neuron.getInputConnections()) {
                    conn.getWeight().setValue(weights[i]);
                    i++;
                }
            }
        }
    }

    public boolean isEmpty() {
        return layers.isEmpty();
    }

    /**
     * Creates connection with specified weight value between specified neurons
     *
     * @param fromNeuron neuron to connect
     * @param toNeuron   neuron to connect to
     * @param weightVal  connection weight value
     */
    public void createConnection(Neuron fromNeuron, Neuron toNeuron, double weightVal) {
        //  Connection connection = new Connection(fromNeuron, toNeuron, weightVal);
        toNeuron.addInputConnection(fromNeuron, weightVal);
    }

    @Override
    public String toString() {
        if (label != null) {
            return label;
        }

        return super.toString();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // This methods allows classes to register for LearningEvents
    public synchronized void addListener(NeuralNetworkEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener is null!");

        listeners.add(listener);
    }

    // This methods allows classes to unregister for LearningEvents
    public synchronized void removeListener(NeuralNetworkEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener is null!");

        listeners.remove(listener);
    }

    // This method is used to fire NeuralNetworkEvents
    public synchronized void fireNetworkEvent(NeuralNetworkEvent evt) {
        for (NeuralNetworkEventListener listener : listeners) {
            listener.handleNeuralNetworkEvent(evt);
        }
    }
}
