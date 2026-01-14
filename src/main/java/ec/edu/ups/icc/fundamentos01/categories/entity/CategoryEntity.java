package ec.edu.ups.icc.fundamentos01.categories.entity;

import ec.edu.ups.icc.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseModel {
    
    public CategoryEntity() {
      
    }

    @Column(nullable=false, length=100, unique= true)
    private String name;

    @Column(length=500)
    private String description;


    //atributos relaciones
    @ManyToOne(optional= false, fetch= FetchType.LAZY)
    @JoinColumn(name="user_id", nullable= false)
    private UserEntity owner;

    @ManyToOne(optional= false, fetch= FetchType.LAZY)
    @JoinColumn(name="category_id", nullable= false)
    private CategoryEntity category;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public UserEntity getOwner() {
    return owner;
}

public void setOwner(UserEntity owner) {
    this.owner = owner;
}

public CategoryEntity getCategory() {
    return category;
}

public void setCategory(CategoryEntity category) {
    this.category = category;
}






}
