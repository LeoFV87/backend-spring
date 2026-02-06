package ec.edu.ups.icc.fundamentos01.projects.entity;

import ec.edu.ups.icc.core.entities.BaseModel; 
import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class ProjectEntity extends BaseModel {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;
    private String demoUrl;
    private String repoUrl;
    private String role;
    private String type;
    private String programmerId; 
    private String technologies;

    public ProjectEntity() {
    }

    public ProjectEntity(String title, String description, String imageUrl, String demoUrl, 
                         String repoUrl, String role, String type, String programmerId, 
                         String technologies) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.demoUrl = demoUrl;
        this.repoUrl = repoUrl;
        this.role = role;
        this.type = type;
        this.programmerId = programmerId;
        this.technologies = technologies;
    }

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDemoUrl() { return demoUrl; }
    public void setDemoUrl(String demoUrl) { this.demoUrl = demoUrl; }

    public String getRepoUrl() { return repoUrl; }
    public void setRepoUrl(String repoUrl) { this.repoUrl = repoUrl; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getProgrammerId() { return programmerId; }
    public void setProgrammerId(String programmerId) { this.programmerId = programmerId; }

    public String getTechnologies() { return technologies; }
    public void setTechnologies(String technologies) { this.technologies = technologies; }
}