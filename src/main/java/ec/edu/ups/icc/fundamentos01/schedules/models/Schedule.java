package ec.edu.ups.icc.fundamentos01.schedules.models;

public class Schedule {
    
    private Long id;
    private String programmerEmail;
    private String timeSlot;
    private String createdAt;

    // Constructor vacío
    public Schedule() {}

    // Constructor con parámetros esenciales
    public Schedule(Long id, String programmerEmail, String timeSlot) {
        this.id = id;
        this.programmerEmail = programmerEmail;
        this.timeSlot = timeSlot;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProgrammerEmail() { return programmerEmail; }
    public void setProgrammerEmail(String programmerEmail) { this.programmerEmail = programmerEmail; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}