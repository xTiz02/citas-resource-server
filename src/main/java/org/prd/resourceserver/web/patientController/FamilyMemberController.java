package org.prd.resourceserver.web.patientController;

import java.util.List;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.entity.FamilyMemberPatient;
import org.prd.resourceserver.persistence.patient.repository.FamilyMemberRepository;
import org.prd.resourceserver.service.implPatient.FamilyMemberImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/family-members")
public class FamilyMemberController {

  @Autowired
  private FamilyMemberImpl familyMemberImpl;

  @Autowired
  private FamilyMemberRepository familyMemberRepository;

  @GetMapping("/{username}")
  public ApiResponse<List<FamilyMemberPatient>> getFamilyMembersByPatient(
      @PathVariable String username) {
    try {
      return familyMemberImpl.getFamilyMembersByPatient(username);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al obtener los miembros de la familia: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @PostMapping("/{username}")
  public ApiResponse<FamilyMemberPatient> saveFamilyMember(
      @RequestBody FamilyMemberPatient familyMember, @PathVariable String username) {
    try {
      return familyMemberImpl.saveFamilyMember(familyMember, username);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al guardar el miembro de la familia: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @PutMapping("/{username}/{id}")
  public ApiResponse<FamilyMemberPatient> updateFamilyMember(
      @RequestBody FamilyMemberPatient familyMember, @PathVariable String username,
      @PathVariable Long id) {
    try {
      return familyMemberImpl.updateFamilyMember(familyMember);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al actualizar el miembro de la familia: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @DeleteMapping("/{username}/{id}")
  public ApiResponse<FamilyMemberPatient> deleteFamilyMember(
      @PathVariable String username,
      @PathVariable Long id) {
    try {
      return familyMemberImpl.deleteFamilyMember(id);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al eliminar el miembro de la familia: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }
}
