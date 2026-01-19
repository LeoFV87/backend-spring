package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;
import org.springframework.stereotype.Service;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
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
    public ProductsResponseDto create(CreateProductsDto dto) {
        // 1. VALIDAR RELACIONES
        UserEntity owner = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.getUserId()));

        CategoryEntity category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + dto.getCategoryId()));

        // 2. REGLA DE NEGOCIO
        if (productRepo.findByName(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del producto '" + dto.getName() + "' ya existe");
        }

        // 3. DOMINIO -> ENTIDAD
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        
        // === CORRECCIÓN IMPORTANTE PARA EL SCRIPT ===
        // Si el stock viene nulo (como en el script), ponemos 0 por defecto para que la BD no falle
        entity.setStock(dto.getStock() != null ? dto.getStock() : 0);
        
        entity.setOwner(owner);
        entity.setCategory(category);

        // 4. PERSISTIR
        ProductEntity saved = productRepo.save(entity);

        // 5. RESPUESTA
        return toResponseDto(saved);
    }

    private ProductsResponseDto toResponseDto(ProductEntity entity) {
        ProductsResponseDto dto = new ProductsResponseDto();

        if (entity.getId() != null) dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());

        if (entity.getCreatedAt() != null) dto.setCreatedAt(entity.getCreatedAt().toString());
        if (entity.getUpdatedAt() != null) dto.setUpdatedAt(entity.getUpdatedAt().toString());

        if (entity.getOwner() != null) {
            ProductsResponseDto.UserSummaryDto userDto = new ProductsResponseDto.UserSummaryDto();
            userDto.id = entity.getOwner().getId();
            userDto.name = entity.getOwner().getName();
            userDto.email = entity.getOwner().getEmail();
            dto.user = userDto;
        }

        if (entity.getCategory() != null) {
            ProductsResponseDto.CategoryResponseDto catDto = new ProductsResponseDto.CategoryResponseDto();
            catDto.id = entity.getCategory().getId();
            catDto.name = entity.getCategory().getName();
            catDto.description = entity.getCategory().getDescription();
            dto.category = catDto;
        }
        return dto;
    }

    @Override
    public List<ProductsResponseDto> findAll() {
        return productRepo.findAll().stream().map(this::toResponseDto).toList();
    }

    @Override
    public Object findOne(int id) {
        return productRepo.findById((long) id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }
    
    // Métodos placeholder (implementar según necesidad)
    @Override
    public Object update(int id, UpdateProductsDto dto) {
        // 1. Buscar producto existente
        ProductEntity existing = productRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        // 2. Actualizar campos simples si vienen en el DTO
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getPrice() != null) existing.setPrice(dto.getPrice());
        if (dto.getStock() != null) existing.setStock(dto.getStock());

        // 3. Actualizar categoría si viene en el DTO
        if (dto.getCategoryId() != null) {
            CategoryEntity newCategory = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
            existing.setCategory(newCategory);
        }

        // 4. Guardar y retornar DTO actualizado
        ProductEntity saved = productRepo.save(existing);
        return toResponseDto(saved);
    }
    
    @Override public Object partialUpdate(int id, PartialUpdateProductsDto dto) { return null; }
    @Override public Object delete(int id) { return null; }
    
    @Override
    public List<ProductsResponseDto> findByUserId(Long userId) {
        if (!userRepo.existsById(userId)) throw new NotFoundException("Usuario no encontrado");
        return productRepo.findByOwnerId(userId).stream().map(this::toResponseDto).toList();
    }

    @Override
    public List<ProductsResponseDto> findByCategoryId(Long categoryId) {
        if (!categoryRepo.existsById(categoryId)) throw new NotFoundException("Categoría no encontrada");
        return productRepo.findByCategoryId(categoryId).stream().map(this::toResponseDto).toList();
    }
}