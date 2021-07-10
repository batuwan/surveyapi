package com.batu.surveyapi.Core.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue
    private int id;

    private String name;



}
