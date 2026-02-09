package ec.edu.ups.icc.fundamentos01.advisories.dtos;

import jakarta.validation.constraints.NotBlank;

public class CreateAdvisoryDto {

    @NotBlank private String clientEmail;
    @NotBlank private String clientId;
    @NotBlank private String clientName;
    private String message;
    @NotBlank private String programmerId;
    @NotBlank private String programmerName;
    private String replyMessage;
    private String status = "pending";
    @NotBlank private String timeSlot;
    @NotBlank private String topic;

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