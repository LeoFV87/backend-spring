package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.Products;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductsService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductsResponseDto> findAll() {
        return productRepo.findAll().stream()
                .map(Products::fromEntity)
                .map(Products::toResponseDto)
                .toList();
    }

   @Override
    public Object findOne(int id) {
        // Busca por ID en PostgreSQL (cast a Long para BaseModel)
        return productRepo.findById((long) id)
                .map(Products::fromEntity)
                .map(Products::toResponseDto)
                .map(dto -> (Object) dto) // Casteo para que coincida con el retorno Object
                .orElse(Map.of("error", "Product not found"));
    }

   @Override
    public ProductsResponseDto create(CreateProductsDto dto) {
        // DTO -> Modelo de Dominio
        Products product = Products.fromDto(dto);
        // Modelo -> Entidad -> Guardar en Postgres
        ProductEntity saved = productRepo.save(product.toEntity());
        // Entidad -> Modelo -> DTO de respuesta
        return Products.fromEntity(saved).toResponseDto();
    }

    @Override
    public Object update(int id, UpdateProductsDto dto) {
        return productRepo.findById((long) id)
                .map(Products::fromEntity)
                .map(p -> p.update(dto))         
                .map(Products::toEntity)
                .map(productRepo::save)            
                .map(Products::fromEntity)
                .map(Products::toResponseDto)
                .map(dtoResponse -> (Object) dtoResponse)
                .orElse(Map.of("error", "Product not found"));
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateProductsDto dto) {
        return productRepo.findById((long) id)
                .map(Products::fromEntity)
                .map(p -> p.partialUpdate(dto))   
                .map(Products::toEntity)
                .map(productRepo::save)
                .map(Products::fromEntity)
                .map(Products::toResponseDto)
                .map(dtoResponse -> (Object) dtoResponse)
                .orElse(Map.of("error", "Product not found"));
    }

    @Override
    public Object delete(int id) {
        return productRepo.findById((long) id)
                .map(entity -> {
                    productRepo.delete(entity); 
                    return (Object) Map.of("message", "Deleted successfully");
                })
                .orElse(Map.of("error", "Product not found"));
    }



   
}
