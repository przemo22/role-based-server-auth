package com.vis.server.backend.domain.dto;

import com.vis.server.backend.domain.model.PermissionTypes;
import java.util.Objects;
import java.util.Set;

public class RoleResponseDto {

    private final Long id;
    private final String name;
    private final Set<PermissionTypes> permissions;

    public RoleResponseDto(final Long id, final String name,
        final Set<PermissionTypes> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<PermissionTypes> getPermissions() {
        return permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleResponseDto that = (RoleResponseDto) o;
        return id.equals(that.id) && name.equals(that.name) && permissions.equals(that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permissions);
    }

    @Override
    public String toString() {
        return "RoleResponseDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", permissions=" + permissions +
            '}';
    }
}
