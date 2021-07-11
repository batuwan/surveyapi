package com.batu.surveyapi.Core.Entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Questions")
public class Question {

    @Id
    @Column(name = "question_id")
    @GeneratedValue
    private Long id;
    @NotNull
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Option> options = new ArrayList<>();
}
