package me.neuralnetwork.perceptron.bytesperceptron;

import me.neuralnetwork.core.NeuralNetwork;
import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.core.data.DataSetRow;
import me.neuralnetwork.core.event.LearningEvent;
import me.neuralnetwork.core.event.LearningEventListener;
import me.neuralnetwork.core.learning.SupervisedLearning;
import me.neuralnetwork.perceptron.MultiLayerPerceptron;
import me.neuralnetwork.perceptron.learning.BackPropagation;
import me.neuralnetwork.perceptron.util.SensorByteDataProvider;

import java.util.Arrays;

/**
 * Created by Bogdan Kornev
 * on 23.03.2015, 23:09.
 */
public class Main {
    public static void main(String[] args) {
        DataSetGenerator dataSetGenerator = new DataSetGenerator();
        dataSetGenerator.setCount(1000);
        dataSetGenerator.generate();

        SensorByteDataProvider dataProvider = new SensorByteDataProvider();
        DataSet trainingSet = dataProvider.getSensorsDataSet();

        if (trainingSet.size() > 0) {
            NeuralNetwork neuralNetwork = new MultiLayerPerceptron(4, 6, 2);
            final SupervisedLearning learningRule = new BackPropagation();
            learningRule.setNeuralNetwork(neuralNetwork);
            learningRule.setMaxIterations(100);
            learningRule.setMinErrorChange(0.001);
            learningRule.addListener(new LearningEventListener() {
                @Override
                public void handleLearningEvent(LearningEvent event) {
                    System.out.println("Iteration " + ((SupervisedLearning) event.getSource()).getCurrentIteration() +
                            " Network error is " + ((SupervisedLearning) event.getSource()).getTotalNetworkError());
                }
            });
            neuralNetwork.learn(trainingSet, learningRule);
            System.out.println("Total network error is " + learningRule.getTotalNetworkError());
            System.out.println("Min error change iterations count is " + learningRule.getMinErrorChangeIterationsCount());
            testNeuralNetwork(dataProvider, neuralNetwork);
        } else {
            System.err.println("Dataset is empty!");
        }
    }

    private static void testNeuralNetwork(SensorByteDataProvider dataProvider, NeuralNetwork neuralNetwork) {
        DataSet testSet = dataProvider.getTestDataSet();
        for (DataSetRow row : testSet.getRows()) {
            neuralNetwork.setInput(row.getInput());
            neuralNetwork.calculate();
            double[] networkOutput = neuralNetwork.getOutput();

            System.out.print("Input: " + Arrays.toString(row.getInput()) );
            System.out.print(" Output: " + Arrays.toString(networkOutput));
            System.out.println(" Desired output: " + Arrays.toString(row.getDesiredOutput()));
        }
    }
}
