package com.sermaluc.retotecnico.h2.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PHONE")
public class PhoneData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String citycode;
    private String contrycode;
}