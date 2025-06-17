package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.Role;
import org.prd.resourceserver.util.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

  Role findByName(RoleEnum name);

}
