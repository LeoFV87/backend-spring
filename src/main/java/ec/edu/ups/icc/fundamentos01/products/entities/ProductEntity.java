package ec.edu.ups.icc.fundamentos01.products.entities;

import ec.edu.ups.icc.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseModel {
    @Column(nullable = false, length = 150)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    // Relación 1:N con Usuario (Owner)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    // Relación N:N con Categorías
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_categories",                    // Tabla intermedia
        joinColumns = @JoinColumn(name = "product_id"), // FK producto
        inverseJoinColumns = @JoinColumn(name = "category_id") // FK categoría
    )
    private Set<CategoryEntity> categories = new HashSet<>();

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public UserEntity getOwner() { return owner; }
    public void setOwner(UserEntity owner) { this.owner = owner; }

    // Getters y Setters para la colección
    public Set<CategoryEntity> getCategories() { return categories; }
    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories != null ? categories : new HashSet<>();
    }
    
    // Métodos helper para sincronizar
    public void clearCategories() { this.categories.clear(); }
}