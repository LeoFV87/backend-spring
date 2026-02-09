package ec.edu.ups.icc.fundamentos01.advisories.entities;

import ec.edu.ups.icc.core.entities.BaseModel;
import jakarta.persistence.*;

@Entity
@Table(name = "advisories")
public class AdvisoryEntity extends BaseModel {

    private String clientEmail;
    private String clientId;
    private String clientName;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    private String programmerId;
    private String programmerName;
    
    @Column(columnDefinition = "TEXT")
    private String replyMessage;

    // Pending, accepted, rejected
    private String status; 
    private String timeSlot;
    private String topic;

    // Getters y Setters
    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getProgrammerId() { return programmerId; }
    public void setProgrammerId(String programmerId) { this.programmerId = programmerId; }
    public String getProgrammerName() { return programmerName; }
    public void setProgrammerName(String programmerName) { this.programmerName = programmerName; }
    public String getReplyMessage() { return replyMessage; }
    public void setReplyMessage(String replyMessage) { this.replyMessage = replyMessage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}