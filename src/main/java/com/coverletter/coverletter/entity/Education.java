package com.coverletter.coverletter.entity;

import com.coverletter.coverletter.entity.enums.EducationStatus;
import com.coverletter.coverletter.entity.enums.EducationType;
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
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // JPA에서 엔터티의 PK값을 자동으로 생성. DB에서 자동 증가 방식으로 PK를 생성
    private Long educationId;

    private String educationStartDate;      // 입학일
    
    private String educationEndDate;        // 졸업일
    
    @Enumerated(EnumType.STRING)
    private EducationStatus educationStatus;    // 상태 

    @Enumerated(EnumType.STRING)
    private EducationType educationType;    // 고등, 대학교 구분

    private String educationSchoolName;     // 학교명

    private String educationMajor;          // 전공

    private String educationGrade;          // 학점

    private String educationLocation;       // 소재지

    private String absenceStartDate;        // 휴학 기간(시작일)

    private String absenceEndDate;          // 휴학 기간(종료일)

    private String absenceReason;           // 휴학 사유
}
