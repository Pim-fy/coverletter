package com.coverletter.coverletter.service;

import com.coverletter.coverletter.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final MemberService memberService;
    private final RecruitService recruitService;
    private final EducationService educationService;
    private final CareerService careerService;
    private final PrizeService prizeService;
    private final QualificationService qualificationService;
    private final LanguageService languageService; 
    private final OAService oaService;
    private final ComputerService computerService;
    private final MilitaryService militaryService;
    private final TrainingService trainingService;

    public ResumeDto getFullResume(Long userId) {
        ResumeDto resumeDto = new ResumeDto();

        // 각 Service의 readResponse 메소드를 호출하여 데이터를 가져온다
        resumeDto.setMemberInfo(memberService.readResponse(userId));
        resumeDto.setRecruitInfo(recruitService.readResponse(userId).getRecruits());
        resumeDto.setEducationList(educationService.readResponse(userId).getEducation());
        resumeDto.setCareerList(careerService.readResponse(userId).getCareers());
        resumeDto.setPrizeList(prizeService.readResponse(userId).getPrizes());
        resumeDto.setQualificationList(qualificationService.readResponse(userId).getQualifications());
        resumeDto.setLanguageList(languageService.readResponse(userId).getLanguages());
        resumeDto.setOaList(oaService.readResponse(userId).getOas());
        resumeDto.setComputerList(computerService.readResponse(userId).getComputers());
        resumeDto.setMilitaryList(militaryService.readResponse(userId).getMilitaries());
        resumeDto.setTrainingList(trainingService.readResponse(userId).getTrainings());
        
        return resumeDto;
    }
}