package com.Jobly.entity;

import com.Jobly.enums.JobStatus;
import com.Jobly.enums.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long JobId;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    private String description;
    private String location;
    private double budget;
    private int duration;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    private User worker;
}
