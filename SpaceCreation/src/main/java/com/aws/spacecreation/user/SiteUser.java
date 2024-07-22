package com.aws.spacecreation.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity@Data
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
