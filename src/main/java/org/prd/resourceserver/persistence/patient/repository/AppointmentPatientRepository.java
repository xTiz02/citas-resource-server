package org.prd.resourceserver.persistence.patient.repository;

import java.util.List;
import org.prd.resourceserver.persistence.patient.entity.AppointmentPatient;
import org.prd.resourceserver.util.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentPatientRepository extends JpaRepository<AppointmentPatient, Long> {

  List<AppointmentPatient> findAllByPatientId(Long patientId);

  @Query("""
    select ap from AppointmentPatient ap where ap.status in :statuses
    and ap.patientId = ?1
""")
  List<AppointmentPatient> getUpcomingAppointmentsByPatientId(Long patientId, List<AppointmentStatus> statuses);
}