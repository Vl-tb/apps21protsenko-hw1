package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

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

    public double min() {
        return findTempClosestToValue(-273);
    }

    public double max() {
        return findTempClosestToValue(1000);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) throws IllegalArgumentException{
        double diff = Double.POSITIVE_INFINITY;
        double closestTemp = 0;
        for (double temp : temperatureSeries) {
            if (Math.abs(tempValue - temp) < diff) {
                closestTemp = temp;
                diff = Math.abs(tempValue - temp);
            }
        }
        if (closestTemp < 0) {
            for (int i = 0; i < temperatureSeries.length; i++) {
                if (temperatureSeries[i] == -1 * closestTemp) {
                    return -1 * closestTemp;
                }
            }
        }
        return closestTemp;
    }


    public double[] findTemps(double tempValue, int param) throws IllegalArgumentException{
        boolean condition;
        int counter = 0;
        for (int i=0; i<temperatureSeries.length; i++){
            if (param == 0){
                condition = temperatureSeries[i] < tempValue;
            }
            else{
                condition = temperatureSeries[i] >= tempValue;
            }
            if (condition){
                counter++;
            }
        }
        double[] less = new double[counter];
        counter = 0;
        for (int i=0; i<temperatureSeries.length; i++){
            if (param == 0){
                condition = temperatureSeries[i] < tempValue;
            }
            else{
                condition = temperatureSeries[i] >= tempValue;
            }
            if (condition){
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

    public TempSummaryStatistics summaryStatistics() throws IllegalArgumentException{
        double avg = this.average();
        double dev = this.deviation();
        double min = this.min();
        double max = this.max();

        return new TempSummaryStatistics(avg, dev, min, max);
    }

    public int addTemps(double... temps) throws InputMismatchException {
        int newLen = temperatureSeries.length+temps.length;
        double[] newTemperatureSeries = new double[newLen];
        for (int i=0; i<temperatureSeries.length; i++){
            newTemperatureSeries[i] = temperatureSeries[i];
        }
        for (int i=0; i<temps.length; i++){
            if (temps[i] < -273){
                return temperatureSeries.length;
            }
            newTemperatureSeries[temperatureSeries.length+i] = temps[i];
        }
        this.temperatureSeries = newTemperatureSeries;
        return temperatureSeries.length;
    }

    public static void main(String[] args) {
        double[] arr= {-34,9,5,1.5,-1.5,-8,-7};
        TemperatureSeriesAnalysis an = new TemperatureSeriesAnalysis(arr);
        System.out.println(an.summaryStatistics());
        System.out.print(Arrays.toString(an.findTempsGreaterThen(0)));
        System.out.println(an.addTemps(2,4,8.9));
        System.out.println(Arrays.toString(an.temperatureSeries));
    }
}
