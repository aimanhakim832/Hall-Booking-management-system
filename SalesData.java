package com.group.oodjAssignment.manager;

//SalesData.java
public class SalesData {

    private String timePeriod;
    private double totalSales;

    public SalesData(String timePeriod, double totalSales) {
        this.timePeriod = timePeriod;
        this.totalSales = totalSales;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public double getTotalSales() {
        return totalSales;
    }
}
