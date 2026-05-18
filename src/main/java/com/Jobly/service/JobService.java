package com.Jobly.service;

import com.Jobly.entity.Job;
import com.Jobly.entity.User;
import com.Jobly.enums.JobStatus;
import com.Jobly.enums.Role;
import com.Jobly.repository.JobRepository;
import com.Jobly.repository.UserRepository;
import com.Jobly.dto.JobResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    public List<Job> viewAllJobs;
    @Autowired
 private JobRepository jobRepository;

 @Autowired
 private UserRepository userRepository;

    public JobResponseDTO postJob(Long ownerId, Job job) {
        User owner= userRepository.findById(ownerId).orElseThrow();
        if(owner.getRole()!= Role.OWNER){
            throw new RuntimeException("Only owner can post jobs");
        }
        job.setOwner(owner);
        job.setStatus(JobStatus.PENDING);
        Job savedJob = jobRepository.save(job);
        return mapToDto(savedJob);
    }
    private JobResponseDTO mapToDto(Job job){
        return new JobResponseDTO(
                job.getJobId(),
                job.getServiceType(),
                job.getDescription(),
                job.getLocation(),
                job.getBudget(),
                job.getDuration(),
                job.getStatus(),
                job.getOwner().getName(),
                job.getWorker()!= null ? job.getWorker().getName():null
        );
    }

    public List<JobResponseDTO> getPendingJobs(Long workerId) {
        User worker = userRepository.findById(workerId).orElseThrow(()-> new RuntimeException("worker not found"));
        List<Job> jobs= jobRepository.findByStatusAndServiceType(JobStatus.PENDING,worker.getServiceType());
        return jobs.stream()
                .map(this::mapToDto)
                .toList();
    }

    public JobResponseDTO acceptJob(Long jobId, Long workerId) {
        Job job = jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Worker not found"));
        User worker = userRepository.findById(workerId).orElseThrow(()-> new RuntimeException("Worker not found"));
        if(worker.getRole()!= Role.WORKER){
            throw new RuntimeException("Only worker can accept jobs");
        }
        if(job.getStatus() != JobStatus.PENDING){
            throw new RuntimeException("Job already assigned");
        }
        if(job.getServiceType()!= worker.getServiceType()){
            throw new RuntimeException("ServiceType mismatch");
        }

        if(!worker.isAvailable()){
            throw new RuntimeException("Worker not available");
        }

        if(!worker.isApproved()){
            throw new RuntimeException("Worker not approved by admin");
        }

        if(!worker.isBlocked()){
            throw new RuntimeException("Worker is Blocked by admin");
        }

        job.setWorker(worker);
        job.setStatus(JobStatus.ASSIGNED);
        worker.setAvailable(false);
        Job savedJob = jobRepository.save(job);
       return mapToDto(savedJob);
    }


    public List<JobResponseDTO> getJobsByOwner(Long ownerId) {
        List<Job> jobs = jobRepository.findByOwnerId(ownerId);
        return jobs.stream()
                .map(this::mapToDto)
                .toList();
    }

    public JobResponseDTO completeJob(Long jobId){
        Job job = jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Job not found"));
        if(job.getStatus()!= JobStatus.ASSIGNED){
            throw new RuntimeException("only assigned jobs can be completed");
        }
        job.setStatus(JobStatus.COMPLETED);

        User worker = job.getWorker();
        worker.setAvailable(true);
        Job savedJob = jobRepository.save(job);
        return mapToDto(savedJob);
    }



    public List<JobResponseDTO> getCompletedJobsByOwner(Long ownerId,JobStatus jobStatus) {
        List<Job> jobs = jobRepository.findByOwnerIdAndStatus(ownerId,JobStatus.COMPLETED);
        return jobs.stream()
                .map(this::mapToDto)
                .toList();
    }
    public List<JobResponseDTO> getCompletedJobsByWorker(Long workerId,JobStatus jobStatus) {
        List<Job> jobs = jobRepository.findByWorkerIdAndStatus(workerId,JobStatus.COMPLETED);
        return jobs.stream()
                .map(this::mapToDto)
                .toList();
    }
}


