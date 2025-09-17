package com.coverletter.coverletter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMemberDto {
    
    private String password;

    private String name;

    private String phoneNumber;
}
