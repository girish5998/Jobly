package com.Jobly.controller;


import com.Jobly.enums.JobStatus;
import com.Jobly.service.JobService;
import com.Jobly.service.UserService;
import com.Jobly.dto.JobResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @GetMapping("/pending-jobs/{workerId}")
    public ResponseEntity<List<JobResponseDTO>> getPendingJobs(@PathVariable Long workerId){
        return ResponseEntity.ok(jobService.getPendingJobs(workerId));
    }

    @PutMapping("/accept/{jobId}/{workerId}")
    public ResponseEntity<JobResponseDTO> acceptJob(@PathVariable Long jobId,
                                         @PathVariable Long workerId){
        return ResponseEntity.ok(jobService.acceptJob(jobId,workerId));
    }

    @PutMapping("/complete/{jobId}")
    public ResponseEntity<JobResponseDTO> completeJob(@PathVariable Long jobId){
        return ResponseEntity.ok(jobService.completeJob(jobId));
    }

    @GetMapping("/completed-jobs/{workerId}")
    public ResponseEntity<List<JobResponseDTO>> getCompletedJobs(@PathVariable Long workerId) {
        return ResponseEntity.ok(jobService.getCompletedJobsByWorker(workerId, JobStatus.COMPLETED));

    }


}
