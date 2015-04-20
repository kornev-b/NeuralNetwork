package me.neuralnetwork.perceptron.flightdelay.model;

/**
 * Created by Bogdan Kornev
 * on 13.04.2015, 22:32.
 */
public class Flight {
    private String id;
    private String flightId;
    private String aircraftId;
    // Filed flight duration
    private String filedEte;
    // Filed time of departure
    private long outTime;
    // Filed speed (in miles, 1 mile = 1.825 km)
    private int filedAirspeedKts;
    // Actual time of departure
    private long actualOutTime;
    // Filed arrival time
    private long inTime;
    // Actual arrival time
    private long actualInTime;
    // Departure airport code
    private String outAirportCode;
    // Arrival airport code
    private String inAirportCode;
    // Departure airport
    private String outAirport;
    // Departure city
    private String outCity;
    // Arrival airport
    private String inAirport;
    // Arrival city
    private String inCity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getFiledEte() {
        return filedEte;
    }

    public void setFiledEte(String filedEte) {
        this.filedEte = filedEte;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public int getFiledAirspeedKts() {
        return filedAirspeedKts;
    }

    public void setFiledAirspeedKts(int filedAirspeedKts) {
        this.filedAirspeedKts = filedAirspeedKts;
    }

    public long getActualOutTime() {
        return actualOutTime;
    }

    public void setActualOutTime(long actualOutTime) {
        this.actualOutTime = actualOutTime;
    }

    public long getInTime() {
        return inTime;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    public long getActualInTime() {
        return actualInTime;
    }

    public void setActualInTime(long actualInTime) {
        this.actualInTime = actualInTime;
    }

    public String getOutAirportCode() {
        return outAirportCode;
    }

    public void setOutAirportCode(String outAirportCode) {
        this.outAirportCode = outAirportCode;
    }

    public String getInAirportCode() {
        return inAirportCode;
    }

    public void setInAirportCode(String inAirportCode) {
        this.inAirportCode = inAirportCode;
    }

    public String getOutAirport() {
        return outAirport;
    }

    public void setOutAirport(String outAirport) {
        this.outAirport = outAirport;
    }

    public String getOutCity() {
        return outCity;
    }

    public void setOutCity(String outCity) {
        this.outCity = outCity;
    }

    public String getInAirport() {
        return inAirport;
    }

    public void setInAirport(String inAirport) {
        this.inAirport = inAirport;
    }

    public String getInCity() {
        return inCity;
    }

    public void setInCity(String inCity) {
        this.inCity = inCity;
    }
}
