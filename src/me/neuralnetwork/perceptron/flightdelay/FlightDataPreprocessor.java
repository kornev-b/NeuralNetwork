package me.neuralnetwork.perceptron.flightdelay;

import me.neuralnetwork.core.exception.NeuroException;
import me.neuralnetwork.perceptron.flightdelay.model.Flight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 13.04.2015, 22:56.
 */
public class FlightDataPreprocessor {
    public static List<Flight> getFlightsFromCSVFile(String filename) {
        List<Flight> flights = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Flight flight = new Flight();
                flight.setId(values[0]);
                flight.setFlightId(values[1]);
                flight.setAircraftId(values[2]);
                flight.setFiledEte(values[3]);
                flight.setOutTime(Long.parseLong(values[4]) * 1000);
                flight.setFiledAirspeedKts(Integer.parseInt(values[5]));
                flight.setActualOutTime(Long.parseLong(values[6]) * 1000);
                flight.setInTime(Long.parseLong(values[7]) * 1000);
                flight.setActualInTime(Long.parseLong(values[8]) * 1000);
                flight.setOutAirportCode(values[9]);
                flight.setInAirportCode(values[10]);
                flight.setOutAirport(values[11]);
                flight.setOutCity(values[12]);
                flight.setInAirport(values[13]);
                flight.setInCity(values[14]);

                flights.add(flight);
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }

        return flights;
    }
}
