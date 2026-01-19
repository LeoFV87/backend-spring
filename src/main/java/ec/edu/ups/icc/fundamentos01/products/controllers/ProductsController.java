package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.services.ProductsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsResponseDto>> findAll() {
        return ResponseEntity.ok(productsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") int id) {
        return ResponseEntity.ok(productsService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<ProductsResponseDto> create(@Valid @RequestBody CreateProductsDto dto) {
        // Para creación se debe usar status 201
        ProductsResponseDto created = productsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
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
}