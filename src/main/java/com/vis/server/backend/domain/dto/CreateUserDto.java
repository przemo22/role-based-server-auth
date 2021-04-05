package com.vis.server.backend.domain.dto;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateUserDto {

    @NotNull
    private long roleId;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public CreateUserDto(long roleId, String username, String password) {
        this.roleId = roleId;
        this.username = username;
        this.password = password;
    }

    public long getRoleId() {
        return roleId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateUserDto that = (CreateUserDto) o;
        return roleId == that.roleId && Objects.equals(username, that.username) && Objects
            .equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, username, password);
    }

    @Override
    public String toString() {
        return "CreateUserDto{" +
            "roleId=" + roleId +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
