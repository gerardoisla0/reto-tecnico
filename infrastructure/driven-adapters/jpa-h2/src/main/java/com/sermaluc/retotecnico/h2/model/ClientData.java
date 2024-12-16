package com.sermaluc.retotecnico.h2.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder(toBuilder = true)
@Table(name = "CLIENT")
@NoArgsConstructor
@AllArgsConstructor
public class ClientData {

    @Id
    @Column(name = "ID", unique = true)
    private String id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "MODIFIED")
    private LocalDateTime modified;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "IS_ACTIVE")
    private boolean active;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PhoneData> phoneList = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        setId(java.util.UUID.randomUUID().toString());
    }
}
