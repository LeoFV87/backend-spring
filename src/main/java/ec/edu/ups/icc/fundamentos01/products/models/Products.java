package ec.edu.ups.icc.fundamentos01.products.models;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class Products {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

    //Getters y Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public Integer getStock() { return stock; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Double price) { this.price = price; }
    public void setStock(Integer stock) { this.stock = stock; }

    public static Products fromDto(CreateProductsDto dto) {
        Products p = new Products();
        p.name = dto.getName();
        p.description = dto.getDescription();
        p.price = dto.getPrice();
        p.stock = dto.getStock();
        return p;
    }

    public static Products fromEntity(ProductEntity entity) {
        Products p = new Products();
        p.id = entity.getId();
        p.name = entity.getName();
        p.description = entity.getDescription();
        p.price = entity.getPrice();
        p.stock = entity.getStock();
        return p;
    }

    public ProductEntity toEntity(UserEntity owner, CategoryEntity category) {
        ProductEntity entity = new ProductEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        entity.setOwner(owner); //
        entity.setCategory(category); //
        return entity;
    }

    public Products update(UpdateProductsDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
        return this;
    }

    public Products partialUpdate(PartialUpdateProductsDto dto) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getPrice() != null) this.price = dto.getPrice();
        if (dto.getStock() != null) this.stock = dto.getStock();
        return this;
    }
}