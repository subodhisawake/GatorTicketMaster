package models;

import data.MinHeap;

public class Event {
    private int seatCount;
    private MinHeap<Integer> availableSeats;

    public Event(int seatCount) {
        this.seatCount = seatCount;
        this.availableSeats = new MinHeap<>();
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 1; i <= seatCount; i++) {
            availableSeats.insert(i);
        }
    }

    public int getAvailableSeat() {
        if (!availableSeats.isEmpty()) {
            return availableSeats.extractTop();
        }
        return -1; // No seats available
    }

    public void addSeats(int count) {
        for (int i = seatCount + 1; i <= seatCount + count; i++) {
            availableSeats.insert(i);
        }
        seatCount += count;
    }

    public void returnSeat(int seatNumber) {
        availableSeats.insert(seatNumber);
    }

    public int getAvailableSeatCount() {
        return availableSeats.size();
    }

    public int getTotalSeatCount() {
        return seatCount;
    }
}