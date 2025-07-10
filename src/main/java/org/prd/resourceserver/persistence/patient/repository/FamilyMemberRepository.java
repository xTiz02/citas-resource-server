package org.prd.resourceserver.persistence.patient.repository;

import java.util.List;
import org.prd.resourceserver.persistence.patient.entity.FamilyMemberPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FamilyMemberRepository extends JpaRepository<FamilyMemberPatient,Long> {

  @Query("""
  select fmp from FamilyMemberPatient fmp
  where fmp.familyCode = :familyCode
""")
  List<FamilyMemberPatient> getFamilyMembersByFamilyCode(String familyCode);
}
