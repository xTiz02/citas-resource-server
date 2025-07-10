package org.prd.resourceserver.service.implPatient;

import java.util.UUID;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.prd.resourceserver.persistence.dto.CreateUserDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.persistence.entity.User;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.entity.FamilyMemberPatient;
import org.prd.resourceserver.persistence.patient.entity.UserPatient;
import org.prd.resourceserver.persistence.patient.repository.FamilyMemberRepository;
import org.prd.resourceserver.persistence.patient.repository.UserPatientRepository;
import org.prd.resourceserver.persistence.repository.UserRepository;
import org.prd.resourceserver.service.impl.UserServiceImpl;
import org.prd.resourceserver.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserPatientImpl {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserServiceImpl userServiceImpl;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserPatientRepository patientRepository;
  @Autowired
  private FamilyMemberRepository familyMemberRepository;


  public ApiResponse<UserPatient> getPatientByUsername(String username) {
    log.info("Fetching patient by username: " + username);
    User userPatient = userRepository.findByUsername(username).orElse(null);
    UserPatient patient = patientRepository.findByUserId(userPatient.getId());

    return new ApiResponse<>(
        patient != null ? "Paciente encontrado" : "Paciente no encontrado",
        null,
        patient,
        true
    );
  }

  @Transactional
  public ApiResponse<UserPatient> updatePatient(UserPatient userPatient) {
    log.info("Updating patient with ID: " + userPatient.getId());
    UserPatient existingPatient = patientRepository.findById(userPatient.getId())
        .orElseThrow(
            () -> new RuntimeException("Paciente no encontrado con ID: " + userPatient.getId()));
    existingPatient.setNombres(userPatient.getNombres());
    existingPatient.setApellidoPaterno(userPatient.getApellidoPaterno());
    existingPatient.setApellidoMaterno(userPatient.getApellidoMaterno());
    existingPatient.setNumeroCelular(userPatient.getNumeroCelular());
    existingPatient.setCorreoElectronico(userPatient.getCorreoElectronico());
    existingPatient.setPeso(userPatient.getPeso());
    existingPatient.setAltura(userPatient.getAltura());

    UserPatient updatedPatient = patientRepository.save(existingPatient);
    log.info("Patient updated successfully with ID: " + updatedPatient.getId());
    return new ApiResponse<>(
        "Paciente actualizado correctamente",
        null,
        updatedPatient,
        true
    );
  }

  @Transactional
  public ApiResponse<UserPatient> savePatient(UserPatient userPatient) {
    log.info(
        "Saving new patient: " + userPatient.getNombres() + " " + userPatient.getApellidoPaterno());
    String password = userPatient.getPassword();
    if (password != null) {
      password = passwordEncoder.encode(password);
    }
    CreateUserDto createUserDto = new CreateUserDto(
        null,
        userPatient.getNumeroDocumento(),
        password,
        RoleEnum.ROLE_PATIENT,
        false,
        true
    );
    UserPageDto user = userServiceImpl.createUser(createUserDto);

    User userEntity = userRepository.findByUsername(user.username()).orElse(null);
    userPatient.setUserId(userEntity.getId());
    userPatient.setFamilyCode(UUID.randomUUID().toString());
    UserPatient savedPatient = patientRepository.save(userPatient);

    FamilyMemberPatient familyMember = new FamilyMemberPatient();
    familyMember.setFamilyCode(userPatient.getFamilyCode());
    familyMember.setUserId(savedPatient.getUserId());
    familyMember.setParentesco(null);
    familyMember.setTipoDocumento(userPatient.getTipoDocumento());
    familyMember.setNumeroDocumento(userPatient.getNumeroDocumento());
    familyMember.setApellidoPaterno(userPatient.getApellidoPaterno());
    familyMember.setApellidoMaterno(userPatient.getApellidoMaterno());
    familyMember.setNombres(userPatient.getNombres());
    familyMember.setGenero(userPatient.getGenero());
    familyMember.setFechaNacimiento(userPatient.getFechaNacimiento());
    familyMember.setNumeroCelular(userPatient.getNumeroCelular());
    familyMember.setCorreoElectronico(userPatient.getCorreoElectronico());

    familyMemberRepository.save(familyMember);

    return new ApiResponse<>(
        "Paciente guardado correctamente",
        null,
        savedPatient,
        true
    );
  }
}
