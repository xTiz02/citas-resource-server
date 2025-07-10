package org.prd.resourceserver.persistence.patient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
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
public class AppoinmentPatient {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long patientId;
  private String patientType; // considerar Enum si solo hay "titular", "familiar", etc.
  private String specialty;
  private Long doctorId;
  private String doctorName;
  private LocalDate date;
  private String time; // puede ser LocalTime si lo prefieres
  private String status;
  private String consultorio;
  private Double price;
  @CreationTimestamp
  private Date createdAt;
  @UpdateTimestamp
  private Date updatedAt;
}