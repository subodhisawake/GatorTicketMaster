package controller;

import service.ReservationService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GatorTicketMaster {
    private ReservationService reservationService;

    public GatorTicketMaster() {
        this.reservationService = new ReservationService();
    }

    public void processInputFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processCommand(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    private void processCommand(String command) {
        // Normalize command by converting it to lowercase and removing parentheses, commas, and extra spaces
        command = command.toLowerCase().replaceAll("[(),]", " ").trim().replaceAll("\\s+", " ");
        String[] parts = command.split(" ");

        switch (parts[0]) {
            case "initialize":
                if (parts.length == 2) {
                    reservationService.initialize(Integer.parseInt(parts[1]));
                }
                break;
            case "available":
                reservationService.available();
                break;
            case "reserve":
                if (parts.length == 3) {
                    reservationService.reserve(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
                break;
            case "cancel":
                if (parts.length == 3) {
                    reservationService.cancel(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
                break;
            case "exitwaitlist":
                if (parts.length == 2) {
                    reservationService.exitWaitlist(Integer.parseInt(parts[1]));
                }
                break;
            case "updatepriority":
                if (parts.length == 3) {
                    reservationService.updatePriority(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
                break;
            case "addseats":
                if (parts.length == 2) {
                    reservationService.addSeats(Integer.parseInt(parts[1]));
                }
                break;
            case "printreservations":
                reservationService.printReservations();
                break;
            case "releaseseats":
                if (parts.length == 3) {
                    reservationService.releaseSeats(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
                break;
            case "quit":
                System.out.println("Exiting the program.");
                System.exit(0);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

}