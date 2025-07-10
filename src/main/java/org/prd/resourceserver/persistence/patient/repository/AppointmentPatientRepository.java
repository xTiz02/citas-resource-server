package org.prd.resourceserver.persistence.patient.repository;

import java.util.List;
import org.prd.resourceserver.persistence.patient.entity.AppointmentPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentPatientRepository extends JpaRepository<AppointmentPatient, Long> {

  List<AppointmentPatient> findAllByPatientId(Long patientId);

  @Query("""
    select ap from AppointmentPatient ap where ap.status in ('programada', 'pendiente','reprogramada')
""")
  List<AppointmentPatient> getUpcomingAppointmentsByPatientId(Long patientId);
}
