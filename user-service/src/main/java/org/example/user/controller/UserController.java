package org.example.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.UserDTO;
import org.example.common.response.Result;
import org.example.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<UserDTO> register(@RequestBody Map<String, String> body) {
        UserDTO user = userService.register(
                body.get("username"),
                body.get("password"),
                body.get("email"),
                body.get("phone")
        );
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result<UserDTO> login(@RequestBody Map<String, String> body) {
        UserDTO user = userService.login(body.get("username"), body.get("password"));
        return Result.success(user);
    }

    @GetMapping("/{id}")
    public Result<UserDTO> getUserById(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @GetMapping
    public Result<List<UserDTO>> listUsers() {
        return Result.success(userService.listUsers());
    }

    @PutMapping("/{id}")
    public Result<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return Result.success(userService.updateUser(id, dto));
    }
}
