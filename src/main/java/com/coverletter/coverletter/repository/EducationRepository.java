package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
    
    List<Education> findByMemberUserId(Long userId);
    List<Education> findByEducationId(Long educationId);
}
