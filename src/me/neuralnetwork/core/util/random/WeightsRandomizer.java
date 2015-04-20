package me.neuralnetwork.core.util.random;

import me.neuralnetwork.core.Connection;
import me.neuralnetwork.core.Layer;
import me.neuralnetwork.core.NeuralNetwork;
import me.neuralnetwork.core.Neuron;

import java.util.Random;

/**
 * Basic weights randomizer, iterates and randomizes all connection weights in network.
 */
public class WeightsRandomizer {
 
    /**
     * Random number genarator used by randomizers
     */
    protected Random randomGenerator;

    /**
     * Create a new instance of WeightsRandomizer
     */
    public WeightsRandomizer() {
        this.randomGenerator = new Random();
    }
    
    /**
     * Create a new instance of WeightsRandomizer with specified random generator
     * If you use the same random generators, you'll get the same random sequences
     * @param randomGenerator random geneartor to use for randomizing weights
     */    
    public WeightsRandomizer(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }    
    
    /**
     * Gets random generator used to generate random values
     * @return random generator used to generate random values
     */
    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }
    
   
    /**
     * Iterates and randomizes all layers in specified network
     * @param neuralNetwork neural network to randomize
     */
    public void randomize(NeuralNetwork neuralNetwork) {
        for (Layer layer : neuralNetwork.getLayers()) {
            this.randomize(layer);
        }
    }
    
    /**
     * Iterate and randomizes all neurons in specified layer
     * @param layer layer to randomize
     */    
    public void randomize(Layer layer) {
        for (Neuron neuron : layer.getNeurons()) {
            randomize(neuron);
        }
    }
    
    /**
     * Iterates and randomizes all connection weights in specified neuron
     * @param neuron neuron to randomize
     */        
    public void randomize(Neuron neuron) {
        for (Connection connection : neuron.getInputConnections()) {
            connection.getWeight().setValue(nextRandomWeight());
        }
    }

    /**
     * Returns next random value from random generator, that will be used to initialize weight
     * @return next random value fro random generator
     */
    protected double nextRandomWeight() {
        return randomGenerator.nextDouble();
    }
}
