package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;

public interface ProductsService {
    
    // Métodos CRUD
    List<ProductsResponseDto> findAll();
    Object findOne(int id);
    ProductsResponseDto create(CreateProductsDto dto);
    Object update(int id, UpdateProductsDto dto);
    Object partialUpdate(int id, PartialUpdateProductsDto dto);
    Object delete(int id);

    // Métodos de Listas (Legacy)
    List<ProductsResponseDto> findByUserId(Long userId);
    List<ProductsResponseDto> findByCategoryId(Long categoryId);
    Map<String, Object> countProductsByCategory(Long categoryId);

    // ================== NUEVOS MÉTODOS PAGINADOS ==================
    
    // Paginación estándar
    Page<ProductsResponseDto> findAll(int page, int size, String[] sort);

    // Paginación ligera
    Slice<ProductsResponseDto> findAllSlice(int page, int size, String[] sort);

    // Búsqueda avanzada
    Page<ProductsResponseDto> findWithFilters(
        String name, Double minPrice, Double maxPrice, Long categoryId,
        int page, int size, String[] sort
    );

    // Búsqueda por usuario avanzada
    Page<ProductsResponseDto> findByUserIdWithFilters(
        Long userId, String name, Double minPrice, Double maxPrice, Long categoryId,
        int page, int size, String[] sort
    );
}