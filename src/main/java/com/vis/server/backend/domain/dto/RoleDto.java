package com.vis.server.backend.domain.dto;

import com.vis.server.backend.domain.model.PermissionTypes;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotEmpty;

public class RoleDto {

    @NotEmpty(message = "Role name is required")
    private String name;

    private Set<PermissionTypes> permissions;

    public RoleDto(String name, Set<PermissionTypes> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PermissionTypes> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionTypes> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDto roleDto = (RoleDto) o;
        return name.equals(roleDto.name) && permissions.equals(roleDto.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, permissions);
    }

    @Override
    public String toString() {
        return "RoleDto{" +
            "name='" + name + '\'' +
            ", permissions=" + permissions +
            '}';
    }
}
