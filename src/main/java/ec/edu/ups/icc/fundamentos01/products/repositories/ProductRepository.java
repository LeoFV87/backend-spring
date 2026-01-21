package ec.edu.ups.icc.fundamentos01.products.repositories;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  
    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findByOwnerId(Long userId);
    List<ProductEntity> findByCategoriesId(Long categoryId);
    
    // Conteo para estadísticas
    long countByCategoriesId(Long categoryId);

    // ================== MÉTODOS PAGINADOS (PRÁCTICA 10) ==================

    // 1. Slice: Paginación optimizada sin contar el total
    @Query("SELECT p FROM ProductEntity p")
    Slice<ProductEntity> findAllSlice(Pageable pageable);

    // 2. Filtros Dinámicos + Paginación (Page)
    @Query("SELECT DISTINCT p FROM ProductEntity p " +
           "LEFT JOIN p.categories c " +
           "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
           "AND (:categoryId IS NULL OR c.id = :categoryId)")
    Page<ProductEntity> findWithFilters(
        @Param("name") String name,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("categoryId") Long categoryId,
        Pageable pageable
    );

    // 3. Productos de Usuario + Filtros + Paginación
    @Query("SELECT DISTINCT p FROM ProductEntity p " +
           "LEFT JOIN p.categories c " +
           "WHERE p.owner.id = :userId " +
           "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
           "AND (:categoryId IS NULL OR c.id = :categoryId)")
    Page<ProductEntity> findByUserIdWithFilters(
        @Param("userId") Long userId,
        @Param("name") String name,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("categoryId") Long categoryId,
        Pageable pageable
    );
}