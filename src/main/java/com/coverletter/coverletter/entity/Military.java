package com.coverletter.coverletter.entity;

import com.coverletter.coverletter.entity.enums.MilitaryStatus;
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
public class Military {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // JPA에서 엔터티의 PK값을 자동으로 생성. DB에서 자동 증가 방식으로 PK를 생성
    private Long militaryId;

    @Enumerated(EnumType.STRING)
    private MilitaryStatus militaryStatus;      // 병역구분.

    private String militaryRank;        // 계급

    private String militaryBranch;      // 병과

    private String militaryDischarge;   // 제대구분 // enum 변경 예정

    private String militaryStartDate;   // 복무기간(시작일)

    private String militaryEndDate;     // 복무기간(종료일)
}
