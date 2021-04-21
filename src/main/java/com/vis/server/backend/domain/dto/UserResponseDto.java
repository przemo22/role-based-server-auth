package com.vis.server.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class UserResponseDto {

    private long id;
    private String username;
    private RoleResponseDto role;


    public UserResponseDto(){
    }

    public UserResponseDto(@JsonProperty("id") long id, @JsonProperty("username") String username,
        @JsonProperty("role") RoleResponseDto role) {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(RoleResponseDto role) {
        this.role = role;
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
