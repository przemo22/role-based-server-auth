package com.vis.server.backend.domain.repository;

import com.vis.server.backend.domain.model.Roles;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Roles, Long> {

    @Modifying
    @Query("update Roles r set r.name = :name where r.id = :id")
    int updateRoles(@Param(value = "id") long id, @Param(value = "name") String name);
}
