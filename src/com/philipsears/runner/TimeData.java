package com.philipsears.runner;

import java.io.Serializable;

/**
 * User: phils
 * Date: 9/22/12
 */
public class TimeData implements Serializable {
    
    int hours = 0;
    int minutes = 0;
    double seconds = 0.0;

    public TimeData(double inputSeconds) {
        hours = new Double(inputSeconds).intValue() / 3600;
        double remainingSeconds = inputSeconds % 3600;
        minutes = new Double(remainingSeconds).intValue() / 60;
        seconds = remainingSeconds % 60.0;
    }
    
    public TimeData(int hours, int minutes, double seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public double getTimeInSeconds() {
        double timeInSeconds = hours * 60 * 60;
        timeInSeconds += minutes * 60;
        timeInSeconds += seconds;
        return timeInSeconds;
    }

    public double getTimeInMinutes() {
        double timeInMinutes = hours * 60;
        timeInMinutes += minutes;
        timeInMinutes += seconds / 60.0;
        return timeInMinutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }
}
