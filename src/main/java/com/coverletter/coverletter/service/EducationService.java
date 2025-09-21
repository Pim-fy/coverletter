package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.EducationDto;
import com.coverletter.coverletter.entity.Education;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.EducationRepository;
import com.coverletter.coverletter.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class EducationService {
    private final EducationRepository educationRepository;
    private final MemberRepository memberRepository;

    public EducationService(EducationRepository educationRepository, MemberRepository memberRepository) {
        this.educationRepository = educationRepository;
        this.memberRepository = memberRepository;
    }

    // 학력 정보 생성
    @Transactional
    public EducationDto.CreateResponse createResponse(Long userId, EducationDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if(!memberOpt.isPresent()) {
            return new EducationDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }

        Member member = memberOpt.get();

        Education education = new Education();
        education.setMember(member);
        education.setEducationStartDate(request.getEducationStartDate());
        education.setEducationEndDate(request.getEducationEndDate());
        education.setEducationStatus(request.getEducationStatus());
        education.setEducationType(request.getEducationType());
        education.setEducationSchoolName(request.getEducationSchoolName());
        education.setEducationMajor(request.getEducationMajor());
        education.setEducationGrade(request.getEducationGrade());
        education.setEducationLocation(request.getEducationLocation());
        education.setAbsenceStartDate(request.getAbsenceStartDate());
        education.setAbsenceEndDate(request.getAbsenceEndDate());
        education.setAbsenceReason(request.getAbsenceReason());
        educationRepository.save(education);

        return new EducationDto.CreateResponse(true, "학력 정보 생성 완료", education.getEducationId());
    }

    // 학력 정보 조회
    public EducationDto.ReadResponse readResponse(Long userId) {
        List<Education> educationList = educationRepository.findByMemberUserId(userId);
        EducationDto.ReadResponse response = new EducationDto.ReadResponse();
        if(educationList != null && !educationList.isEmpty()) {
            List<EducationDto.ReadResponse.EducationInfo> educationInfos = educationList.stream()
            .map(education -> new EducationDto.ReadResponse.EducationInfo(
                education.getEducationId(),
                education.getEducationStartDate(),
                education.getEducationEndDate(),
                education.getEducationStatus(),
                education.getEducationType(),
                education.getEducationSchoolName(),
                education.getEducationMajor(),
                education.getEducationGrade(),
                education.getEducationLocation(),
                education.getAbsenceStartDate(),
                education.getAbsenceEndDate(),
                education.getAbsenceReason()
            ))
            .collect(Collectors.toList());

            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setEducation(educationInfos);
        }
        else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setEducation(Collections.emptyList());
        }
        return response;
    }

    // 학력 정보 수정
    @Transactional
    public EducationDto.UpdateResponse updateResponse(Long userId, EducationDto.UpdateRequest dto) {
        Optional<Education> educationOpt = educationRepository.findByEducationId(dto.getEducationId());
        if(educationOpt.isPresent()) {
            Education education = educationOpt.get();
            if(!education.getMember().getUserId().equals(userId)) {
                return new EducationDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getEducationStartDate() != null) {education.setEducationStartDate(dto.getEducationStartDate());}
            if(dto.getEducationEndDate() != null) {education.setEducationEndDate(dto.getEducationEndDate());}
            if(dto.getEducationStatus() != null) {education.setEducationStatus(dto.getEducationStatus());}
            if(dto.getEducationType() != null) {education.setEducationType(dto.getEducationType());}
            if(dto.getEducationSchoolName() != null) {education.setEducationSchoolName(dto.getEducationSchoolName());}
            if(dto.getEducationMajor() != null) {education.setEducationMajor(dto.getEducationMajor());}
            if(dto.getEducationGrade() != null) {education.setEducationGrade(dto.getEducationGrade());}
            if(dto.getEducationLocation() != null) {education.setEducationLocation(dto.getEducationLocation());}
            if(dto.getAbsenceStartDate() != null) {education.setAbsenceStartDate(dto.getAbsenceStartDate());}
            if(dto.getAbsenceEndDate() != null) {education.setAbsenceEndDate(dto.getAbsenceEndDate());}
            if(dto.getAbsenceReason() != null) {education.setAbsenceReason(dto.getAbsenceReason());}
            educationRepository.save(education);

            return new EducationDto.UpdateResponse(true, "수정 완료");
        } else {
            return new EducationDto.UpdateResponse(false, "수정 실패");
        }

    }

    // 학력 정보 삭제
    @Transactional
    public EducationDto.DeleteResponse deleteResponse(Long userId, Long educationId) {
        Optional<Education> educationOpt = educationRepository.findByEducationId(educationId);

        if(educationOpt.isPresent()) {
            Education education = educationOpt.get();

            if(!education.getMember().getUserId().equals(userId)) {
                return new EducationDto.DeleteResponse(false, "권한이 없습니다.");
            }

            educationRepository.delete(education);
            return new EducationDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new EducationDto.DeleteResponse(false, "삭제 실패");
        }
    }
}
