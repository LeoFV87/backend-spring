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
                .map(this::toResponseDto) // Usamos el mapeo anidado
                .toList();
    }

    @Override
    public Object findOne(int id) {
        return productRepo.findById((long) id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    }

    /**
     * Helper para convertir Entidad a DTO con estructura ANIDADA
     */
    private ProductsResponseDto toResponseDto(ProductEntity entity) {
        ProductsResponseDto dto = new ProductsResponseDto();

        // 1. Campos básicos (Conversión de tipos)
        dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());

        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt().toString());
        }

        // 2. Mapeo anidado del Usuario (Owner)
        if (entity.getOwner() != null) {
            ProductsResponseDto.UserSummaryDto userDto = new ProductsResponseDto.UserSummaryDto();
            userDto.id = entity.getOwner().getId();
            userDto.name = entity.getOwner().getName();
            userDto.email = entity.getOwner().getEmail();
            dto.user = userDto; // Asignación directa según tu DTO
        }

        // 3. Mapeo anidado de la Categoría
        if (entity.getCategory() != null) {
            ProductsResponseDto.CategoryResponseDto catDto = new ProductsResponseDto.CategoryResponseDto();
            catDto.id = entity.getCategory().getId();
            catDto.name = entity.getCategory().getName();
            catDto.description = entity.getCategory().getDescription();
            dto.category = catDto; // Asignación directa según tu DTO
        }

        return dto;
    }

    @Override
    public ProductsResponseDto create(CreateProductsDto dto) {

        // 1. VALIDAR RELACIONES (Usando userId para que el script no de error 400)
        UserEntity owner = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.getUserId()));

        CategoryEntity category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + dto.getCategoryId()));

        // 2. REGLA DE NEGOCIO (Validar que el nombre no esté duplicado)
        if (productRepo.findByName(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del producto '" + dto.getName() + "' ya existe");
        }

        // 3. DOMINIO (Crear el modelo de negocio desde el DTO)
        Products product = Products.fromDto(dto);

        // 4. ENTIDAD CON RELACIONES (Aquí se define la variable 'entity')
        // Pasamos el dueño (owner) y la categoría encontrados en el paso 1
        ProductEntity entity = product.toEntity(owner, category);

        // 5. PERSISTIR
        ProductEntity saved = productRepo.save(entity);

        // 6. RESPUESTA ANIDADA
        return toResponseDto(saved);
    }

    @Override
    public Object update(int id, UpdateProductsDto dto) {
        return productRepo.findById((long) id)
                .map(existingEntity -> {

                    CategoryEntity category = categoryRepo.findById(dto.getCategoryId())
                            .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

                    Products domain = Products.fromEntity(existingEntity);
                    domain.update(dto);

                    ProductEntity updatedEntity = domain.toEntity(existingEntity.getOwner(), category);
                    updatedEntity.setId(existingEntity.getId());

                    return productRepo.save(updatedEntity);
                })
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("No se puede actualizar: Producto no encontrado"));
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateProductsDto dto) {
        return productRepo.findById((long) id)
                .map(Products::fromEntity)
                .map(p -> p.partialUpdate(dto))
                .map(Products::toEntity)
                .map(productRepo::save)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("No se puede actualizar: Producto no encontrado"));
    }

    @Override
    public Object delete(int id) {
        ProductEntity entity = productRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("No se puede eliminar: Producto no encontrado"));

        productRepo.delete(entity);
        return Map.of("message", "Deleted successfully");
    }

    @Override
    public List<ProductsResponseDto> findByUserId(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }
        return productRepo.findByOwnerId(userId).stream().map(this::toResponseDto).toList();
    }

    @Override
    public List<ProductsResponseDto> findByCategoryId(Long categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
        }
        return productRepo.findByCategoryId(categoryId).stream().map(this::toResponseDto).toList();
    }
}