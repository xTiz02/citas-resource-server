package org.prd.resourceserver.service.implPatient;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.prd.resourceserver.persistence.entity.User;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.entity.FamilyMemberPatient;
import org.prd.resourceserver.persistence.patient.entity.UserPatient;
import org.prd.resourceserver.persistence.patient.repository.FamilyMemberRepository;
import org.prd.resourceserver.util.PatientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FamilyMemberImpl {

  @Autowired
  private FamilyMemberRepository familyMemberRepository;

  @Autowired
  private UserPatientImpl userPatientImpl;


  public ApiResponse<List<FamilyMemberPatient>> getFamilyMembersByPatient(String username) {
    log.info("Fetching family members for user ID: " + username);
    UserPatient userPatient = userPatientImpl.getPatientByUsername(username).data();
    List<FamilyMemberPatient> familyMembers = familyMemberRepository.getFamilyMembersByFamilyCode(
        userPatient.getFamilyCode());
    familyMembers.stream().forEach(f -> {
          if (f.getUserId() == userPatient.getId()) {
            f.setPatientType(PatientType.titular);
          } else {
            f.setPatientType(PatientType.familiar);
          }
        }
    );
    if (familyMembers.isEmpty()) {
      return new ApiResponse<>("No family members found for user ID: " + username, null, null,
          true);
    }

    return new ApiResponse<>("Family members found", null, familyMembers, true);
  }

  public ApiResponse<FamilyMemberPatient> saveFamilyMember(FamilyMemberPatient familyMember,
      String username) {
    log.info("Saving family member: " + familyMember);
    UserPatient userPatient = userPatientImpl.getPatientByUsername(username).data();
    familyMember.setFamilyCode(userPatient.getFamilyCode());
    familyMember.setUserId(null);
    FamilyMemberPatient savedFamilyMember = familyMemberRepository.save(familyMember);
    return new ApiResponse<>("Family member saved successfully", null, savedFamilyMember, true);
  }

  public ApiResponse<FamilyMemberPatient> updateFamilyMember(FamilyMemberPatient familyMember) {
    FamilyMemberPatient member = familyMemberRepository.findById(familyMember.getId()).orElse(null);
    if (member == null) {
      return new ApiResponse<>("Family member not found", null, null, false);
    }
    if (member.getUserId() != null) {
      return new ApiResponse<>("Family member cannot be updated as it is linked to a user", null,
          null, false);
    }

    if (familyMember.getGenero() != null) {
      member.setGenero(familyMember.getGenero());
    }

    if (familyMember.getNombres() != null) {
      member.setNombres(familyMember.getNombres());
    }

    if (familyMember.getApellidoMaterno() != null) {
      member.setApellidoPaterno(familyMember.getApellidoPaterno());
    }

    if (familyMember.getApellidoPaterno() != null) {
      member.setApellidoMaterno(familyMember.getApellidoMaterno());
    }

    if (familyMember.getFechaNacimiento() != null) {
      member.setFechaNacimiento(familyMember.getFechaNacimiento());
    }

    if (familyMember.getParentesco() != null) {
      member.setParentesco(familyMember.getParentesco());
    }

    if (familyMember.getNumeroCelular() != null) {
      member.setNumeroCelular(familyMember.getNumeroCelular());
    }

    if (familyMember.getNumeroDocumento() != null) {
      member.setNumeroDocumento(familyMember.getNumeroDocumento());
    }

    if (familyMember.getCorreoElectronico() != null) {
      member.setCorreoElectronico(familyMember.getCorreoElectronico());
    }

    if (familyMember.getTipoDocumento() != null) {
      member.setTipoDocumento(familyMember.getTipoDocumento());
    }

    FamilyMemberPatient updatedFamilyMember = familyMemberRepository.save(member);
    return new ApiResponse<>("Family member updated successfully", null, updatedFamilyMember, true);
  }

  @Transactional
  public ApiResponse<FamilyMemberPatient> deleteFamilyMember(Long id) {
    FamilyMemberPatient member = familyMemberRepository.findById(id).orElse(null);

    if (member == null) {
      return new ApiResponse<>("Family member not found", null, null, false);
    }
    if (member.getUserId() != null) {
      member.setFamilyCode(UUID.randomUUID().toString()); // Unlink the family member from the user
      familyMemberRepository.save(member);
    } else {
      familyMemberRepository.delete(member);
    }

    return new ApiResponse<>("Family member deleted successfully", null, member, true);
  }
}
