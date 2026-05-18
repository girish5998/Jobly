package com.Jobly.controller;

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

    @GetMapping("/completed-jobs/{ownerId}")
    public ResponseEntity<List<JobResponseDTO>> getCompletedJobs(@PathVariable Long ownerId){
        return ResponseEntity.ok(jobService.getCompletedJobsByOwner(ownerId, JobStatus.COMPLETED));
}}
