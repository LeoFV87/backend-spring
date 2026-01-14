package ec.edu.ups.icc.fundamentos01.products.models;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class Products {
    private int id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String createdAt;

    public Products(int id, String name, String description, Double price, Integer stock) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        if (price == null || price < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        if (stock == null || stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public static Products fromDto(CreateProductsDto dto) {
        return new Products(0, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock());
    }

    public static Products fromEntity(ProductEntity entity) {
        return new Products(
            entity.getId().intValue(),
            entity.getName(),
            entity.getDescription(),
            entity.getPrice(),
            entity.getStock()
        );
    }

  public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0) entity.setId((long) this.id);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        return entity;
    }

 public ProductEntity toEntity(UserEntity owner, CategoryEntity category) {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0) {
         entity.setId((long) this.id);

        }
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setPrice(this.price);
        entity.setStock(this.stock);

        entity.setOwner(owner);
        entity.setCategory(category);

        return entity;
    }




    public ProductsResponseDto toResponseDto() {
        ProductsResponseDto dto = new ProductsResponseDto();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setPrice(this.price);
        dto.setStock(this.stock);
        return dto;
    }

    // Actualización completa
    public Products update(UpdateProductsDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
        return this;
    }

    // Actualización parcial
    public Products partialUpdate(PartialUpdateProductsDto dto) {
        if (dto.getName() != null) {
            this.name = dto.getName();
        }
        if (dto.getPrice() != null) {
            this.price = dto.getPrice();
        }
        if (dto.getStock() != null) {
            this.stock = dto.getStock();
        }
        return this;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
