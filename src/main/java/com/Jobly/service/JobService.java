package com.Jobly.service;

import com.Jobly.dto.OwnerDashboardDTO;
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

    public String deleteJob(Long jobId, Long ownerId) {
        Job job = jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Job not found"));

        if(job.getOwner().getId()!= ownerId){
            throw new RuntimeException("You can delete only your jobs");
        }
        jobRepository.delete(job);
        return "Job deleted successfully";
    }

    public JobResponseDTO updateJob(Long jobId, Long ownerId, Job updatedJob) {
        Job existingJob = jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Job not found"));

        if(existingJob.getOwner().getId()!=ownerId){
            throw new RuntimeException("You can update only your job");
        }
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setBudget(updatedJob.getBudget());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setDuration(updatedJob.getDuration());
        Job savedJob = jobRepository.save(existingJob);
        return convertToDTO(savedJob);
    }
    private JobResponseDTO convertToDTO(Job job){
        JobResponseDTO dto = new JobResponseDTO();
        dto.setJobId(job.getJobId());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setBudget(job.getBudget());
        dto.setDuration(job.getDuration());
        dto.setServiceType(job.getServiceType());
        dto.setStatus(job.getStatus());
        dto.setOwnerName(job.getOwner().getName());
        return dto;
    }
    public JobResponseDTO cancelJob(Long jobId, Long ownerId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getOwner().getId() != ownerId) {
            throw new RuntimeException("You can Delete only your job");
        }
        job.setStatus(JobStatus.CANCELLED);
        Job savedJob = jobRepository.save(job);

        return convertToDTO(savedJob);
    }

    public List<JobResponseDTO> getAssignedJobs(Long ownerId){
        List<Job> jobs = jobRepository.findByOwnerIdAndStatus(ownerId,JobStatus.ASSIGNED);
        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public OwnerDashboardDTO getOwnerDashboard(Long ownerId){
        OwnerDashboardDTO dto = new OwnerDashboardDTO();
        dto.setTotalJobs(jobRepository.countByOwnerId(ownerId));
        dto.setPendingJobs(jobRepository.countByOwnerIdAndStatus(ownerId,JobStatus.PENDING));
        dto.setAssignedJobs(jobRepository.countByOwnerIdAndStatus(ownerId,JobStatus.ASSIGNED));
        dto.setCompletedJobs(jobRepository.countByOwnerIdAndStatus(ownerId,JobStatus.COMPLETED));
        dto.setCompletedJobs(jobRepository.countByOwnerIdAndStatus(ownerId,JobStatus.CANCELLED));
        return dto;
    }


}


