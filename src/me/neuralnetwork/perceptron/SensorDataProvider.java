package me.neuralnetwork.perceptron;

/**
 * Created by Bogdan Kornev
 * on 20.03.2015, 15:28.
 */
public interface SensorDataProvider<T> {
    double DATA_SET_PERCENT = 0.6;
    double CROSS_VALIDATION_SET_PERCENT = 0.2;
    double TEST_SET_PERCENT = 0.2;

    T getSensorsDataSet();

    T getCrossValidationDataSet();

    T getTestDataSet();
}
