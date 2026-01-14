package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.Products;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service
public class ProductServiceImpl implements ProductsService {

    private final ProductRepository productRepo;

    private final CategoryRepository categoryRepo;

    private final UserRepository userRepo;

    public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo, UserRepository userRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
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

    private ProductsResponseDto toResponseDto(ProductEntity entity) {
    return Products.fromEntity(entity).toResponseDto();
}

    @Override
    public ProductsResponseDto create(CreateProductsDto dto) {

        // 1. VALIDAR RELACIONES
        UserEntity owner = userRepo.findById(dto.getOwnerId())
                .orElseThrow(() -> new NotFoundException(
                "Usuario no encontrado con ID: " + dto.getOwnerId()
        ));

        CategoryEntity category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                "Categoría no encontrada con ID: " + dto.getCategoryId()
        ));

        // 2. REGLA DE NEGOCIO
        if (productRepo.findByName(dto.getName()).isPresent()) {
            throw new ConflictException(
                    "El nombre del producto '" + dto.getName() + "' ya existe"
            );
        }

        // 3. DOMINIO
        Products product = Products.fromDto(dto);

        // 4. ENTIDAD CON RELACIONES
        ProductEntity entity = product.toEntity(owner, category);

        // 5. PERSISTIR
        ProductEntity saved = productRepo.save(entity);

        // 6. RESPUESTA
        return toResponseDto(saved);
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
