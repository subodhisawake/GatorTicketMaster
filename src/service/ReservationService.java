package service;

import data.RBTree;
import data.MinHeap;
import models.Event;
import models.User;
import models.Seat;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private Event event;
    private RBTree<Integer, Seat> reservedSeats;
    private MinHeap<User> waitlist;

    public ReservationService() {
        this.reservedSeats = new RBTree<>();
        this.waitlist = new MinHeap<>();
    }

    public void initialize(int seatCount) {
        this.event = new Event(seatCount);
        System.out.println(seatCount + " seats successfully initialized");
    }

    public void available() {
        int availableSeats = event.getAvailableSeatCount();
        int waitlistLength = waitlist.size();
        System.out.println("Total Seats Available : " + availableSeats + ", Waitlist : " + waitlistLength);
    }

    public void reserve(int userID, int userPriority) {
        int seatID = event.getAvailableSeat();
        if (seatID != -1) {
            Seat seat = new Seat(seatID, userID);
            reservedSeats.insert(userID, seat);
            System.out.println("User " + userID + " reserved seat " + seatID);
        } else {
            User user = new User(userID, userPriority);
            waitlist.insert(user);
            System.out.println("User " + userID + " is added to the waiting list");
        }
    }

    public void cancel(int seatID, int userID) {
        Seat seat = reservedSeats.search(userID);
        if (seat != null && seat.getSeatID() == seatID) {
            reservedSeats.delete(userID);
            System.out.println("User " + userID + " canceled their reservation");
            if (!waitlist.isEmpty()) {
                User nextUser = waitlist.extractTop();
                Seat newSeat = new Seat(seatID, nextUser.getUserID());
                reservedSeats.insert(nextUser.getUserID(), newSeat);
                System.out.println("User " + nextUser.getUserID() + " reserved seat " + seatID);
            } else {
                event.returnSeat(seatID);
            }
        } else {
            System.out.println("User " + userID + " has no reservation for seat " + seatID + " to cancel");
        }
    }

    public void exitWaitlist(int userID) {
        User user = new User(userID, 0);  // Create a dummy user to search by ID
        waitlist.remove(user);  // Remove user from MinHeap
        System.out.println("User " + userID + " removed from waitlist");
    }

    public void updatePriority(int userID, int newPriority) {
        User oldUser = new User(userID, 0);
        User newUser = new User(userID, newPriority);
        waitlist.update(oldUser, newUser);
        System.out.println("User " + userID + " priority has been updated to " + newPriority);
    }

    public void addSeats(int count) {
        event.addSeats(count); // Add new seats to Event
        System.out.println(count + " seats added.");

        // Assign newly added seats to users on the waitlist if applicable
        while (!waitlist.isEmpty() && event.getAvailableSeatCount() > 0) {
            User nextUser = waitlist.extractTop();
            int seatToAssign = event.getAvailableSeat();
            Seat newSeat = new Seat(seatToAssign, nextUser.getUserID());
            reservedSeats.insert(nextUser.getUserID(), newSeat);
            System.out.println("Seat " + seatToAssign + " assigned to user " + nextUser.getUserID() + " from waitlist");
        }
    }


    public void printReservations() {
        // This method requires an in-order traversal of the RBTree
        // For now, we'll just print a placeholder message
        System.out.println("Reservations:");
        reservedSeats.printInOrder();
    }


    public void releaseSeats(int userID1, int userID2) {
        System.out.println("Reservations of the Users in the range [" + userID1 + ", " + userID2 + "] are released");
        for (int userID = userID1; userID <= userID2; userID++) {
            Seat seat = reservedSeats.search(userID);
            if (seat != null) {
                int seatID = seat.getSeatID();
                reservedSeats.delete(userID);
                event.returnSeat(seatID);
                System.out.println("User " + userID + "'s reservation for seat " + seatID + " is released");
            } else {
                removeFromWaitlist(userID);
            }
        }

        // Assign released seats to users on the waitlist
        while (!waitlist.isEmpty() && event.getAvailableSeatCount() > 0) {
            User nextUser = waitlist.extractTop();
            int seatToAssign = event.getAvailableSeat();
            Seat newSeat = new Seat(seatToAssign, nextUser.getUserID());
            reservedSeats.insert(nextUser.getUserID(), newSeat);
            System.out.println("User " + nextUser.getUserID() + " reserved seat " + seatToAssign + " from waitlist");
        }
    }

//    private void removeFromWaitlist(int userID) {
//        List<User> tempList = new ArrayList<>();
//        boolean removed = false;
//        while (!waitlist.isEmpty()) {
//            User user = waitlist.extractTop();
//            if (user.getUserID() == userID) {
//                removed = true;
//                System.out.println("User " + userID + " removed from waitlist");
//                break;
//            }
//            tempList.add(user);
//        }
//        // Re-insert users that were not removed
//        for (User user : tempList) {
//            waitlist.insert(user);
//        }
//        if (!removed) {
//            System.out.println("User " + userID + " not found in waitlist");
//        }
//    }

    private void removeFromWaitlist(int userID) {
        User user = new User(userID, 0);
        waitlist.remove(user);
    }
}