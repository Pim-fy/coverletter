package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Qualification;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    
    List<Qualification> findByMemberUserId(Long userId);
    List<Qualification> findByQualificationId(Long qualificationId);
}
