package org.prd.resourceserver.persistence.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserPatient {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private Long userId;
  private String familyCode;
  private String tipoDocumento;
  @Column(unique = true)
  private String numeroDocumento;
  private String apellidoPaterno;
  private String apellidoMaterno;
  @Transient
  private String password;
  private String nombres;
  private LocalDate fechaNacimiento;
  private String numeroCelular;
  private String correoElectronico;
  private String genero;
  private String peso;
  private String altura;
  private String direccion;
  private String contactoEmergencia;
  private String telefonoEmergencia;
  @CreationTimestamp
  private Date createdAt;
  @UpdateTimestamp
  private Date updatedAt;
}