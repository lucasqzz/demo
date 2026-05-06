package org.example.order.feign;

import org.example.common.dto.ProductDTO;
import org.example.common.response.Result;
import org.example.order.feign.fallback.ProductFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", fallback = ProductFeignFallback.class)
public interface ProductFeignClient {

    @GetMapping("/v1/products/{id}")
    Result<ProductDTO> getProductById(@PathVariable("id") Long id);
}
