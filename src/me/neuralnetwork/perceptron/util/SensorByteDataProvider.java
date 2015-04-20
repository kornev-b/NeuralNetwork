package me.neuralnetwork.perceptron.util;

import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.perceptron.SensorDataProvider;
import me.neuralnetwork.perceptron.bytesperceptron.DataSetGenerator;

/**
 * Created by Bogdan Kornev
 * on 20.03.2015, 15:30.
 */
public class SensorByteDataProvider implements SensorDataProvider {
    @Override
    public DataSet getSensorsDataSet() {
        return DataSet.createFromFile(DataSetGenerator.FILENAME, 4, 2, DataSetGenerator.DELIMITER, false);
    }

    @Override
    public Object getCrossValidationDataSet() {
        return null;
    }

    @Override
    public DataSet getTestDataSet() {
        return DataSet.createFromFile("test.txt", 4, 2, DataSetGenerator.DELIMITER, false);
    }
}
