package org.prd.resourceserver.persistence.repository;


import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select new org.prd.resourceserver.persistence.dto.UserPageDto(" +
            "u.username, u.account_locked, u.enabled, u.role.name, u.createdAt, u.updatedAt) " +
            "from User u")
    Page<UserPageDto> findAllUsers(Pageable pageable);
}