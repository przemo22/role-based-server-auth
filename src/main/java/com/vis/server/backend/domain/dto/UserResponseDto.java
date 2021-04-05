package com.vis.server.backend.domain.dto;

import java.util.Objects;

public class UserResponseDto {

    private final long id;
    private final String username;
    private final RoleResponseDto role;

    public UserResponseDto(long id, String username, RoleResponseDto role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public RoleResponseDto getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserResponseDto that = (UserResponseDto) o;
        return id == that.id && Objects.equals(username, that.username) && Objects
            .equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role);
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", role=" + role +
            '}';
    }
}
