package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.QualificationDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.entity.Qualification;
import com.coverletter.coverletter.repository.MemberRepository;
import com.coverletter.coverletter.repository.QualificationRepository;

import jakarta.transaction.Transactional;

@Service
public class QualificationService {
    private final QualificationRepository qualificationRepository;
    private final MemberRepository memberRepository;

    public QualificationService(QualificationRepository qualificationRepository, MemberRepository memberRepository) {
        this.qualificationRepository = qualificationRepository;
        this.memberRepository = memberRepository;
    }

    // 자격 정보 생성
    @Transactional
    public QualificationDto.CreateResponse createResponse(Long userId, QualificationDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (!memberOpt.isPresent()) {
            return new QualificationDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }
        
        Member member = memberOpt.get();

        Qualification qualification = new Qualification();
        qualification.setMember(member);
        qualification.setQualificationName(request.getQualificationName());
        qualification.setQualificationDate(request.getQualificationDate());
        qualification.setQualificationCompany(request.getQualificationCompany());
        qualificationRepository.save(qualification);

        return new QualificationDto.CreateResponse(true, "자격 정보 생성 완료", qualification.getQualificationId());
    }

    // 자격 정보 조회
    public QualificationDto.ReadResponse readResponse(Long userId) {
        List<Qualification> qualificationList = qualificationRepository.findByMemberUserId(userId);
        QualificationDto.ReadResponse response = new QualificationDto.ReadResponse();
        if(qualificationList != null && !qualificationList.isEmpty()) {
            List<QualificationDto.ReadResponse.QualificationInfo> qualificationInfos = qualificationList.stream()
            .map(qualification -> new QualificationDto.ReadResponse.QualificationInfo(
                qualification.getQualificationName(),
                qualification.getQualificationDate(),
                qualification.getQualificationCompany()
            ))
            .collect(Collectors.toList());

            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setQualifications(qualificationInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setQualifications(Collections.emptyList());
        }
        return response;
    }
    
    // 자격 정보 수정
    @Transactional
    public QualificationDto.UpdateResponse updateResponse(Long userId, QualificationDto.UpdateRequest dto) {
        Optional<Qualification> qualificationOpt = qualificationRepository.findByQualificationId(dto.getQualificationId());
        if(qualificationOpt.isPresent()) {
            Qualification qualification = qualificationOpt.get();

            if(!qualification.getMember().getUserId().equals(userId)) {
                return new QualificationDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getQualificationName() != null) {qualification.setQualificationName(dto.getQualificationName());}
            if(dto.getQualificationDate() != null) {qualification.setQualificationDate(dto.getQualificationDate());}
            if(dto.getQualificationCompany() != null) {qualification.setQualificationCompany(dto.getQualificationCompany());}
            qualificationRepository.save(qualification);
            return new QualificationDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new QualificationDto.UpdateResponse(false, "수정 실패.");
        }
    }

    // 자격 정보 삭제
    @Transactional
    public QualificationDto.DeleteResponse deleteResponse(Long userId, Long qualificationId) {
        Optional<Qualification> qualificationOpt = qualificationRepository.findByQualificationId(qualificationId);

        if(qualificationOpt.isPresent()) {
            Qualification qualification = qualificationOpt.get();

            if (!qualification.getMember().getUserId().equals(userId)) {
                return new QualificationDto.DeleteResponse(false, "권한이 없습니다.");
            }

            qualificationRepository.delete(qualification);
            return new QualificationDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new QualificationDto.DeleteResponse(false, "삭제 실패");
        }
    }
}
