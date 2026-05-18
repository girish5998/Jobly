package com.Jobly.controller;

import com.Jobly.entity.User;
import com.Jobly.service.JobService;
import com.Jobly.service.UserService;
import com.Jobly.dto.AdminDashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private JobService jobService;


    @Autowired
    private UserService userService;

    @PutMapping("/approve-worker/{workerId}")
    public ResponseEntity<User> approveWorker(@PathVariable Long workerId){
        return ResponseEntity.ok(userService.approveWorker(workerId));
    }

    @GetMapping("/pending-workers")
    public ResponseEntity<List<User>> getPendingWorkers(){
        return ResponseEntity.ok(userService.getPendingWorkers());
    }

    @GetMapping("/workers")
    public ResponseEntity<List<User>> getAllWorkers(){
        return ResponseEntity.ok(userService.getAllWorkers());
    }

    @PutMapping("/block-worker/{workerId}")
    public ResponseEntity<User> blockWorker(@PathVariable Long workerId){
        return ResponseEntity.ok(userService.blockWorker(workerId));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardDTO> getDashboard(){
        return ResponseEntity.ok(userService.getDashboardStatus());
    }

}
