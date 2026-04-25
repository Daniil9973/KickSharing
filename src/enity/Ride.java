package enity;

import java.time.LocalDateTime;

public class Ride {
    private int id;
    private int userId;                 // FK на пользователя
    private LocalDateTime startTime;    // время начала поездки
    private LocalDateTime endTime;      // время конца поездки
    private double price;           // стоимость

    public Ride() {}

    // Конструктор для создания новой поездки
    public Ride(int userId, LocalDateTime startTime, LocalDateTime endTime, double price) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
