package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prd.resourceserver.util.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Date cancelDate;
    private Date rescheduleDate;

    private String startTime;
    private int duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private DoctorSchedule schedule;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private Date createdAt;
    private Date updatedAt;

    private boolean isCancelled;
    private boolean isRescheduled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_create_id")
    private User createdBy;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_cancel_id")
//    private User cancelledBy;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_reschedule_id")
//    private User rescheduledBy;
}