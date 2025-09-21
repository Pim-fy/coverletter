package com.coverletter.coverletter.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Long> {

    List<Computer> findByMemberUserId(Long userId);
    Optional<Computer> findByComputerId(Long computerId);
    
    void deleteByMemberUserId(Long userId);
}
