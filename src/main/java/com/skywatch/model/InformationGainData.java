package com.skywatch.model;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
public class InformationGainData {

    @Id
    private long id;

    @Column(name = "rating")
    private double rating;

    @Column(name = "model_age")
    private double modelAge;

    @Column(name = "first_flight")
    private double firstFlight;

    @Column(name = "pilot_age")
    private double pilotAge;

    @Column(name = "weather")
    private double weather;

    public InformationGainData() {}

    public InformationGainData(double[] data) {
        this.id = 1L;
        this.rating = data[0];
        this.modelAge = data[1];
        this.firstFlight = data[2];
        this.pilotAge = data[3];
        this.weather = data[4];
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getModelAge() {
        return modelAge;
    }

    public void setModelAge(double modelAge) {
        this.modelAge = modelAge;
    }

    public double getFirstFlight() {
        return firstFlight;
    }

    public void setFirstFlight(double firstFlight) {
        this.firstFlight = firstFlight;
    }

    public double getPilotAge() {
        return pilotAge;
    }

    public void setPilotAge(double pilotAge) {
        this.pilotAge = pilotAge;
    }

    public double getWeather() {
        return weather;
    }

    public void setWeather(double weather) {
        this.weather = weather;
    }
}
