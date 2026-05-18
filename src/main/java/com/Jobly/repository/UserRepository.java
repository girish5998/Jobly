package com.Jobly.repository;

import com.Jobly.entity.User;
import com.Jobly.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRoleAndApproved(Role role, boolean approved);

    List<User> findByRole(Role role);

    long countByRole(Role role);

    long countByRoleAndApproved(Role role, boolean approved);
}
