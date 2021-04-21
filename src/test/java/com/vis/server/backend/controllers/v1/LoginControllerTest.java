package com.vis.server.backend.controllers.v1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.vis.server.backend.domain.dto.UserDto;
import com.vis.server.backend.domain.services.token.JwtTokenService;
import com.vis.server.backend.domain.services.user.JwtUserDetailsService;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
class LoginControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenUserPutAppropriateCredentials_thenServerShouldRespondThatRequestIsOkWithTokenDto(){

        // given
        when(authenticationManager.authenticate(any())).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "admin";
            }
        });

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return "admin";
            }

            @Override
            public String getUsername() {
                return "admin";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        });
        when(jwtTokenService.generateToken(any())).thenReturn("Bearer <token>");

        // when/then
        webTestClient
            .post().uri("/v1/vis-test/login/")
            .body(Mono.just(new UserDto("admin", "admin")), UserDto.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody().json("{\"token\":\"Bearer <token>\"}");//.isEqualTo(new UserLoginResponseDto("Bearer asdasdsadasdadsadasdasd"));
    }
}