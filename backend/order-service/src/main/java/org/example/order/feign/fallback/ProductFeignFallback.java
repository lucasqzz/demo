package org.example.order.feign.fallback;

import org.example.common.dto.ProductDTO;
import org.example.common.response.Result;
import org.example.common.response.ResultCode;
import org.example.order.feign.ProductFeignClient;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignFallback implements ProductFeignClient {

    @Override
    public Result<ProductDTO> getProductById(Long id) {
        return Result.fail(ResultCode.SERVICE_UNAVAILABLE);
    }
}
