package me.neuralnetwork.core.util;

import me.neuralnetwork.core.Neuron;
import me.neuralnetwork.core.input.InputFunction;
import me.neuralnetwork.core.input.WeightedSum;
import me.neuralnetwork.core.transfer.Linear;
import me.neuralnetwork.core.transfer.TransferFunction;

/**
 * Represents properties of a neuron.
 */
public class NeuronProperties extends Properties {
    public NeuronProperties() {
        initKeys();
        this.setProperty("inputFunction", WeightedSum.class);
        this.setProperty("transferFunction", Linear.class);
        this.setProperty("neuronType", Neuron.class);
    }

    public NeuronProperties(Class<? extends Neuron> neuronClass) {
        initKeys();
        this.setProperty("inputFunction", WeightedSum.class);
        this.setProperty("transferFunction", Linear.class);
        this.setProperty("neuronType", neuronClass);
    }

    public NeuronProperties(Class<? extends Neuron> neuronClass, Class<? extends TransferFunction> transferFunctionClass) {
        initKeys();
        this.setProperty("inputFunction", WeightedSum.class);
        this.setProperty("transferFunction", transferFunctionClass);
        this.setProperty("neuronType", neuronClass);
    }

    public NeuronProperties(Class<? extends Neuron> neuronClass,
                            Class<? extends InputFunction> inputFunctionClass,
                            Class<? extends TransferFunction> transferFunctionClass) {
        initKeys();
        this.setProperty("inputFunction", inputFunctionClass);
        this.setProperty("transferFunction", transferFunctionClass);
        this.setProperty("neuronType", neuronClass);
    }

    public NeuronProperties(Class<? extends Neuron> neuronClass, TransferFunctionType transferFunctionType) {
        initKeys();
        this.setProperty("inputFunction", WeightedSum.class);
        this.setProperty("transferFunction", transferFunctionType.getTypeClass());
        this.setProperty("neuronType", neuronClass);
    }

    public NeuronProperties(TransferFunctionType transferFunctionType, boolean useBias) {
        initKeys();
        this.setProperty("inputFunction", WeightedSum.class);
        this.setProperty("transferFunction", transferFunctionType.getTypeClass());
        this.setProperty("useBias", useBias);
        this.setProperty("neuronType", Neuron.class);
    }

    private void initKeys() {
        createKeys("inputFunction", "transferFunction", "neuronType", "useBias");
    }

    public Class getInputFunction() {
        Object val = this.get("inputFunction");
        if (!val.equals("")) {
            return (Class) val;
        }
        return null;
    }

    public Class getTransferFunction() {
        return (Class) this.get("transferFunction");
    }

    public Class getNeuronType() {
        return (Class) this.get("neuronType");
    }

    public Properties getTransferFunctionProperties() {
        Properties tfProperties = new Properties();
        for (Object o : this.keySet()) {
            String name = o.toString();
            if (name.contains("transferFunction")) {
                tfProperties.setProperty(name, this.get(name));
            }
        }
        return tfProperties;
    }

    @Override
    public final void setProperty(String key, Object value) {
        if (value instanceof TransferFunctionType) {
            value = ((TransferFunctionType) value).getTypeClass();
        }
        put(key, value);
    }
}