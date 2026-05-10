package org.example.product.service;

import org.example.common.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO dto);

    ProductDTO getProductById(Long id);

    List<ProductDTO> listProducts();

    ProductDTO updateProduct(Long id, ProductDTO dto);

    void reduceStock(Long productId, Integer quantity);
}
