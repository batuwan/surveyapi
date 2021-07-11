package com.batu.surveyapi.Core.Entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Options")
public class Option {

    @Id
    @Column(name = "option_id")
    @GeneratedValue
    private Long id;
    @NotNull
    private String description;
    private Long count;
}
