package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.services.ProductServiceImpl;
import ec.edu.ups.icc.fundamentos01.products.services.ProductsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    // ================== ENDPOINTS PAGINADOS ==================
    @GetMapping(params = {"page", "size"}) 
    public ResponseEntity<Page<ProductsResponseDto>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sort) {
        return ResponseEntity.ok(productsService.findAll(page, size, sort));
    }

    @GetMapping("/slice")
    public ResponseEntity<Slice<ProductsResponseDto>> findAllSlice(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sort) {
        return ResponseEntity.ok(productsService.findAllSlice(page, size, sort));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductsResponseDto>> findWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sort) {
        return ResponseEntity.ok(productsService.findWithFilters(name, minPrice, maxPrice, categoryId, page, size, sort));
    }

    @GetMapping(value = "/user/{userId}", params = {"page", "size"})
    public ResponseEntity<Page<ProductsResponseDto>> findByUserIdPaged(
            @PathVariable Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sort) {
        return ResponseEntity.ok(productsService.findByUserIdWithFilters(userId, name, minPrice, maxPrice, categoryId, page, size, sort));
    }

    // ================== ENDPOINTS EXISTENTES ==================

    @PostMapping
    public ResponseEntity<ProductsResponseDto> create(@Valid @RequestBody CreateProductsDto dto) {
        ProductsResponseDto created = productsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProductsResponseDto>> findAll() {
        return ResponseEntity.ok(((ProductServiceImpl) productsService).findAll().stream().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") int id) {
        return ResponseEntity.ok(productsService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") int id, @Valid @RequestBody UpdateProductsDto dto) {
        return ResponseEntity.ok(productsService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> partialUpdate(@PathVariable("id") int id, @RequestBody PartialUpdateProductsDto dto) {
        return ResponseEntity.ok(productsService.partialUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok(productsService.delete(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductsResponseDto>> findByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(productsService.findByUserId(userId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductsResponseDto>> findByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(productsService.findByCategoryId(categoryId));
    }

    @GetMapping("/category/{categoryId}/count")
    public ResponseEntity<Object> countByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productsService.countProductsByCategory(categoryId));
    }
}