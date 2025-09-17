package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    
    List<Training> findByMemberUserId(Long userId);
    List<Training> findByTrainingId(Long trainingId);
}
