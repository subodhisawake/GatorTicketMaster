package models;

public class User implements Comparable<User> {
    private int userID;
    private int priority;
    private long timestamp;

    public User(int userID, int priority) {
        this.userID = userID;
        this.priority = priority;
        this.timestamp = System.currentTimeMillis();
    }

    public int getUserID() {
        return userID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(User other) {
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority); // Higher priority first
        }
        return Long.compare(this.timestamp, other.timestamp); // Earlier timestamp first
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", priority=" + priority +
                '}';
    }
}