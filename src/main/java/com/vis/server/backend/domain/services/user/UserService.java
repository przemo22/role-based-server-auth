package com.vis.server.backend.domain.services.user;

import com.vis.server.backend.domain.dto.CreateUserDto;
import com.vis.server.backend.domain.dto.RoleResponseDto;
import com.vis.server.backend.domain.dto.UpdateUserDto;
import com.vis.server.backend.domain.dto.UserResponseDto;
import com.vis.server.backend.domain.model.Permission;
import com.vis.server.backend.domain.model.Roles;
import com.vis.server.backend.domain.model.User;
import com.vis.server.backend.domain.repository.PermissionRepository;
import com.vis.server.backend.domain.repository.RoleRepository;
import com.vis.server.backend.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
        PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<UserResponseDto> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
            .map(user ->
                new UserResponseDto(
                    user.getId(),
                    user.getUsername(),
                    new RoleResponseDto(
                        user.getRoleId(),
                        user.getRole().getName(),
                        permissionRepository.findByRoleId(user.getId()).stream()
                            .map(Permission::getPermissionType)
                            .collect(Collectors.toSet())
                    )
                )
            )
            .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto createNewUser(CreateUserDto createUserDto) throws Exception {
        User user = new User();
        Roles role = roleRepository.findById(createUserDto.getRoleId()).orElseThrow(
            () -> new Exception(String.format("Can not find role with id: %s", createUserDto.getRoleId())));
        user.setUsername(createUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRoleId(role.getId());
        user.setRole(role);
        User createdUser = userRepository.save(user);
        return mapUserToUserResponseDto(createdUser);
    }

    @Transactional
    public UserResponseDto updateUser(UpdateUserDto updateUserDto, long userId) throws Exception {
        Optional<User> updatedUser = userRepository.findById(userId);
        Roles role = roleRepository.findById(updateUserDto.getRoleId()).orElseThrow(
            () -> new Exception(String.format("Can not find role with id: %s", updateUserDto.getRoleId())));
        User changedUser = updatedUser
            .map(user -> {
                user.setUsername(updateUserDto.getUsername());
                user.setRoleId(role.getId());
                user.setRole(role);
                return user;
            })
            .orElseThrow(() -> new Exception(String.format("User with id: %s not found", userId)));
        userRepository.save(changedUser);
        return mapUserToUserResponseDto(changedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private static UserResponseDto mapUserToUserResponseDto(User createdUser) {
        return new UserResponseDto(
            createdUser.getId(),
            createdUser.getUsername(),
            new RoleResponseDto(
                createdUser.getRoleId(),
                createdUser.getRole().getName(),
                createdUser.getRole().getPermissionMaps().stream()
                    .map(Permission::getPermissionType)
                    .collect(Collectors.toSet())
            )
        );
    }
}
