package ua.edu.ucu.tempseries;

import java.util.Arrays;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;

    public TemperatureSeriesAnalysis() {

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
    }

    public double average() throws IllegalArgumentException{
        double sum = 0;
        for (int i=0; i<temperatureSeries.length; i++){
            sum += temperatureSeries[i];
        }
        return sum/temperatureSeries.length;
    }

    public double deviation() throws IllegalArgumentException{
        double mean = average();
        double variance = 0;
        for (int i=0; i<temperatureSeries.length; i++){
            variance += Math.pow(temperatureSeries[i]-mean, 2);
        }
        return Math.sqrt(variance);
    }

    public double min() throws IllegalArgumentException{
        return findTempClosestToValue(-1000);
    }

    public double max() throws IllegalArgumentException{
        return findTempClosestToValue(Double.POSITIVE_INFINITY);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) throws IllegalArgumentException{
        double diff = Double.POSITIVE_INFINITY;
        double closestTemp = 0;
        for (double temp: temperatureSeries){
            if (Math.abs(temp - tempValue) < diff){
                closestTemp = temp;
                diff = Math.abs(temp - tempValue);
            }
        }
        return closestTemp;
    }

    public double[] findTempsLessThen(double tempValue) {
        return null;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        return null;
    }

    public TempSummaryStatistics summaryStatistics() {
        return null;
    }

    public int addTemps(double... temps) {
        return 0;
    }
}
