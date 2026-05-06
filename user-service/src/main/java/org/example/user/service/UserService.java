package org.example.user.service;

import org.example.common.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO register(String username, String password, String email, String phone);

    UserDTO login(String username, String password);

    UserDTO getUserById(Long id);

    List<UserDTO> listUsers();

    UserDTO updateUser(Long id, UserDTO dto);
}
