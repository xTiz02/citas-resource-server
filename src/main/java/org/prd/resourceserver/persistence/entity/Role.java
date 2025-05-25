package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prd.resourceserver.util.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "auth_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Override
    public String getAuthority() {
        return name.name();
    }


//    public void addUserRole(UserRoleEntity user_role) {
//        user_roles.add(user_role);
//        user_role.setRole(this);
//    }
//
//    public void removeUserRole(UserRoleEntity user_role) {
//        user_roles.remove(user_role);
//        user_role.setRole(null);
//    }

}

//For @ManyToMany associations, the REMOVE entity state transition doesn't make sense to be cascaded because it will propagate beyond the link table.