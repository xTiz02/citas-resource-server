package org.prd.resourceserver.persistence.patient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.util.AppointmentStatus;
import org.prd.resourceserver.util.PatientType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppointmentPatient {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long patientId; //family member id
  private String specialty;
  private Long doctorId;
  private String doctorName;
  private LocalDate date;
  private String time; // puede ser LocalTime si lo prefieres
  private AppointmentStatus status;
  private String consultorio;
  private BigDecimal price;
  @CreationTimestamp
  private Date createdAt;
  @UpdateTimestamp
  private Date updatedAt;
}