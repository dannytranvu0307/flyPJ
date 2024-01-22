package com.example.flyPJ.Service;

import com.example.flyPJ.DTO.UserDTO;
import com.example.flyPJ.Entity.User;
import com.example.flyPJ.Exception.UserCreationException;
import com.example.flyPJ.Payload.UserPayload;
import com.example.flyPJ.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper; // Dùng để chuyển đổi giữa User và UserDTO

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> modelMapper.map(u, UserDTO.class));
    }

    @Override
    public UserDTO createUser(UserPayload userPayload) {
        try {
            User user = new User();
            user.setEmail(userPayload.getEmail());
            user.setName(userPayload.getName());
            user.setPassword(userPayload.getPassword());
            User savedUser = userRepository.save(user);

            // Tạo một phiên bản rút gọn của UserDTO chỉ chứa email và name
            UserDTO createdUserDTO = new UserDTO();
            createdUserDTO.setEmail(savedUser.getEmail());
            createdUserDTO.setName(savedUser.getName());
            return createdUserDTO;
        } catch (Exception e) {
            throw new UserCreationException("Error creating user: " + e.getMessage());
        }
    }

//    @Override
//    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) {
//        return userRepository.findById(userId).map(user -> {
//            user.setEmail(updatedUserDTO.getEmail());
//            user.setPassword(updatedUserDTO.getPassword());
//            user.setName(updatedUserDTO.getName());
//            User updatedUser = userRepository.save(user);
//            return modelMapper.map(updatedUser, UserDTO.class);
//        }).orElse(null);
//    }

    @Override
    public boolean deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
