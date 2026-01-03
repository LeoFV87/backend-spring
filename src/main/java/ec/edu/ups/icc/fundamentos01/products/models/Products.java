package ec.edu.ups.icc.fundamentos01.products.models;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

public class Products {
    private int id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String createdAt;

    public Products() {
    }

    public static Products fromDto(CreateProductsDto dto) {
        Products p = new Products();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
        return p;
    }

    public static Products fromEntity(ProductEntity entity) {
        Products p = new Products();
        p.setId(entity.getId().intValue());
        p.setName(entity.getName());
        p.setDescription(entity.getDescription());
        p.setPrice(entity.getPrice());
        p.setStock(entity.getStock());
        // Mapear la fecha de la entidad al modelo
        if (entity.getCreatedAt() != null) {
            p.setCreatedAt(entity.getCreatedAt().toString());
        }
        return p;
    }

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0)
            entity.setId((long) this.id);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        return entity;
    }

    public ProductsResponseDto toResponseDto() {
        ProductsResponseDto dto = new ProductsResponseDto();
        // ERROR 3: Estos métodos fallarán si no pusiste Setters en ProductsResponseDto
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
