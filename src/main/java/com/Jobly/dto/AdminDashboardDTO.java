package com.Jobly.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDTO {

    private long totalWorkers;
    private long approvedWorkers;
    private long pendingWorkers;
    private long totalJobs;
    private long pendingJobs;
    private long completedJobs;

}
