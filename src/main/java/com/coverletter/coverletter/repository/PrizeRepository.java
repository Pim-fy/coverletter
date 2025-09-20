package com.coverletter.coverletter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Prize;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
    List<Prize> findByMemberUserId(Long userId);
    Optional<Prize> findByPrizeId(Long prizeId);
}
