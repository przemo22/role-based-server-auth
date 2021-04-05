package com.vis.server.backend.domain.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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
        UserDto userDto = (UserDto) o;
        return username.equals(userDto.username) && password.equals(userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "UserDto{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
