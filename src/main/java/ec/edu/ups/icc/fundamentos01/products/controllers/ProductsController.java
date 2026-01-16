package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.services.ProductsService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public List<ProductsResponseDto> findAll() {
        return productsService.findAll();
    }

    @GetMapping("/{id}")
    public Object findOne(@PathVariable("id") int id) {
        return productsService.findOne(id);
    }

    @PostMapping
    public ProductsResponseDto create(@Valid @RequestBody CreateProductsDto dto) {
        return productsService.create(dto);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable("id") int id, @Valid @RequestBody UpdateProductsDto dto) {
        return productsService.update(id, dto);
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable("id") int id, @RequestBody PartialUpdateProductsDto dto) {
        return productsService.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") int id) {
        return productsService.delete(id);
    }

    @GetMapping("/user/{userId}")
    public List<ProductsResponseDto> findByUserId(@PathVariable("userId") Long userId) {
        return productsService.findByUserId(userId);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductsResponseDto> findByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return productsService.findByCategoryId(categoryId);
    }

}