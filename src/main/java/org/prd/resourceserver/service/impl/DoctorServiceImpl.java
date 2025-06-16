package org.prd.resourceserver.service.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.prd.resourceserver.persistence.dto.CreateDoctorDto;
import org.prd.resourceserver.persistence.dto.DoctorPageDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.persistence.entity.Specialty;
import org.prd.resourceserver.persistence.entity.User;
import org.prd.resourceserver.persistence.repository.DoctorRepository;
import org.prd.resourceserver.persistence.repository.SpecialtyRepository;
import org.prd.resourceserver.persistence.repository.UserRepository;
import org.prd.resourceserver.service.DoctorService;
import org.prd.resourceserver.util.GenderEnum;
import org.prd.resourceserver.util.mapper.DoctorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

  private final DoctorRepository doctorRepository;
  private final UserRepository userRepository;
  private final SpecialtyRepository specialtyRepository;

  public DoctorServiceImpl(DoctorRepository doctorRepository, UserRepository userRepository,
      SpecialtyRepository specialtyRepository) {
    this.doctorRepository = doctorRepository;
    this.userRepository = userRepository;
    this.specialtyRepository = specialtyRepository;
  }

  @Override
  public PageResponse<DoctorPageDto> getAllDoctors(Pageable pageable) {
    Page<Doctor> page = doctorRepository.findAll(pageable);

    return new PageResponse<>(
        page.getContent().stream().map(DoctorMapper::toPageDto).toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isLast(),
        page.isFirst()
    );
  }

  @Override
  public DoctorPageDto getDoctorById(Long id) {
    return doctorRepository.findById(id)
        .map(DoctorMapper::toPageDto)
        .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
  }

  @Override
  public DoctorPageDto createDoctor(CreateDoctorDto createDoctorDto) {

    Doctor doctor = Doctor.builder()
        .dni(createDoctorDto.dni())
        .firstName(createDoctorDto.firstName())
        .lastName(createDoctorDto.lastName())
        .email(createDoctorDto.email())
        .birthDate(createDoctorDto.birthDate())
        .licenseNumber(createDoctorDto.licenseNumber())
        .gender(createDoctorDto.gender())
        .user(new User(createDoctorDto.userId()))
        .phone(createDoctorDto.phone()).build();

    Set<Specialty> specialties = new HashSet<>(
        specialtyRepository.findAllById(createDoctorDto.specialties()));
    doctor.setSpecialties(specialties);

    return DoctorMapper.toPageDto(doctorRepository.save(doctor));
  }

  @Override
  public DoctorPageDto updateDoctor(CreateDoctorDto updateDoctorDto) {
    return null;
  }

  @Override
  public void deleteDoctorById(Long id) {
    Doctor doctor = doctorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    doctor.setEnabled(false);
    doctorRepository.save(doctor);
  }
}