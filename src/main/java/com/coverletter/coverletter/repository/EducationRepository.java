package com.coverletter.coverletter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
    
    List<Education> findByMemberUserId(Long userId);
    Optional<Education> findByEducationId(Long educationId);
}
