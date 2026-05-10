package org.example.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.ProductDTO;
import org.example.common.exception.BusinessException;
import org.example.common.response.ResultCode;
import org.example.product.entity.Product;
import org.example.product.mapper.ProductMapper;
import org.example.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        productMapper.insert(product);
        dto.setId(product.getId());
        return dto;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return toDTO(product);
    }

    @Override
    public List<ProductDTO> listProducts() {
        return productMapper.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        productMapper.update(product);
        return toDTO(product);
    }

    @Override
    public void reduceStock(Long productId, Integer quantity) {
        int rows = productMapper.reduceStock(productId, quantity);
        if (rows == 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "Insufficient stock for product: " + productId);
        }
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }
}
