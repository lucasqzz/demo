package org.example.order.feign;

import org.example.common.dto.UserDTO;
import org.example.common.response.Result;
import org.example.order.feign.fallback.UserFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallback = UserFeignFallback.class)
public interface UserFeignClient {

    @GetMapping("/v1/users/{id}")
    Result<UserDTO> getUserById(@PathVariable("id") Long id);
}
