package com.vis.server.backend.domain.services;

import com.vis.server.backend.domain.dto.RoleDto;
import com.vis.server.backend.domain.dto.RoleResponseDto;
import com.vis.server.backend.domain.model.Permission;
import com.vis.server.backend.domain.model.PermissionTypes;
import com.vis.server.backend.domain.model.Roles;
import com.vis.server.backend.domain.repository.PermissionRepository;
import com.vis.server.backend.domain.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public RolesService(RoleRepository roleRepository,
        PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public RoleResponseDto insertRole(RoleDto roleDto) {
        Roles roles = new Roles();
        roles.setName(roleDto.getName());
        Roles role = roleRepository.save(roles);
        insertNewPermissionsForRole(roleDto, roles.getId(), role);
        return convertToRoleResponseDto(role);
    }

    @Transactional
    public RoleResponseDto updateRole(long roleId, RoleDto roleDto) throws Exception {
        roleRepository.updateRoles(roleId, roleDto.getName());
        this.permissionRepository.deleteByRoleId(roleId);
        Roles updatedRoles = roleRepository.findById(roleId)
            .orElseThrow(() -> new Exception(String.format("Can not find role with id: %s", roleId)));
        insertNewPermissionsForRole(roleDto, roleId, updatedRoles);
        return convertToRoleResponseDto(updatedRoles);
    }


    @Transactional
    public void deleteRole(long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Transactional
    public List<RoleResponseDto> getAllDefinedRoles() {
        List<Roles> roles = StreamSupport.stream(roleRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
        return roles.stream()
            .map(roleEntry ->
                new RoleResponseDto(roleEntry.getId(), roleEntry.getName(), roleEntry.getPermissionMaps().stream()
                    .map(Permission::getPermissionType)
                    .collect(Collectors.toSet()))
            )
            .collect(Collectors.toList());
    }

    private RoleResponseDto convertToRoleResponseDto(Roles role) {
        Stream<PermissionTypes> permissionTypesStream = role.getPermissionMaps().stream().map(
            Permission::getPermissionType);
        return new RoleResponseDto(role.getId(), role.getName(), permissionTypesStream.collect(Collectors.toSet()));
    }

    private void insertNewPermissionsForRole(RoleDto roleDto, long roleId,
        Roles role) {
        Set<Permission> permissionSet = new HashSet<>();
        roleDto.getPermissions().forEach(permissionType -> {
            Permission permission = new Permission();
            permission.setPermissionType(permissionType);
            permission.setRoleId(roleId);
            Permission resp = permissionRepository.save(permission);
            permissionSet.add(resp);
        });
        role.setPermissionMaps(permissionSet);
    }
}
