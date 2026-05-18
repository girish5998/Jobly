package com.Jobly.service;

import com.Jobly.entity.User;
import com.Jobly.enums.JobStatus;
import com.Jobly.enums.Role;
import com.Jobly.repository.JobRepository;
import com.Jobly.repository.UserRepository;
import com.Jobly.dto.AdminDashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    public User approveWorker(Long workerId) {
        User worker = userRepository.findById(workerId).orElseThrow();
        if(worker.getRole()!=Role.WORKER){
            throw new RuntimeException("only workers can be approved");
        }
        worker.setApproved(true);
        return userRepository.save(worker);
    }

    public List<User> getPendingWorkers(){
        return userRepository.findByRoleAndApproved(Role.WORKER,false);
    }

    public List<User> getAllWorkers(){
        return userRepository.findByRole(Role.WORKER);
    }

    public User blockWorker(Long workerId){
        User worker = userRepository.findById(workerId).orElseThrow();
        worker.setBlocked(true);
        return userRepository.save(worker);
    }

    public AdminDashboardDTO getDashboardStatus(){
        long totalWorkers = userRepository.countByRole(Role.WORKER);
        long approvedWorkers = userRepository.countByRoleAndApproved(Role.WORKER,true);
        long pendingWorkers = userRepository.countByRoleAndApproved(Role.WORKER,false);
        long totalJobs = jobRepository.count();
        long pendingJobs = jobRepository.countByStatus(JobStatus.PENDING);
        long completedJobs = jobRepository.countByStatus(JobStatus.COMPLETED);
        return new AdminDashboardDTO(
                totalWorkers,
                approvedWorkers,
                pendingWorkers,
                totalJobs,
                pendingJobs,
                completedJobs
        );
    }

}
