package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.util.AppointmentStatus;
import org.springframework.data.annotation.CreatedBy;

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

    private LocalDate date;
    private LocalDate cancelDate;

    private String startTime;

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

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    private boolean isCancelled;
    private boolean isRescheduled;

    @OneToMany(mappedBy = "originalAppointment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RescheduledAppointment> rescheduledAppointments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_create_id")
    @CreatedBy
    private User createdBy;

    private boolean enabled;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_cancel_id")
//    private User cancelledBy;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_reschedule_id")
//    private User rescheduledBy;

    public Appointment(Long id) {
        this.id = id;
    }
}