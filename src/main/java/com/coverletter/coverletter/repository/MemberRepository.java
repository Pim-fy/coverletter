package com.coverletter.coverletter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(Long userId);
    Optional<Member> findByLoginId(String loginId);
    List<Member> findByName(String name);
    List<Member> findByPhoneNumber(String phoneNumber);
    List<Member> findByEmail(String email);
}
