package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
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
        // Reemplazamos el .orElse(Map) por .orElseThrow para usar el manejador global
        return productRepo.findById((long) id)
                .map(Products::fromEntity)
                .map(Products::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    public ProductsResponseDto create(CreateProductsDto dto) {
        
        if (productRepo.findByName(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del producto '" + dto.getName() + "' ya existe");
        }

        Products product = Products.fromDto(dto);
        ProductEntity saved = productRepo.save(product.toEntity());
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
                .orElseThrow(() -> new NotFoundException("No se puede actualizar: Producto no encontrado")); //
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
                .orElseThrow(() -> new NotFoundException("No se puede actualizar: Producto no encontrado")); //
    }

    @Override
    public Object delete(int id) {
        // Buscamos la entidad; si no existe, lanzamos 404
        ProductEntity entity = productRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("No se puede eliminar: Producto no encontrado"));

        productRepo.delete(entity); 
        return Map.of("message", "Deleted successfully");
    }
}