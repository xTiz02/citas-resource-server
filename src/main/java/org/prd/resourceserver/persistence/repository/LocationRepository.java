package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {

    List<Location> findAllByEnabled(boolean enabled);
}