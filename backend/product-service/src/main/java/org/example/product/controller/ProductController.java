package org.example.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.ProductDTO;
import org.example.common.response.Result;
import org.example.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Result<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        return Result.success(productService.createProduct(dto));
    }

    @GetMapping("/{id}")
    public Result<ProductDTO> getProductById(@PathVariable Long id) {
        return Result.success(productService.getProductById(id));
    }

    @GetMapping
    public Result<List<ProductDTO>> listProducts() {
        return Result.success(productService.listProducts());
    }

    @PutMapping("/{id}")
    public Result<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return Result.success(productService.updateProduct(id, dto));
    }
}
