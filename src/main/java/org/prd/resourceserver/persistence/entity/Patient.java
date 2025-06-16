package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.util.GenderEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String dni;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String documentType;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String address;
    private boolean enabled;
    @CreationTimestamp
    private Date creationDate;
    @UpdateTimestamp
    private Date updateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "patient",fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    public Patient(Long id) {
        this.id = id;
    }

}