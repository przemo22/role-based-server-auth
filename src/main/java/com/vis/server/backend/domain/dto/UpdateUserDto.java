package com.vis.server.backend.domain.dto;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateUserDto {

    @NotNull
    private long roleId;

    @NotEmpty
    private String username;

    public UpdateUserDto(@NotNull long roleId, @NotEmpty String username) {
        this.roleId = roleId;
        this.username = username;
    }

    public long getRoleId() {
        return roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateUserDto that = (UpdateUserDto) o;
        return roleId == that.roleId && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, username);
    }

    @Override
    public String toString() {
        return "UpdateUserDto{" +
            "roleId=" + roleId +
            ", username='" + username + '\'' +
            '}';
    }
}
