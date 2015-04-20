package me.neuralnetwork.perceptron.flightdelay;

import me.neuralnetwork.core.NeuralNetwork;
import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.core.data.DataSetRow;
import me.neuralnetwork.core.event.LearningEvent;
import me.neuralnetwork.core.event.LearningEventListener;
import me.neuralnetwork.core.learning.SupervisedLearning;
import me.neuralnetwork.perceptron.MultiLayerPerceptron;
import me.neuralnetwork.perceptron.flightdelay.model.Flight;
import me.neuralnetwork.perceptron.learning.BackPropagation;
import me.neuralnetwork.perceptron.util.SensorByteDataProvider;

import java.util.*;

/**
 * Created by Bogdan Kornev
 * on 14.04.2015, 0:20.
 */
public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightDataPreprocessor.getFlightsFromCSVFile("data.txt");
        Collections.sort(flights, new Comparator<Flight>() {
            @Override
            public int compare(Flight f1, Flight f2) {
                return f1.getOutTime() > f2.getOutTime() ? 1 : -1;
            }
        });
//        System.out.println(new Date(flights.get(0).getOutTime()));
//        System.out.println(new Date(flights.get(flights.size() - 1).getOutTime()));

        SensorFlightDataProvider dataProvider = new SensorFlightDataProvider(flights);
        DataSet trainingSet = dataProvider.getSensorsDataSet();

        if (trainingSet.size() > 0) {
            NeuralNetwork neuralNetwork = new MultiLayerPerceptron(13, 13, 2);
            final SupervisedLearning learningRule = new BackPropagation();
            learningRule.setNeuralNetwork(neuralNetwork);
            learningRule.setMaxIterations(1000);
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

    private static void testNeuralNetwork(SensorFlightDataProvider dataProvider, NeuralNetwork neuralNetwork) {
        DataSet testSet = dataProvider.getCrossValidationDataSet();
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
