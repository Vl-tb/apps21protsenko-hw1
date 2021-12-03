package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int min_temp = -273;
    private int max_temp = 1000;

    public TemperatureSeriesAnalysis() {

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.temperatureSeries = Arrays.copyOf(temperatureSeries,
                temperatureSeries.length);
    }

    public double average() throws IllegalArgumentException {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double sum = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            sum += temperatureSeries[i];
        }
        return sum/temperatureSeries.length;
    }

    public double deviation() throws IllegalArgumentException {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double mean = average();
        double variance = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            variance += (temperatureSeries[i]-mean)*(temperatureSeries[i]-mean);
        }
        return Math.sqrt(variance);
    }

    public double min() {
        return findTempClosestToValue(min_temp);
    }

    public double max() {
        return findTempClosestToValue(max_temp);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue)
            throws IllegalArgumentException {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double diff = Double.POSITIVE_INFINITY;
        double closestTemp = 0;
        for (double temp : temperatureSeries) {
            if (Math.abs(tempValue - temp) < diff) {
                closestTemp = temp;
                diff = Math.abs(tempValue - temp);
            }
        }
        if (closestTemp < 0 && tempValue != min_temp) {
            for (int i = 0; i < temperatureSeries.length; i++) {
                if ((Math.abs(tempValue - temperatureSeries[i]) == diff)
                        && (temperatureSeries[i] < 0)) {
                    return temperatureSeries[i];
                }
            }
        }
        return closestTemp;
    }


    public double[] findTemps(double tempValue, int param)
            throws IllegalArgumentException {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        boolean condition;
        int counter = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (param == 0) {
                condition = temperatureSeries[i] < tempValue;
            }
            else {
                condition = temperatureSeries[i] >= tempValue;
            }
            if (condition) {
                counter++;
            }
        }
        double[] less = new double[counter];
        counter = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (param == 0) {
                condition = temperatureSeries[i] < tempValue;
            }
            else {
                condition = temperatureSeries[i] >= tempValue;
            }
            if (condition) {
                less[counter] = temperatureSeries[i];
                counter++;
            }
        }
        return less;
    }

    public double[] findTempsLessThen(double tempValue) {
        return findTemps(tempValue, 0);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        return findTemps(tempValue, 1);
    }

    public TempSummaryStatistics summaryStatistics()
            throws IllegalArgumentException {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double avg = this.average();
        double dev = this.deviation();
        double min = this.min();
        double max = this.max();

        return new TempSummaryStatistics(avg, dev, min, max);
    }

    public int addTemps(double... temps) throws InputMismatchException {
        int newLen = temperatureSeries.length+temps.length;
        double[] newTemperatureSeries = new double[newLen];
        for (int i = 0; i < temperatureSeries.length; i++) {
            newTemperatureSeries[i] = temperatureSeries[i];
        }
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] < min_temp) {
                throw new InputMismatchException();
            }
            newTemperatureSeries[temperatureSeries.length+i] = temps[i];
        }
        this.temperatureSeries = newTemperatureSeries;
        return temperatureSeries.length;
    }
}
