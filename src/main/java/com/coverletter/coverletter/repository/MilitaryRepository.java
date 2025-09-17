package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Military;

public interface MilitaryRepository extends JpaRepository<Military, Long> {
    
    List<Military> findByMemberUserId(Long userId);
    List<Military> findByMilitaryId(Long militaryId);
}
