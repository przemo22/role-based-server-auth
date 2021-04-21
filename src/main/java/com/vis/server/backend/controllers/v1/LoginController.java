package com.vis.server.backend.controllers.v1;

import com.vis.server.backend.domain.dto.UserDto;
import com.vis.server.backend.domain.dto.UserLoginResponseDto;
import com.vis.server.backend.domain.services.token.JwtTokenService;
import com.vis.server.backend.domain.services.user.JwtUserDetailsService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/v1/vis-test/login")
public class LoginController {

    private AuthenticationManager authenticationManager;

    private JwtTokenService jwtTokenService;

    private JwtUserDetailsService userDetailsService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
        JwtTokenService jwtTokenService,
        JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDto> userLogin(@Valid @RequestBody UserDto userDto) throws Exception {
        authenticate(userDto.getUsername(), userDto.getPassword());

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(userDto.getUsername());

        final String token = jwtTokenService.generateToken(userDetails);

        return ResponseEntity.ok(new UserLoginResponseDto(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
