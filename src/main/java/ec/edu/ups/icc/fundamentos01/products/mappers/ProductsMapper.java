package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.Products;

public class ProductsMapper {

    // Para convertir la entidad de base de datos a respuesta
    public static ProductsResponseDto toResponse(ProductEntity entity) {
        ProductsResponseDto dto = new ProductsResponseDto();
        dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        return dto;
    }

    // Para convertir el modelo de dominio a respuesta
    public static ProductsResponseDto toResponse(Products product) {
        ProductsResponseDto dto = new ProductsResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }

    public static Products toEntity(int i, String name, Double price, Integer stock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }
}