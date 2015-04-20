package me.neuralnetwork.perceptron.flightdelay;

import me.neuralnetwork.core.data.DataSet;
import me.neuralnetwork.core.data.DataSetRow;
import me.neuralnetwork.perceptron.SensorDataProvider;
import me.neuralnetwork.perceptron.flightdelay.model.Flight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 13.04.2015, 22:31.
 */
public class SensorFlightDataProvider implements SensorDataProvider {
    private List<Flight> flights;
    private int dataSetSize;
    private int crossValidationSetSize;
    private int testSetSize;

    public SensorFlightDataProvider(List<Flight> flights) {
        this.flights = flights;
        dataSetSize = (int) (flights.size() * DATA_SET_PERCENT);
        crossValidationSetSize = (int) (flights.size() * CROSS_VALIDATION_SET_PERCENT);
        testSetSize = (int) (flights.size() * TEST_SET_PERCENT);
    }

    @Override
    public DataSet getSensorsDataSet() {
        DataSet dataSet = new DataSet(13, 2);


        for (int i = 0; i < dataSetSize; i++) {
            Flight flight = flights.get(i);

            DataSetRow row = new DataSetRow();

            double[] input = getInput(flight);
            double[] output = getOutput(flight);

            row.setInput(input);
            row.setDesiredOutput(output);

            dataSet.addRow(row);
        }

        return dataSet;
    }

    private double[] getOutput(Flight flight) {
        double[] output = new double[2];
        output[0] = new Long(flight.getActualOutTime()).doubleValue();
        output[1] = flight.getActualInTime();
        return output;
    }

    private double[] getInput(Flight flight) {
        double[] input = new double[13];

        input[0] = flight.getId().hashCode();
        input[1] = flight.getFlightId().hashCode();
        input[2] = flight.getAircraftId().hashCode();
        input[3] = flight.getFiledEte().hashCode();
        input[4] = flight.getOutTime();
        input[5] = flight.getFiledAirspeedKts();
//        input[6] = flight.getActualOutTime();
        input[6] = flight.getInTime();
//        input[8] = flight.getActualInTime();
        input[7] = flight.getOutAirportCode().hashCode();
        input[8] = flight.getInAirportCode().hashCode();
        input[9] = flight.getOutAirport().hashCode();
        input[10] = flight.getOutCity().hashCode();
        input[11] = flight.getInAirport().hashCode();
        input[12] = flight.getInCity().hashCode();

        return input;
    }

    @Override
    public DataSet getCrossValidationDataSet() {
        DataSet dataSet = new DataSet(13, 2);


        for (int i = dataSetSize; i < dataSetSize + 1 + crossValidationSetSize; i++) {
            Flight flight = flights.get(i);

            DataSetRow row = new DataSetRow();

            double[] input = getInput(flight);
            double[] output = getOutput(flight);

            row.setInput(input);
            row.setDesiredOutput(output);

            dataSet.addRow(row);
        }

        return dataSet;
    }

    @Override
    public DataSet getTestDataSet() {
        DataSet dataSet = new DataSet(13, 2);


        for (int i = flights.size() - testSetSize; i < flights.size(); i++) {
            Flight flight = flights.get(i);

            DataSetRow row = new DataSetRow();

            double[] input = getInput(flight);
            double[] output = getOutput(flight);

            row.setInput(input);
            row.setDesiredOutput(output);

            dataSet.addRow(row);
        }

        return dataSet;
    }
}
