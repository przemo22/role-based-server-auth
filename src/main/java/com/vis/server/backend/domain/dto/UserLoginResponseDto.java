package com.vis.server.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

public class UserLoginResponseDto implements Serializable {

    private String token;

    public UserLoginResponseDto() {
    }

    public UserLoginResponseDto(@JsonProperty("token") String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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
