package com.coverletter.coverletter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Recruit;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    
    List<Recruit> findByMemberUserId(Long userId);
    Optional<Recruit> findByRecruitId(Long recruitId);
}
