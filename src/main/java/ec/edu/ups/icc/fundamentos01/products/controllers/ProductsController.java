package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Products;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductsMapper;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private List<Products> products = new ArrayList<>();
    private int currentId = 1;

    @GetMapping
    public List<ProductsResponseDto> findAll() {

        List<ProductsResponseDto> dtos = new ArrayList<>();
        for (Products product : products) {
            dtos.add(ProductsMapper.toResponse(product));
        }

        return products.stream()
                .map(ProductsMapper::toResponse)
                .toList();
    }

            @GetMapping("/{id}")
    public Object findOne(@PathVariable("id") int id) {

        for (Products product : products) {
            if (product.getId() == id) {
                return ProductsMapper.toResponse(product);
            }
        }
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(ProductsMapper::toResponse)
                .orElseGet(() -> new ProductsResponseDto() { public String error = "Product not found"; });
    }

            @PostMapping
    public ProductsResponseDto create(@RequestBody ProductsResponseDto dto) {
        Products product = ProductsMapper.toEntity(currentId++, dto.name, dto.price, dto.stock);
        products.add(product);
        return ProductsMapper.toResponse(product);
    }

            @PutMapping("/{id}")
    public Object update(@PathVariable("id") int id, @RequestBody UpdateProductsDto dto) {

        for (Products product : products) {
            if (product.getId() == id) {
                product.setName(dto.name);
                product.setPrice(dto.price);
                product.setStock(dto.stock);
                return ProductsMapper.toResponse(product);
            }
        }

        Products product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product == null) return new Object() { public String error = "Product not found"; };

        product.setName(dto.name);
        product.setPrice(dto.price);
        product.setStock(dto.stock);    
        return ProductsMapper.toResponse(product);
    }

           @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable("id") int id, @RequestBody PartialUpdateProductsDto dto) {

        for (Products product : products) {
            if (product.getId() == id) {
                if (dto.name != null) product.setName(dto.name);
                if (dto.price != null) product.setPrice(dto.price);
                if (dto.stock != null) product.setStock(dto.stock);
                return ProductsMapper.toResponse(product);
            }
        }
        Products product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product == null) return new Object() { public String error = "Product not found"; };

        if (dto.name != null) product.setName(dto.name);
        if (dto.price != null) product.setPrice(dto.price);
        if (dto.stock != null) product.setStock(dto.stock);

        return ProductsMapper.toResponse(product);
    }

        @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") int id) {

        Iterator<Products> iterator = products.iterator();
        while (iterator.hasNext()) {
            Products product = iterator.next();
            if (product.getId() == id) {
                iterator.remove();
                return new Object() { public String message = "Deleted successfully"; };
            }
        }

        boolean exists = products.removeIf(p -> p.getId() == id);
        if (!exists) return new Object() { public String error = "Product not found"; };

        return new Object() { public String message = "Deleted successfully"; };
    }
    
}