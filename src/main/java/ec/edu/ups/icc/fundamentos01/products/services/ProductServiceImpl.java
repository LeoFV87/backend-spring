package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.BadRequestException;
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

    private Set<CategoryEntity> validateAndGetCategories(Set<Long> categoryIds) {
        Set<CategoryEntity> categories = new HashSet<>();
        if (categoryIds != null) {
            for (Long catId : categoryIds) {
                CategoryEntity category = categoryRepo.findById(catId)
                        .orElseThrow(() -> new NotFoundException("Categoría no encontrada ID: " + catId));
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    @Transactional
    public ProductsResponseDto create(CreateProductsDto dto) {
        UserEntity owner = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Set<CategoryEntity> categories = validateAndGetCategories(dto.getCategoryIds());

        // CORRECCIÓN: Como el repositorio ahora devuelve Optional, .isPresent() funciona correctamente
        if (productRepo.findByName(dto.getName()).isPresent()) {
            throw new ConflictException("Nombre duplicado");
        }

        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock() != null ? dto.getStock() : 0);
        entity.setOwner(owner);
        entity.setCategories(categories);

        return toResponseDto(productRepo.save(entity));
    }

    @Override
    @Transactional
    public Object update(int id, UpdateProductsDto dto) {
        ProductEntity existing = productRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getPrice() != null) existing.setPrice(dto.getPrice());
        if (dto.getStock() != null) existing.setStock(dto.getStock());

        if (dto.getCategoryIds() != null) {
            Set<CategoryEntity> newCategories = validateAndGetCategories(dto.getCategoryIds());
            existing.clearCategories();
            existing.setCategories(newCategories);
        }

        return toResponseDto(productRepo.save(existing));
    }

    // ================== MÉTODOS DE PAGINACIÓN (PRÁCTICA 10) ==================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductsResponseDto> findAll(int page, int size, String[] sort) {
        Pageable pageable = createPageable(page, size, sort);
        return productRepo.findAll(pageable).map(this::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<ProductsResponseDto> findAllSlice(int page, int size, String[] sort) {
        Pageable pageable = createPageable(page, size, sort);
        return productRepo.findAllSlice(pageable).map(this::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductsResponseDto> findWithFilters(String name, Double minPrice, Double maxPrice, Long categoryId, int page, int size, String[] sort) {
    
        validateFilterParameters(minPrice, maxPrice);
        Pageable pageable = createPageable(page, size, sort);

    // Formateamos el nombre aquí para que el SQL sea más simple y no necesite casteos
        String nameParam = (name != null && !name.isEmpty()) ? "%" + name + "%" : null;

        return productRepo.findWithFilters(nameParam, minPrice, maxPrice, categoryId, pageable)
            .map(this::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductsResponseDto> findByUserIdWithFilters(Long userId, String name, Double minPrice, Double maxPrice, Long categoryId, int page, int size, String[] sort) {
        if (!userRepo.existsById(userId)) throw new NotFoundException("Usuario no encontrado");
        validateFilterParameters(minPrice, maxPrice);
        Pageable pageable = createPageable(page, size, sort);
        return productRepo.findByUserIdWithFilters(userId, name, minPrice, maxPrice, categoryId, pageable).map(this::toResponseDto);
    }

    // ================== MÉTODOS PRIVADOS ==================

    private Pageable createPageable(int page, int size, String[] sort) {
        if (page < 0) throw new BadRequestException("La página debe ser mayor o igual a 0");
        if (size < 1 || size > 100) throw new BadRequestException("El tamaño debe estar entre 1 y 100");
        return PageRequest.of(page, size, createSort(sort));
    }

   private Sort createSort(String[] sort) {
    if (sort == null || sort.length == 0) return Sort.by("id");

    List<Sort.Order> orders = new ArrayList<>();
    for (String sortParam : sort) {
    
        if (sortParam.equalsIgnoreCase("asc") || sortParam.equalsIgnoreCase("desc")) continue;

        String[] parts = sortParam.split(",");
        String property = parts[0];
        String direction = parts.length > 1 ? parts[1] : "asc";

        if (isValidSortProperty(property)) {
            orders.add("desc".equalsIgnoreCase(direction) ? Sort.Order.desc(property) : Sort.Order.asc(property));
        } else {
            throw new BadRequestException("Propiedad de ordenamiento no válida: " + property);
        }
     }
    return orders.isEmpty() ? Sort.by("id") : Sort.by(orders);
    }

    private boolean isValidSortProperty(String property) {
    Set<String> allowedProperties = Set.of(
        "id", "name", "price", "createdAt", "updatedAt", 
        "owner.name", "category.name"
    );
     return allowedProperties.contains(property);
    }

    private void validateFilterParameters(Double minPrice, Double maxPrice) {
        if (minPrice != null && minPrice < 0) throw new BadRequestException("Precio mínimo negativo");
        if (maxPrice != null && maxPrice < 0) throw new BadRequestException("Precio máximo negativo");
        if (minPrice != null && maxPrice != null && maxPrice < minPrice) throw new BadRequestException("Precio máximo menor que mínimo");
    }

    private ProductsResponseDto toResponseDto(ProductEntity entity) {
        ProductsResponseDto dto = new ProductsResponseDto();
        if (entity.getId() != null) dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        
        if (entity.getOwner() != null) {
            ProductsResponseDto.UserSummaryDto userDto = new ProductsResponseDto.UserSummaryDto();
            userDto.id = entity.getOwner().getId();
            userDto.name = entity.getOwner().getName();
            userDto.email = entity.getOwner().getEmail();
            dto.user = userDto;
        }
        if (entity.getCategories() != null) {
            dto.categories = entity.getCategories().stream().map(cat -> {
                ProductsResponseDto.CategoryResponseDto catDto = new ProductsResponseDto.CategoryResponseDto();
                catDto.id = cat.getId();
                catDto.name = cat.getName();
                catDto.description = cat.getDescription();
                return catDto;
            }).toList();
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductsResponseDto> findAll() {
        return productRepo.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }


    @Override @Transactional(readOnly = true)
    public Object findOne(int id) {
        return productRepo.findById((long) id).map(this::toResponseDto).orElseThrow(() -> new NotFoundException("No encontrado"));
    }
    @Override @Transactional
    public Object delete(int id) { productRepo.deleteById((long)id); return java.util.Map.of("ok", true); }
    @Override public Object partialUpdate(int id, PartialUpdateProductsDto dto) { return null; }
    @Override @Transactional(readOnly = true)
    public List<ProductsResponseDto> findByUserId(Long id) { return productRepo.findByOwnerId(id).stream().map(this::toResponseDto).toList(); }
    @Override @Transactional(readOnly = true)
    public List<ProductsResponseDto> findByCategoryId(Long id) { if (!categoryRepo.existsById(id)) throw new NotFoundException("Categoría no encontrada"); return productRepo.findByCategoriesId(id).stream().map(this::toResponseDto).toList(); } 
    @Override @Transactional(readOnly = true)
    public Map<String, Object> countProductsByCategory(Long categoryId) { if (!categoryRepo.existsById(categoryId)) throw new NotFoundException("Categoría no encontrada"); long count = productRepo.countByCategoriesId(categoryId); return Map.of("categoryId", categoryId, "productCount", count); }
}