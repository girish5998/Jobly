package com.Jobly.repository;

import com.Jobly.entity.Job;
import com.Jobly.enums.JobStatus;
import com.Jobly.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobRepository extends JpaRepository<Job, Long>{

    List<Job> findByWorkerIdAndStatus(Long workerId,JobStatus jobStatus);
    List<Job> findByOwnerIdAndStatus(Long ownerId,JobStatus jobStatus);
    List<Job> findByOwnerId(Long ownerId);
    List<Job> findByStatus(JobStatus status);
    List<Job> findByStatusAndServiceType(JobStatus status,ServiceType serviceType);
    long countByStatus(JobStatus status);





}

