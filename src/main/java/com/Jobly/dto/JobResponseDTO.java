package com.Jobly.dto;

import com.Jobly.enums.JobStatus;
import com.Jobly.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDTO {
    private long JobId;
    private ServiceType serviceType;
    private String description;
    private String location;
    private double budget;
    private int duration;
    private JobStatus status;
    private String ownerName;
    private String workerName;
}
