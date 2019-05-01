package com.skywatch.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Entity
public class Crash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Airline rating > 5 out of 10
     */
    @Column(name = "rating")
    @NotNull
    private boolean rating;

    /**
     * Model age < 10 years
     */
    @Column(name = "model_age")
    @NotNull
    private boolean modelAge;

    /**
     * Aircraft first flight < 10 years
     */
    @Column(name = "first_flight")
    @NotNull
    private boolean firstFlight;

    /**
     * Pilot age > 40 years
     */
    @Column(name = "pilot_age")
    @NotNull
    private boolean pilotAge;

    /**
     * Good weather
     */
    @Column(name = "weather")
    @NotNull
    private boolean weather;

    /**
     * Crashed
     */
    @Column(name = "crashed")
    @NotNull
    private boolean crashed;

    /**
     * Mandatory empty argument constructor
     */
    public Crash() {}

    public Crash(boolean rating, boolean modelAge, boolean firstFlight, boolean pilotAge, boolean weather, boolean crashed) {
        this.rating = rating;
        this.modelAge = modelAge;
        this.firstFlight = firstFlight;
        this.pilotAge = pilotAge;
        this.weather = weather;
        this.crashed = crashed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRating() {
        return rating;
    }

    public void setRating(boolean rating) {
        this.rating = rating;
    }

    public boolean isModelAge() {
        return modelAge;
    }

    public void setModelAge(boolean modelAge) {
        this.modelAge = modelAge;
    }

    public boolean isFirstFlight() {
        return firstFlight;
    }

    public void setFirstFlight(boolean firstFlight) {
        this.firstFlight = firstFlight;
    }

    public boolean isPilotAge() {
        return pilotAge;
    }

    public void setPilotAge(boolean pilotAge) {
        this.pilotAge = pilotAge;
    }

    public boolean isWeather() {
        return weather;
    }

    public void setWeather(boolean weather) {
        this.weather = weather;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean getBoolean(String attribute) {
        switch (attribute) {
            case "rating":
                return rating;
            case "modelAge":
                return modelAge;
            case "firstFlight":
                return firstFlight;
            case "pilotAge":
                return pilotAge;
            case "weather":
                return weather;
            case "crashed":
                return crashed;
            default:
                throw new IllegalArgumentException();
        }
    }
}
