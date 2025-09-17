package com.coverletter.coverletter.entity;

import com.coverletter.coverletter.entity.enums.RecruitmentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // JPA에서 엔터티의 PK값을 자동으로 생성. DB에서 자동 증가 방식으로 PK를 생성
    private Long recruitId;

    @Enumerated(EnumType.STRING)
    private RecruitmentType recruitmentType;    // 구분 - 신입, 경력

    private String recruitmentPart;         // 모집부문 - 개발,...  

    private String salaryRequirement;       // 희망연봉 - String형태
}
