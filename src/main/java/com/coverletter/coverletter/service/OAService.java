package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.OADto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.entity.OA;
import com.coverletter.coverletter.repository.MemberRepository;
import com.coverletter.coverletter.repository.OARepository;

import jakarta.transaction.Transactional;

@Service
public class OAService {
    private final OARepository oaRepository;
    private final MemberRepository memberRepository;

    public OAService(OARepository oaRepository, MemberRepository memberRepository) {
        this.oaRepository = oaRepository;
        this.memberRepository = memberRepository;
    }

    // 컴퓨터능력 정보 생성
    @Transactional
    public OADto.CreateResponse createResponse(Long userId, OADto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if(!memberOpt.isPresent()) {
            return new OADto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }

        Member member = memberOpt.get();

        OA oa = new OA();
        oa.setMember(member);
        oa.setHancom(request.getHancom());
        oa.setExcel(request.getExcel());
        oa.setPowerpoint(request.getPowerpoint());
        oa.setEtc(request.getEtc());
        oaRepository.save(oa);

        return new OADto.CreateResponse(true, "컴퓨터능력 정보 생성 완료", oa.getOaId());
    }

    // 컴퓨터능력 정보 조회
    public OADto.ReadResponse readResponse(Long userId) {
        List<OA> oaList = oaRepository.findByMemberUserId(userId);
        OADto.ReadResponse response = new OADto.ReadResponse();
        if(oaList != null && !oaList.isEmpty()) {
            List<OADto.ReadResponse.OAInfo> oaInfos = oaList.stream()
            .map(oa -> new OADto.ReadResponse.OAInfo(
                oa.getHancom(),
                oa.getExcel(),
                oa.getPowerpoint(),
                oa.getEtc()
            ))
            .collect(Collectors.toList());

            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setOas(oaInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setOas(Collections.emptyList());
        }
        return response;
    }

    // 컴퓨터능력 정보 수정
    @Transactional
    public OADto.UpdateResponse updateResponse(Long userId, OADto.UpdateRequest dto) {
        Optional<OA> oaOpt = oaRepository.findByOaId(dto.getOaId());
        if(oaOpt.isPresent()) {
            OA oa = oaOpt.get();

            if(!oa.getMember().getUserId().equals(userId)) {
                return new OADto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getHancom() != null) {oa.setHancom(dto.getHancom());}
            if(dto.getExcel() != null) {oa.setExcel(dto.getExcel());}
            if(dto.getPowerpoint() != null) {oa.setPowerpoint(dto.getPowerpoint());}
            if(dto.getEtc() != null) {oa.setEtc(dto.getEtc());}
            oaRepository.save(oa);
            return new OADto.UpdateResponse(true, "수정 완료.");
        } else {
            return new OADto.UpdateResponse(false, "수정 실패.");
        }
    }

    // 컴퓨터능력 정보 삭제
    @Transactional
    public OADto.DeleteResponse deleteResponse(Long userId, Long oaId) {
        Optional<OA> oaOpt = oaRepository.findByOaId(oaId);

        if(oaOpt.isPresent()) {
            OA oa = oaOpt.get();

            if(!oa.getMember().getUserId().equals(userId)) {
                return new OADto.DeleteResponse(false, "권한이 없습니다.");
            }

            oaRepository.delete(oa);
            return new OADto.DeleteResponse(true, "삭제 완료.");
        } else {
            return new OADto.DeleteResponse(false, "삭제 실패.");
        }
    }
}
