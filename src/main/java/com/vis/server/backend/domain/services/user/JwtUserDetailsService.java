package com.vis.server.backend.domain.services.user;

import com.vis.server.backend.domain.model.Permission;
import com.vis.server.backend.domain.model.User;
import com.vis.server.backend.domain.repository.PermissionRepository;
import com.vis.server.backend.domain.repository.UserRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private PermissionRepository permissionRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = userRepository.findByUsername(username);

        if (users == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Set<Permission> permissions = permissionRepository.findByRoleId(users.getRoleId());
        return getUserDetails(users, getSimpleGrantedAuthorities(permissions));
    }

    private UserDetails getUserDetails(User user, Set<SimpleGrantedAuthority> convertedPermissions) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return convertedPermissions;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return user.isAccountNonExpired();
            }

            @Override
            public boolean isAccountNonLocked() {
                return user.isAccountNonLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return user.isCredentialsNonExpired();
            }

            @Override
            public boolean isEnabled() {
                return user.isEnabled();
            }
        };
    }

    private Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities(Set<Permission> permissions) {
        Set<SimpleGrantedAuthority> convertedPermissions;
        if (permissions.size() == 0) {
            convertedPermissions = Collections.emptySet();
        } else {
            convertedPermissions = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionType().name()))
                .collect(Collectors.toSet());
        }
        return convertedPermissions;
    }
}
