package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.Products;

public class ProductsMapper {

    /**
     * Convierte la entidad JPA a DTO de respuesta (Estructura Anidada)
     */
    public static ProductsResponseDto toResponse(ProductEntity entity) {
        if (entity == null) return null;
        
        ProductsResponseDto dto = new ProductsResponseDto();
        
        // Mapeo de campos básicos con conversión de tipos
        if (entity.getId() != null) dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        
        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt().toString());
        }

        // Mapeo anidado del Usuario
        if (entity.getOwner() != null) {
            ProductsResponseDto.UserSummaryDto userDto = new ProductsResponseDto.UserSummaryDto();
            userDto.id = entity.getOwner().getId();
            userDto.name = entity.getOwner().getName();
            userDto.email = entity.getOwner().getEmail();
            dto.user = userDto;
        }

        // Mapeo anidado de la Categoría
        if (entity.getCategory() != null) {
            ProductsResponseDto.CategoryResponseDto catDto = new ProductsResponseDto.CategoryResponseDto();
            catDto.id = entity.getCategory().getId();
            catDto.name = entity.getCategory().getName();
            catDto.description = entity.getCategory().getDescription();
            dto.category = catDto;
        }

        return dto;
    }

    /**
     * Convierte el modelo de dominio a DTO
     */
    public static ProductsResponseDto toResponse(Products product) {
        if (product == null) return null;
        
        ProductsResponseDto dto = new ProductsResponseDto();
        // Nota: El dominio no conoce las entidades owner/category directamente en este diseño
        if (product.getId() != null) dto.setId(product.getId().intValue());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }
}