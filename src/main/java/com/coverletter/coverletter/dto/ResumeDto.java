package com.coverletter.coverletter.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResumeDto {

    private MemberDto.ReadResponse memberInfo;
    private List<RecruitDto.ReadResponse.RecruitInfo> recruitInfo;
    private List<EducationDto.ReadResponse.EducationInfo> educationList;
    private List<CareerDto.ReadResponse.CareerInfo> careerList;
    private List<PrizeDto.ReadResponse.PrizeInfo> prizeList;
    private List<QualificationDto.ReadResponse.QualificationInfo> qualificationList;
    private List<LanguageDto.ReadResponse.LanguageInfo> languageList;
    private List<OADto.ReadResponse.OAInfo> oaList;
    private List<ComputerDto.ReadResponse.ComputerInfo> computerList;
    private List<MilitaryDto.ReadResponse.MilitaryInfo> militaryList;
    private List<TrainingDto.ReadResponse.TrainingInfo> trainingList;

}