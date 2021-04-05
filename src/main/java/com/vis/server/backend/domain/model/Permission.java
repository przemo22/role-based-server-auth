package com.vis.server.backend.domain.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permissions")
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Roles roles;

    @Column(name = "role_id")
    private long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type")
    private PermissionTypes permissionType;

    @Override
    public String getAuthority() {
        return permissionType.name();
    }

    public Permission() {
    }

    public Permission(Roles roles, long roleId, PermissionTypes permissionType) {
        this.roles = roles;
        this.roleId = roleId;
        this.permissionType = permissionType;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public PermissionTypes getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionTypes permissionType) {
        this.permissionType = permissionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permission that = (Permission) o;
        return id == that.id && roleId == that.roleId && roles.equals(that.roles)
            && permissionType == that.permissionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, permissionType);
    }

    @Override
    public String toString() {
        return "Permission{" +
            "id=" + id +
            ", roles=" + roles +
            ", roleId=" + roleId +
            ", permissionType=" + permissionType +
            '}';
    }


}
