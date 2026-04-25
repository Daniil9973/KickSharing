package enity;

public class Payment {
    private int id;
    private int rideID;                 // FK на пользователя
    private double amount;    // время начала поездки
    private String status;      // время конца поездки


    public Payment(int rideID, double amount, String status){
        this.rideID = rideID;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRideID() {
        return rideID;
    }

    public void setRideID(int rideID) {
        this.rideID = rideID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
