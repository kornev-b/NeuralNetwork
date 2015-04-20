package me.neuralnetwork.core;

/**
 * Created by Bogdan Kornev
 * on 23.03.2015, 2:25.
 */
public class Weight {
    private double value;
    private double weightChange;

    public Weight() {
        randomize();
    }

    public Weight(double value) {
        this.value = value;
    }

    public void increaseValue(double amount) {
        value += amount;
    }

    public void decreaseValue(double amount) {
        value -= amount;
    }

    public void randomize() {
        this.value = -0.1 + Math.random() * (0.2);
    }

    public void randomize(double min, double max) {
        this.value = min + Math.random() * (max - min);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWeightChange() {
        return weightChange;
    }

    public void setWeightChange(double weightChange) {
        this.weightChange = weightChange;
    }

    public void increaseWeightChange(double weightChange) {
        this.weightChange += weightChange;
    }
}
