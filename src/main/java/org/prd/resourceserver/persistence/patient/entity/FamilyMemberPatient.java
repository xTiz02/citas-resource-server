package org.prd.resourceserver.persistence.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.util.PatientType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FamilyMemberPatient {

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  private Long userId; //patient
  private String parentesco;
  private String familyCode;
  private String tipoDocumento;
  @Transient
  private PatientType patientType; // This field is not persisted in the database
  @Column(unique = true)
  private String numeroDocumento;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String nombres;
  private LocalDate fechaNacimiento;
  private String numeroCelular;
  private String correoElectronico;
  private String genero;
  @CreationTimestamp
  private Date createdAt;
  @UpdateTimestamp
  private Date updatedAt;
}