package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "specialty")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date creationDate;
    private Date updateDate;

    private boolean enabled;

//    @ManyToMany(mappedBy = "specialties")
//    private Set<Doctor> doctors = new HashSet<>();
}