package com.vis.server.backend.domain.repository;

import com.vis.server.backend.domain.model.Permission;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    long deleteByRoleId(long role_id);

    Set<Permission> findByRoleId(long role_id);
}
