package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Long> {

    List<Computer> findByMemberUserId(Long userId);
    List<Computer> findByComputerId(Long computerId);
    
}
