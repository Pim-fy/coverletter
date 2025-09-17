package com.coverletter.coverletter.entity;

import com.coverletter.coverletter.entity.enums.TrainingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // JPA에서 엔터티의 PK값을 자동으로 생성. DB에서 자동 증가 방식으로 PK를 생성
    private Long trainingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    private String trainingStartDate;       // 교육 기간(시작일)

    private String trainingEndDate;         // 교육 기간(종료일)

    private String trainingName;            // 교육 과정명

    private String trainingContent;         // 교육 내용

    private String trainingCompany;         // 교육 기관
    
    @Enumerated(EnumType.STRING)
    private TrainingStatus trainingStatus;  // 수료 여부(교육 상태)
}
