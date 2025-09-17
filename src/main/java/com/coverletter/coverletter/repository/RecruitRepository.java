package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Recruit;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    
    List<Recruit> findByMemberUserId(Long userId);
    List<Recruit> findByRecruitId(Long recruitId);
}
