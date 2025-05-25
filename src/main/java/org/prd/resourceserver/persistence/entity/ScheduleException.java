package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prd.resourceserver.util.TurnEnum;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "schedule_exception")
public class ScheduleException {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateException;

    private TurnEnum turn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private DoctorSchedule schedule;
}