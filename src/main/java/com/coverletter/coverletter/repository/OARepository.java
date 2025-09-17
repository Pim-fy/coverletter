package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.OA;

public interface OARepository extends JpaRepository<OA, Long> {
    
    List<OA> findByMemberUserId(Long userId);
    List<OA> findByLanguage(Long languageId);
}
