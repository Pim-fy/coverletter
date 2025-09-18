package com.coverletter.coverletter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Career;

public interface CareerRepository extends JpaRepository<Career, Long> {

    List<Career> findByMemberUserId(Long userId);
    Optional<Career> findByCareerId(Long careerId);
    
}
