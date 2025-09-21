package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.coverletter.coverletter.repository.PrizeRepository;

import jakarta.transaction.Transactional;

import com.coverletter.coverletter.dto.PrizeDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.entity.Prize;
import com.coverletter.coverletter.repository.MemberRepository;

@Service
public class PrizeService {
    private final PrizeRepository prizeRepository;
    private final MemberRepository memberRepository;

    public PrizeService(PrizeRepository prizeRepository, MemberRepository memberRepository) {
        this.prizeRepository = prizeRepository;
        this.memberRepository = memberRepository;
    }

    // 수상 경력 생성
    @Transactional
    public PrizeDto.CreateResponse createResponse(Long userId, PrizeDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (!memberOpt.isPresent()) {
            return new PrizeDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }
        
        Member member = memberOpt.get();

        Prize prize = new Prize();
        prize.setMember(member);
        prize.setPrizeName(request.getPrizeName());
        prize.setPrizeCompany(request.getPrizeCompany());
        prize.setPrizeYear(request.getPrizeYear());
        prize.setEtc(request.getEtc());
        prizeRepository.save(prize);

        return new PrizeDto.CreateResponse(true, "수상 정보 생성 완료", prize.getPrizeId());
    }

    // 수상 경력 조회
    public PrizeDto.ReadResponse readResponse(Long userId) {
        List<Prize> prizeList = prizeRepository.findByMemberUserId(userId);
        PrizeDto.ReadResponse response = new PrizeDto.ReadResponse();
        if(prizeList != null && !prizeList.isEmpty()) {
            List<PrizeDto.ReadResponse.PrizeInfo> prizeInfos = prizeList.stream()
            .map(prize -> new PrizeDto.ReadResponse.PrizeInfo(
                prize.getPrizeId(),
                prize.getPrizeName(),
                prize.getPrizeCompany(),
                prize.getPrizeYear(),
                prize.getEtc()
            ))
            .collect(Collectors.toList());
        
            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setPrizes(prizeInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setPrizes(Collections.emptyList());
        }
        return response;
    }

    // 수상 경력 수정
    @Transactional
    public PrizeDto.UpdateResponse updateResponse(Long userId, PrizeDto.UpdateRequest dto) {
        Optional<Prize> prizeOpt = prizeRepository.findByPrizeId(dto.getPrizeId());
        if(prizeOpt.isPresent()) {
            Prize prize = prizeOpt.get();

            if(!prize.getMember().getUserId().equals(userId)) {
                return new PrizeDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getPrizeName() != null) {prize.setPrizeName(dto.getPrizeName());}
            if(dto.getPrizeCompany() != null) {prize.setPrizeCompany(dto.getPrizeCompany());}
            if(dto.getPrizeYear() != null) {prize.setPrizeYear(dto.getPrizeYear());}
            if(dto.getEtc() != null) {prize.setEtc(dto.getEtc());}
            prizeRepository.save(prize);
            return new PrizeDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new PrizeDto.UpdateResponse(false, "수정 실패.");
        }
    }

    // 수상 경력 삭제
    @Transactional
    public PrizeDto.DeleteResponse deleteResponse(Long userId, Long prizeId) {
        Optional<Prize> prizeOpt = prizeRepository.findByPrizeId(prizeId);

        if(prizeOpt.isPresent()) {
            Prize prize = prizeOpt.get();

            if (!prize.getMember().getUserId().equals(userId)) {
                return new PrizeDto.DeleteResponse(false, "권한이 없습니다.");
            }

            prizeRepository.delete(prize);
            return new PrizeDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new PrizeDto.DeleteResponse(false, "삭제 실패");
        }
    }
}
