package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Career;

public interface CareerRepository extends JpaRepository<Career, Long> {

    List<Career> findByMemberUserId(Long userId);
    List<Career> findByCareerId(Long careerId);
    
}
