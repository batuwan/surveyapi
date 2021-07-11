package com.batu.surveyapi.Core.Entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Topics")
public class Topic {

    @Id
    @Column(name = "topic_id")
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    private boolean isApproved;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id")
    private List<Option> options = new ArrayList<>();
}
