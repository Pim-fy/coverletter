package com.coverletter.coverletter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coverletter.coverletter.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserId(Long userId);
    List<Member> findByName(Long name);
    List<Member> findByPhoneNumber(Long phoneNumber);
    List<Member> findByEmail(Long email);
}
