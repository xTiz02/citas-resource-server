package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prd.resourceserver.util.GenderEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dni;
    private String firstName;
    private String lastName;
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String phone;
    private String specialty;
    private String licenseNumber;
    private boolean enabled;
    //Lista de especialidades
    @ManyToMany
    @JoinTable(
            name = "doctor_specialty",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties = new HashSet<>();

}