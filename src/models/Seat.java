package models;

public class Seat {
    private int seatID;
    private int userID;

    public Seat(int seatID, int userID) {
        this.seatID = seatID;
        this.userID = userID;
    }

    public int getSeatID() {
        return seatID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatID=" + seatID +
                ", userID=" + userID +
                '}';
    }
}