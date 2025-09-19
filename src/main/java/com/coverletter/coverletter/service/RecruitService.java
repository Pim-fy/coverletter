package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.coverletter.coverletter.dto.RecruitDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.entity.Recruit;
import com.coverletter.coverletter.repository.MemberRepository;
import com.coverletter.coverletter.repository.RecruitRepository;

import jakarta.transaction.Transactional;

@Service
public class RecruitService {
    private final RecruitRepository recruitRepository;
    private final MemberRepository memberRepository;

    public RecruitService(RecruitRepository recruitRepository, MemberRepository memberRepository){
        this.recruitRepository = recruitRepository;
        this.memberRepository = memberRepository;
    }
    
    // 지원 정보 생성
    @Transactional
    public RecruitDto.CreateResponse createResponse(Long userId, RecruitDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (!memberOpt.isPresent()) {
            return new RecruitDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }

        Member member = memberOpt.get();

        Recruit recruit = new Recruit();
        recruit.setMember(member);
        recruit.setRecruitmentType(request.getRecruitmentType());
        recruit.setRecruitmentPart(request.getRecruitmentPart());
        recruit.setSalaryRequirement(request.getSalaryRequirement());
        recruitRepository.save(recruit);

        return new RecruitDto.CreateResponse(true, "지원 정보 생성 완료", recruit.getRecruitId());
    }

    // 지원 정보 조회
    public RecruitDto.ReadResponse readResponse(Long userId) {
        List<Recruit> recruitList = recruitRepository.findByMemberUserId(userId);
        RecruitDto.ReadResponse response = new RecruitDto.ReadResponse();
        if(recruitList != null && !recruitList.isEmpty()) {
            List<RecruitDto.ReadResponse.RecruitInfo> recruitInfos = recruitList.stream()
            .map(recruit -> new RecruitDto.ReadResponse.RecruitInfo(
                recruit.getRecruitmentType(),
                recruit.getRecruitmentPart(),
                recruit.getSalaryRequirement()    
            ))
            .collect(Collectors.toList());
        
            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setRecruits(recruitInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setRecruits(Collections.emptyList());
        }
        return response;

    }

    // 지원 정보 수정
    @Transactional
    public RecruitDto.UpdateResponse updateResponse(Long userId, RecruitDto.UpdateRequest dto) {
        Optional<Recruit> recruitOpt = recruitRepository.findByRecruitId(dto.getRecruitId());
        if(recruitOpt.isPresent()) {
            Recruit recruit = recruitOpt.get();

            if(!recruit.getMember().getUserId().equals(userId)) {
                return new RecruitDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getRecruitmentType() != null) {recruit.setRecruitmentType(dto.getRecruitmentType());}
            if(dto.getRecruitmentPart() != null) {recruit.setRecruitmentPart(dto.getRecruitmentPart());}
            if(dto.getSalaryRequirement() != null) {recruit.setSalaryRequirement(dto.getSalaryRequirement());}
            recruitRepository.save(recruit);
            return new RecruitDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new RecruitDto.UpdateResponse(false, "수정 실패.");
        }
    }
    // 지원 정보 삭제
    @Transactional
    public RecruitDto.DeleteResponse deleteResponse(Long userId, Long recruitId) {
        Optional<Recruit> recruitOpt = recruitRepository.findByRecruitId(recruitId);

        if(recruitOpt.isPresent()) {
            Recruit recruit = recruitOpt.get();

            if (!recruit.getMember().getUserId().equals(userId)) {
                return new RecruitDto.DeleteResponse(false, "권한이 없습니다.");
            }

            recruitRepository.delete(recruit);
            return new RecruitDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new RecruitDto.DeleteResponse(false, "삭제 실패");
        }
    }
}
