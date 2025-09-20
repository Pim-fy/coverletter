package com.coverletter.coverletter.entity;

import jakarta.persistence.Entity;
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
public class Prize {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // JPA에서 엔터티의 PK값을 자동으로 생성. DB에서 자동 증가 방식으로 PK를 생성
    private Long prizeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    private String PrizeName;       // 수상 내용
    private String PrizeCompany;    // 수상처
    private String PrizeYear;       // 수상 년도
    private String Etc;             // 비고
}
