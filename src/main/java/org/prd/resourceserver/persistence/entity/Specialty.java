package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "specialty_ubication",
            joinColumns = @JoinColumn(name = "specialty_id"),
            inverseJoinColumns = @JoinColumn(name = "ubication_id")
    )
    private Set<Location> locations = new HashSet<>();

    @ManyToMany(mappedBy = "specialties")
    private Set<Doctor> doctors = new HashSet<>();

    public Specialty(Long id) {
        this.id = id;
    }

//    @ManyToMany(mappedBy = "specialties")
//    private Set<Doctor> doctors = new HashSet<>();
}