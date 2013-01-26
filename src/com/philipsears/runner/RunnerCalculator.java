package com.philipsears.runner;

/**
 * User: phils
 * Date: 9/22/12
 *
 * The runner calculator contains all of the logic and functions for calculating pace/time/etc
 * This should be de-coupled from the android app.
 */
public class RunnerCalculator {
    protected TimeData time;
    protected TimeData pace;
    protected double distance;
    protected boolean useMiles;

    public static final double KILOMETERS_IN_MILE = 1.60934;

    public RunnerCalculator(TimeData time, TimeData pace, double distance, boolean useMiles) {
        this.time = time;
        this.pace = pace;
        this.distance = distance;
        this.useMiles = useMiles;
    }
    
    public TimeData calculatePace() {
        // always convert distance to miles
        double mileDistance = distance;
        if (!useMiles) {
            mileDistance = distance / KILOMETERS_IN_MILE;
        }
        // pace is calculated as distance in miles per minutes
       double paceInMinutes = time.getTimeInMinutes() / mileDistance;
       return new TimeData(paceInMinutes * 60.0);
    }
    
    public TimeData calculateTime() {
        // always convert distance to miles
        double mileDistance = distance;
        if (!useMiles) {
            mileDistance = distance / KILOMETERS_IN_MILE;
        }
        // time is calculated by pace multiplied by distance
        double timeInSeconds = pace.getTimeInSeconds() * mileDistance;
        return new TimeData(timeInSeconds);
    }
    
    public double calculateDistance() {
        // mile distance is calculated by pace multiplied by time
        double calcDistance =  time.getTimeInMinutes() / pace.getTimeInMinutes();
        if (!useMiles)  {
            calcDistance = calcDistance * KILOMETERS_IN_MILE;
        }
        return calcDistance;
    }

}

