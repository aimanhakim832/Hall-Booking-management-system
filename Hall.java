/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group.oodjAssignment.scheduler;

/**
 *
 * @author Saadin
 */
import java.util.List;

public class Hall {

    private String hallID; // Unique identifier for the hall
    private String hallName; // Name of the hall
    private String hallType; // Type of hall (e.g., Auditorium, Banquet Hall, Meeting Room)
    private int capacity; // Seating capacity
    private double bookingRate; // Rate per hour for booking
    private List<AvailabilitySchedule> availabilitySchedules; // List of availability schedules
    private List<MaintenanceSchedule> maintenanceSchedules; // List of maintenance schedules
    private boolean isActive; // Indicates if the hall is active or not

    // Constructor
    public Hall(String hallID, String hallName, String hallType, int capacity, double bookingRate,
            List<AvailabilitySchedule> availabilitySchedules,
            List<MaintenanceSchedule> maintenanceSchedules, boolean isActive) {
        this.hallID = hallID;
        this.hallName = hallName;
        this.hallType = hallType;
        this.capacity = capacity;
        this.bookingRate = bookingRate;
        this.availabilitySchedules = availabilitySchedules;
        this.maintenanceSchedules = maintenanceSchedules;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getHallID() {
        return hallID;
    }

    public void setHallID(String hallID) {
        this.hallID = hallID;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getBookingRate() {
        return bookingRate;
    }

    public void setBookingRate(double bookingRate) {
        this.bookingRate = bookingRate;
    }

    public List<AvailabilitySchedule> getAvailabilitySchedules() {
        return availabilitySchedules;
    }

    public void setAvailabilitySchedules(List<AvailabilitySchedule> availabilitySchedules) {
        this.availabilitySchedules = availabilitySchedules;
    }

    public List<MaintenanceSchedule> getMaintenanceSchedules() {
        return maintenanceSchedules;
    }

    public void setMaintenanceSchedules(List<MaintenanceSchedule> maintenanceSchedules) {
        this.maintenanceSchedules = maintenanceSchedules;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
