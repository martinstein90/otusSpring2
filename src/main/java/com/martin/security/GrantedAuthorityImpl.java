package com.martin.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class GrantedAuthorityImpl implements GrantedAuthority {

    public static final String FILED_ROLE = "role";

    @Enumerated(EnumType.STRING)
    @Column(name = FILED_ROLE)
    private Role role;

    public String getAuthority(){
        return role.name();
    }

}
