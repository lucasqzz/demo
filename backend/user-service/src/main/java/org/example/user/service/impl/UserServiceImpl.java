package org.example.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.UserDTO;
import org.example.common.exception.BusinessException;
import org.example.common.response.ResultCode;
import org.example.user.entity.User;
import org.example.user.mapper.UserMapper;
import org.example.user.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO register(String username, String password, String email, String phone) {
        if (userMapper.findByUsername(username) != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhone(phone);
        userMapper.insert(user);
        return toDTO(user);
    }

    @Override
    public UserDTO login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "Invalid username or password");
        }
        return toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return toDTO(user);
    }

    @Override
    public List<UserDTO> listUsers() {
        return userMapper.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        userMapper.update(user);
        return toDTO(user);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
