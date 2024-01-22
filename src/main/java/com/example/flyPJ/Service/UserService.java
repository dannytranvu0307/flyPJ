package com.example.flyPJ.Service;

import com.example.flyPJ.DTO.UserDTO;
import com.example.flyPJ.Payload.UserPayload;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long userId);

//    UserDTO updateUser(Long userId, UserDTO updatedUserDTO);

    UserDTO createUser(UserPayload userPayload);

    boolean deleteUser(Long userId);
}
