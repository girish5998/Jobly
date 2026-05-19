package com.Jobly.controller;

import com.Jobly.dto.OwnerDashboardDTO;
import com.Jobly.entity.Job;
import com.Jobly.enums.JobStatus;
import com.Jobly.service.JobService;
import com.Jobly.service.UserService;
import com.Jobly.dto.JobResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @PostMapping("/post-job/{ownerId}")
    public ResponseEntity<JobResponseDTO> postJob(@PathVariable Long ownerId , @RequestBody Job job){
        return ResponseEntity.ok(jobService.postJob(ownerId,job));
    }

    @GetMapping("/jobs/{ownerId}")
    public ResponseEntity<List<JobResponseDTO>> getJobs(@PathVariable Long ownerId){
        return ResponseEntity.ok(jobService.getJobsByOwner(ownerId));
    }

    @GetMapping("/assigned-jobs/{ownerId}")
    public ResponseEntity<List<JobResponseDTO>> getAssignedJobs(@PathVariable Long ownerId){
        return ResponseEntity.ok(jobService.getAssignedJobs(ownerId));
    }


    @PutMapping("/update-job/{jobId}/{ownerId}")
    public ResponseEntity<JobResponseDTO> updateJob(@PathVariable Long jobId,@PathVariable Long ownerId,@RequestBody Job job){
        return ResponseEntity.ok(jobService.updateJob(jobId,ownerId,job));

    }

    @PutMapping("/cancel-job/{jobId}/{ownerId}")
    public ResponseEntity<JobResponseDTO> cancelJob(@PathVariable Long jobId,@PathVariable Long ownerId){
        return ResponseEntity.ok(jobService.cancelJob(jobId,ownerId));

    }

    @GetMapping("/completed-jobs/{ownerId}")
    public ResponseEntity<List<JobResponseDTO>> getCompletedJobs(@PathVariable Long ownerId){
        return ResponseEntity.ok(jobService.getCompletedJobsByOwner(ownerId, JobStatus.COMPLETED));
    }

    @DeleteMapping("/delete-jobs/{jobId}/{ownerId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId,@PathVariable Long ownerId){
        return ResponseEntity.ok(jobService.deleteJob(jobId,ownerId));
    }
    @GetMapping("/dashboard/{ownerId}")
    public ResponseEntity<OwnerDashboardDTO> getDashboardDTO(@PathVariable Long ownerId){
        return ResponseEntity.ok(jobService.getOwnerDashboard(ownerId));
    }


}
