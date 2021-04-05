package com.vis.server.backend.domain.dto;

import java.util.Objects;

public class UserLoginResponseDto {

    private final String token;

    public String getToken() {
        return token;
    }

    public UserLoginResponseDto(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserLoginResponseDto that = (UserLoginResponseDto) o;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "UserLoginResponseDto{" +
            "token='" + token + '\'' +
            '}';
    }
}
