package com.Jobly.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDashboardDTO {
    private long totalJobs;
    private long pendingJobs;
    private long completedJobs;
    private long assignedJobs;
    private long cancelledJobs;
}
