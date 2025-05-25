package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.persistence.entity.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    @Query("select new org.prd.resourceserver.persistence.dto.SpecialtyPageDto(" +
            "s.id, s.name, s.creationDate, s.updateDate, s.enabled) " +
            "from specialty s")
    Page<SpecialtyPageDto> findAllSpecialties(Pageable pageable);
}