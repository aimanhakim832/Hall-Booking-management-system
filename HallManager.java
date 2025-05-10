/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group.oodjAssignment.scheduler;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class HallManager {

    private List<Hall> halls; // List to store hall information
    private int hallCounter; // Counter to generate hallID
    private static final String FILE_NAME = "halls.txt"; // File to store hall data

    // Constructor
    public HallManager() {
        this.halls = new ArrayList<>();
        this.hallCounter = 1; // Start with ID H001
        loadHallsFromFile(); // Load data from file at initialization
    }

    // Method to generate a new hallID
    private String generateHallID() {
        return String.format("H%03d", hallCounter++);
    }

    // Method to add a new hall
    public void addHall(Hall hall) {
        hall.setHallID(generateHallID()); // Set hallID automatically
        halls.add(hall);
        saveHallsToFile(); // Save to file after adding
    }

    // Method to edit an existing hall
    public boolean editHall(Hall updatedHall) {
        for (int i = 0; i < halls.size(); i++) {
            Hall existingHall = halls.get(i);
            if (existingHall.getHallID().equals(updatedHall.getHallID())) {
                if (updatedHall.getAvailabilitySchedules() == null) {
                    updatedHall.setAvailabilitySchedules(
                            existingHall.getAvailabilitySchedules()
                    );
                }

                if (updatedHall.getMaintenanceSchedules() == null) {
                    updatedHall.setMaintenanceSchedules(
                            existingHall.getMaintenanceSchedules()
                    );
                }

                halls.set(i, updatedHall);
                saveHallsToFile(); // Save to file after editing
                return true; // Hall updated successfully
            }
        }
        return false; // Hall not found
    }

    // Method to delete a hall by hallID
    public boolean deleteHall(String hallID) {
        boolean removed = halls.removeIf(hall -> hall.getHallID().equals(hallID));
        if (removed) {
            saveHallsToFile(); // Save to file after deleting
        }
        return removed;
    }

    // Method to find a hall by hallID
    public Hall findHallByID(String hallID) {
        Optional<Hall> hall = halls
                .stream()
                .filter(h -> h.getHallID().equals(hallID))
                .findFirst();
        return hall.orElse(null);
    }

    // Method to get all halls
    public List<Hall> getAllHalls() {
        return new ArrayList<>(halls);
    }

    // Save halls to a file
    private void saveHallsToFile() {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Hall hall : halls) {
                writer.write(hallToString(hall));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load halls from a file
    private void loadHallsFromFile() {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Hall hall = stringToHall(line);
                if (hall != null) {
                    halls.add(hall);
                }
            }
            if (!halls.isEmpty()) {
                hallCounter
                        = Integer.parseInt(
                                halls.get(halls.size() - 1).getHallID().substring(1)
                        )
                        + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert Hall object to a string for file storage
    private String hallToString(Hall hall) {
        StringBuilder sb = new StringBuilder();
        sb.append(hall.getHallID()).append(",");
        sb.append(hall.getHallName()).append(",");
        sb.append(hall.getHallType()).append(",");
        sb.append(hall.getCapacity()).append(",");
        sb.append(hall.getBookingRate()).append(",");
        sb.append(hall.isActive());

        // Serialize availability schedules
        for (AvailabilitySchedule schedule : hall.getAvailabilitySchedules()) {
            sb
                    .append(",")
                    .append(schedule.getStart())
                    .append("~")
                    .append(schedule.getEnd())
                    .append("~")
                    .append(schedule.getRemarks());
        }

        // Serialize maintenance schedules
        for (MaintenanceSchedule maintenance : hall.getMaintenanceSchedules()) {
            sb
                    .append(",")
                    .append(maintenance.getStart())
                    .append("~")
                    .append(maintenance.getEnd())
                    .append("~")
                    .append(maintenance.getRemarks());
        }

        return sb.toString();
    }

    // Convert a string to a Hall object
    private Hall stringToHall(String str) {
        String[] parts = str.split(",");
        if (parts.length < 6) {
            return null; // Basic validation
        }
        String hallID = parts[0];
        String hallName = parts[1];
        String hallType = parts[2];
        int capacity = Integer.parseInt(parts[3]);
        double bookingRate = Double.parseDouble(parts[4]);
        boolean isActive = Boolean.parseBoolean(parts[5]);

        List<AvailabilitySchedule> availabilitySchedules = new ArrayList<>();
        List<MaintenanceSchedule> maintenanceSchedules = new ArrayList<>();

        // Parse schedules
        for (int i = 6; i < parts.length; i++) {
            String[] scheduleParts = parts[i].split("~");
            if (scheduleParts.length == 3) {
                // Check if it's an availability or maintenance schedule based on context
                if (scheduleParts[0].contains("T")) {
                    availabilitySchedules.add(
                            new AvailabilitySchedule(
                                    LocalDateTime.parse(scheduleParts[0]),
                                    LocalDateTime.parse(scheduleParts[1]),
                                    scheduleParts[2]
                            )
                    );
                } else {
                    maintenanceSchedules.add(
                            new MaintenanceSchedule(
                                    LocalDateTime.parse(scheduleParts[0]),
                                    LocalDateTime.parse(scheduleParts[1]),
                                    scheduleParts[2]
                            )
                    );
                }
            }
        }

        return new Hall(
                hallID,
                hallName,
                hallType,
                capacity,
                bookingRate,
                availabilitySchedules,
                maintenanceSchedules,
                isActive
        );
    }

    //   to add availablity schedule to the hall
    public boolean addAvailabilitySchedule(
            String hallID,
            AvailabilitySchedule schedule
    ) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            hall.getAvailabilitySchedules().add(schedule);
            saveHallsToFile(); // Save to file after adding
            return true;
        }
        return false;
    }

    //   to add maintenance schedule to the hall
    public boolean addMaintenanceSchedule(
            String hallID,
            MaintenanceSchedule schedule
    ) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            hall.getMaintenanceSchedules().add(schedule);
            saveHallsToFile(); // Save to file after adding
            return true;
        }
        return false;
    }

    //   to remove availability schedule from the hall
    public boolean removeAvailabilitySchedule(
            String hallID,
            AvailabilitySchedule schedule
    ) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            for (int i = 0; i < hall.getAvailabilitySchedules().size(); i++) {
                AvailabilitySchedule existingSchedule = hall
                        .getAvailabilitySchedules()
                        .get(i);
                if (existingSchedule.getStart().equals(schedule.getStart())
                        && existingSchedule.getEnd().equals(schedule.getEnd())
                        && existingSchedule.getRemarks().equals(schedule.getRemarks())) {
                    hall.getAvailabilitySchedules().remove(i);
                    break;
                }
            }

            for (int i = 0; i < halls.size(); i++) {
                if (halls.get(i).getHallID().equals(hallID)) {
                    halls.set(i, hall);
                    break;
                }
            }
            saveHallsToFile(); // Save to file after removing
            return true;
        }
        return false;
    }

    //   to remove maintenance schedule from the hall
    public boolean removeMaintenanceSchedule(
            String hallID,
            MaintenanceSchedule schedule
    ) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            for (int i = 0; i < hall.getMaintenanceSchedules().size(); i++) {
                MaintenanceSchedule existingSchedule = hall
                        .getMaintenanceSchedules()
                        .get(i);
                if (existingSchedule.getStart().equals(schedule.getStart())
                        && existingSchedule.getEnd().equals(schedule.getEnd())
                        && existingSchedule.getRemarks().equals(schedule.getRemarks())) {
                    hall.getMaintenanceSchedules().remove(i);
                    break;
                }
            }

            for (int i = 0; i < halls.size(); i++) {
                if (halls.get(i).getHallID().equals(hallID)) {
                    halls.set(i, hall);
                    break;
                }
            }
            saveHallsToFile(); // Save to file after removing
            return true;
        }
        return false;
    }

    //   to get all availability schedules of the hall
    public List<AvailabilitySchedule> getAvailabilitySchedules(String hallID) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            return hall.getAvailabilitySchedules();
        }
        return new ArrayList<>();
    }

    //   to get all maintenance schedules of the hall
    public List<MaintenanceSchedule> getMaintenanceSchedules(String hallID) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            return hall.getMaintenanceSchedules();
        }
        return new ArrayList<>();
    }

    //   editAvailabilitySchedule
    public boolean editAvailabilitySchedule(
            String hallID,
            AvailabilitySchedule updatedSchedule,
            AvailabilitySchedule oldSchedule
    ) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            for (int i = 0; i < hall.getAvailabilitySchedules().size(); i++) {
                AvailabilitySchedule existingSchedule = hall
                        .getAvailabilitySchedules()
                        .get(i);
                if (existingSchedule.getStart().equals(oldSchedule.getStart())
                        && existingSchedule.getEnd().equals(oldSchedule.getEnd())
                        && existingSchedule.getRemarks().equals(oldSchedule.getRemarks())) {
                    hall.getAvailabilitySchedules().set(i, updatedSchedule);
                    saveHallsToFile(); // Save to file after editing
                    return true;
                }
            }
        }
        return false;
    }

    //   editMaintenanceSchedule
    public boolean editMaintenanceSchedule(
            String hallID,
            MaintenanceSchedule updatedSchedule,
            MaintenanceSchedule oldSchedule
    ) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            for (int i = 0; i < hall.getMaintenanceSchedules().size(); i++) {
                MaintenanceSchedule existingSchedule = hall
                        .getMaintenanceSchedules()
                        .get(i);
                if (existingSchedule.getStart().equals(oldSchedule.getStart())
                        && existingSchedule.getEnd().equals(oldSchedule.getEnd())
                        && existingSchedule.getRemarks().equals(oldSchedule.getRemarks())) {
                    hall.getMaintenanceSchedules().set(i, updatedSchedule);
                    saveHallsToFile(); // Save to file after editing
                    return true;
                }
            }
        }
        return false;
    }
}
