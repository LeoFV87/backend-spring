package ec.edu.ups.icc.fundamentos01.schedules.entities;

import ec.edu.ups.icc.core.entities.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedules")
public class ScheduleEntity extends BaseModel {
    private String programmerEmail; 
    private String timeSlot;

     // Constructor, Getters y Setters...
    public ScheduleEntity() {
    }

    public ScheduleEntity(String programmerEmail, String timeSlot) {
        this.programmerEmail = programmerEmail;
        this.timeSlot = timeSlot;
    }

    public String getProgrammerEmail() {
        return programmerEmail;
    }
    public void setProgrammerEmail(String programmerEmail) {
        this.programmerEmail = programmerEmail;
    }
    public String getTimeSlot() {
        return timeSlot;
    }
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    } 

   

    
}