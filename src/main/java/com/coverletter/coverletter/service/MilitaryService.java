package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.coverletter.coverletter.dto.MilitaryDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.entity.Military;
import com.coverletter.coverletter.repository.MemberRepository;
import com.coverletter.coverletter.repository.MilitaryRepository;

import jakarta.transaction.Transactional;

public class MilitaryService {
    private final MilitaryRepository militaryRepository;
    private final MemberRepository memberRepository;
    
    public MilitaryService(MilitaryRepository militaryRepository, MemberRepository memberRepository) {
        this.militaryRepository = militaryRepository;
        this.memberRepository = memberRepository;
    }
    
    // 병역 정보 생성
    @Transactional
    public MilitaryDto.CreateResponse createResponse(Long userId, MilitaryDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if(!memberOpt.isPresent()) {
            return new MilitaryDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }

        Member member = memberOpt.get();

        Military military = new Military();
        military.setMember(member);
        military.setMilitaryRank(request.getMilitaryRank());
        military.setMilitaryBranch(request.getMilitaryBranch());
        military.setMilitaryDischarge(request.getMilitaryDischarge());
        military.setMilitaryStartDate(request.getMilitaryStartDate());
        military.setMilitaryEndDate(request.getMilitaryEndDate());
        militaryRepository.save(military);

        return new MilitaryDto.CreateResponse(true, "병역 정보 생성 완료", military.getMilitaryId());
    }

    // 병역 정보 조회
    public MilitaryDto.ReadResponse readResponse(Long userId) {
        List<Military> militaryList = militaryRepository.findByMemberUserId(userId);
        MilitaryDto.ReadResponse response = new MilitaryDto.ReadResponse();
        if(militaryList != null && !militaryList.isEmpty()) {
            List<MilitaryDto.ReadResponse.MilitaryInfo> militaryInfos = militaryList.stream()
            .map(military -> new MilitaryDto.ReadResponse.MilitaryInfo(
                military.getMilitaryStatus(),
                military.getMilitaryRank(),
                military.getMilitaryBranch(),
                military.getMilitaryDischarge(),
                military.getMilitaryStartDate(),
                military.getMilitaryEndDate()
            ))
            .collect(Collectors.toList());

            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setMilitaries(militaryInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setMilitaries(Collections.emptyList());
        }
        return response;
    }

    // 병역 정보 수정
    @Transactional
    public MilitaryDto.UpdateResponse updateResponse(Long userId, MilitaryDto.UpdateRequest dto) {
        Optional<Military> militaryOpt = militaryRepository.findByMilitaryId(dto.getMilitaryId());
        if(militaryOpt.isPresent()) {
            Military military = militaryOpt.get();

            if(!military.getMember().getUserId().equals(userId)) {
                return new MilitaryDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getMilitaryStatus() != null) {military.setMilitaryStatus(dto.getMilitaryStatus());}
            if(dto.getMilitaryRank() != null) {military.setMilitaryRank(dto.getMilitaryRank());}
            if(dto.getMilitaryBranch() != null) {military.setMilitaryBranch(dto.getMilitaryBranch());}
            if(dto.getMilitaryDischarge() != null) {military.setMilitaryDischarge(dto.getMilitaryDischarge());}
            if(dto.getMilitaryStartDate() != null) {military.setMilitaryStartDate(dto.getMilitaryStartDate());}
            if(dto.getMilitaryEndDate() != null) {military.setMilitaryEndDate(dto.getMilitaryEndDate());}
            militaryRepository.save(military);
            return new MilitaryDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new MilitaryDto.UpdateResponse(true, "수정 실패.");
        }
    }

    // 병역 정보 삭제
    @Transactional
    public MilitaryDto.DeleteResponse deleteResponse(Long userId, Long militaryId) {
        Optional<Military> militaryOpt = militaryRepository.findByMilitaryId(militaryId);

        if(militaryOpt.isPresent()) {
            Military military = militaryOpt.get();

            if(!military.getMember().getUserId().equals(userId)) {
                return new MilitaryDto.DeleteResponse(false, "권한이 없습니다.");
            }

            militaryRepository.delete(military);
            return new MilitaryDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new MilitaryDto.DeleteResponse(false, "삭제 실패");
        }
    }
}
