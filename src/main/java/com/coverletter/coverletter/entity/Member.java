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
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // JPA에서 엔터티의 PK값을 자동으로 생성. DB에서 자동 증가 방식으로 PK를 생성
    private Long userId;

    private String password;

    private String name;

    private String phoneNumber;

    private String emergencyPhoneNumber;

    private String address;

    private String email;

    private String dateOfBirth;

    private String profileImagePath;    // 사진 저장 경로
}
