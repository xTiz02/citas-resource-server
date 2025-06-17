package org.prd.resourceserver.web.controller;

import org.prd.resourceserver.persistence.dto.CreateSpecialtyDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.PatientPageDto;
import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.entity.Specialty;
import org.prd.resourceserver.service.PatientService;
import org.prd.resourceserver.service.SpecialtyService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("specialty")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<SpecialtyPageDto>> getAllSpecialities(Pageable pageable) {
        return ResponseEntity.ok(specialtyService.getAllSpecialties(pageable));
    }

    @GetMapping("/all/{doctorId}")
    public ResponseEntity<List<SpecialtyPageDto>> getAllSpecialitiesByEnabledAndDoctor(@PathVariable Long doctorId, boolean enabled) {
        return ResponseEntity.ok(specialtyService.getAllSpecialtiesByEnabledAndDoctor(doctorId, enabled));
    }

    @PostMapping("/create")
    public ResponseEntity<SpecialtyPageDto> createSpecialty(@RequestBody  CreateSpecialtyDto specialty) {
        return ResponseEntity.ok(specialtyService.createSpecialty(specialty));
    }

    @PutMapping("/update")
    public ResponseEntity<SpecialtyPageDto> updateSpecialty(@RequestBody CreateSpecialtyDto specialty) {
        return ResponseEntity.ok(specialtyService.updateSpecialty(specialty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyPageDto> getSpecialtyById(@PathVariable Long id) {
        return ResponseEntity.ok(specialtyService.getSpecialtyById(id));
    }

}