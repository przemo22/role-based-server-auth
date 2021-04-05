package com.vis.server.backend.domain.model;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Role name is required")
    private String name;

    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Permission> permissions;


    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Set<User> users;

    public Roles(String name, Set<Permission> permissions, Set<User> users) {
        this.name = name;
        this.permissions = permissions;
        this.users = users;
    }

    public Roles() {
    }

    public Set<Permission> getPermissionMaps() {
        return permissions;
    }

    public void setPermissionMaps(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Roles roles = (Roles) o;
        return id == roles.id && name.equals(roles.name) && permissions.equals(roles.permissions) && users
            .equals(roles.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Roles{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", permissions=" + permissions +
            ", user=" + users +
            '}';
    }
}
