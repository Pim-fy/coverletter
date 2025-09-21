package com.coverletter.coverletter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    
    List<Language> findByMemberUserId(Long userId);
    Optional<Language> findByLanguageId(Long languageId);
}
