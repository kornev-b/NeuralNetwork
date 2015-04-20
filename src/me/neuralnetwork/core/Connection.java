package me.neuralnetwork.core;

/**
 * Created by Bogdan Kornev
 * on 23.03.2015, 2:23.
 */
public class Connection {
    /**
     * From neuron for this connection (source neuron).
     * This connection is output connection for from neuron.
     */
    protected Neuron fromNeuron;

    /**
     * To neuron for this connection (target, destination neuron)
     * This connection is input connection for to neuron.
     */
    protected Neuron toNeuron;

    /**
     * Weight for this connection
     */
    protected Weight weight;

    /**
     * Creates a new connection between specified neurons with random weight
     *
     * @param fromNeuron neuron to connect from
     * @param  toNeuron neuron to connect to
     */
    public Connection(Neuron fromNeuron, Neuron toNeuron) {

        if (fromNeuron == null) {
            throw new IllegalArgumentException("From neuron in connection cant be null !");
        } else {
            this.fromNeuron = fromNeuron;
        }

        if (toNeuron == null) {
            throw new IllegalArgumentException("To neuron in connection cant be null!");
        } else {
            this.toNeuron = toNeuron;
        }

        this.weight = new Weight();
    }

    /**
     * Creates a new connection to specified neuron with specified weight object
     *
     * @param fromNeuron neuron to connect from
     * @param toNeuron neuron to connect to
     * @param weight
     *            weight for this connection
     */
    public Connection(Neuron fromNeuron, Neuron toNeuron, Weight weight) {
        if (fromNeuron == null) {
            throw new IllegalArgumentException("From neuron in connection cant be null !");
        } else {
            this.fromNeuron = fromNeuron;
        }

        if (toNeuron == null) {
            throw new IllegalArgumentException("To neuron in connection cant be null!");
        } else {
            this.toNeuron = toNeuron;
        }

        if (weight == null) {
            throw new IllegalArgumentException("Connection Weight cant be null!");
        } else {
            this.weight = weight;
        }

    }

    /**
     * Creates a new connection to specified neuron with specified weight value
     *
     * @param fromNeuron neuron to connect from
     * @param  toNeuron neuron to connect to
     * @param weightVal
     *            weight value for this connection
     */
    public Connection(Neuron fromNeuron, Neuron toNeuron, double weightVal) {
        if (fromNeuron == null) {
            throw new IllegalArgumentException("From neuron in connection cant be null !");
        } else {
            this.fromNeuron = fromNeuron;
        }

        if (toNeuron == null) {
            throw new IllegalArgumentException("To neuron in connection cant be null!");
        } else {
            this.toNeuron = toNeuron;
        }

        this.weight = new Weight(weightVal);
    }

    /**
     * Returns weight for this connection
     *
     * @return weight for this connection
     */
    public Weight getWeight() {
        return this.weight;
    }

    /**
     * Set the weight of the connection.
     * @param weight The new weight of the connection.
     */
    public void setWeight(Weight weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Connection Weight cant be null!");
        } else {
            this.weight = weight;
        }
    }

    /**
     * Returns input received through this connection - the activation that
     * comes from the output of the cell on the other end of connection
     *
     * @return input received through this connection
     */
    public double getInput() {
        return this.fromNeuron.getOutput();
    }

    /**
     * Returns the weighted input received through this connection
     *
     * @return weighted input received through this connection
     */
    public double getWeightedInput() {
        return this.fromNeuron.getOutput() * weight.getValue();
    }

    /**
     * Gets from neuron for this connection
     * @return from neuron for this connection
     */
    public Neuron getFromNeuron() {
        return fromNeuron;
    }

    /**
     * Sets from neuron for this connection
     * @param fromNeuron neuron to set as from neuron
     */
    public void setFromNeuron(Neuron fromNeuron) {
        if (fromNeuron == null) {
            throw new IllegalArgumentException("From neuron in connection cant be null!");
        } else {
            this.fromNeuron = fromNeuron;
        }
    }

    /**
     * Gets to neuron for this connection
     * @return neuron to set as to neuron
     */
    public Neuron getToNeuron() {
        return toNeuron;
    }

    /**
     * Sets to neuron for this connection
     * @param toNeuron
     */
    public void setToNeuron(Neuron toNeuron) {
        if (toNeuron == null) {
            throw new IllegalArgumentException("From neuron in connection cant be null!");
        } else {
            this.toNeuron = toNeuron;
        }
    }
}
