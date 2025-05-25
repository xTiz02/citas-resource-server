package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private Date birthDate;
    private String email;
    private String phone;
    private String address;
    private boolean enabled;
    private Date creationDate;
    private Date updateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "patient",fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();



}