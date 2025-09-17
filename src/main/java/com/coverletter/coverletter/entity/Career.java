package com.coverletter.coverletter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // JPA에서 엔터티의 PK값을 자동으로 생성. DB에서 자동 증가 방식으로 PK를 생성
    private Long careerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    private String company;             // 회사명

    private String careerStartDate;     // 근무 기간(시작일)

    private String careerEndDate;       // 근무 기간(종료일)

    private String careerTask;          // 담당 업무
    
    private String reasonForLeaving;    // 퇴사 사유
}
