package org.example.order.feign.fallback;

import org.example.common.dto.UserDTO;
import org.example.common.response.Result;
import org.example.common.response.ResultCode;
import org.example.order.feign.UserFeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallback implements UserFeignClient {

    @Override
    public Result<UserDTO> getUserById(Long id) {
        return Result.fail(ResultCode.SERVICE_UNAVAILABLE);
    }
}
