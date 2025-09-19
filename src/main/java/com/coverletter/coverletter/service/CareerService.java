package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.CareerDto;
import com.coverletter.coverletter.entity.Career;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.CareerRepository;
import com.coverletter.coverletter.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class CareerService {
    private final CareerRepository careerRepository;
    private final MemberRepository memberRepository;

    public CareerService(CareerRepository careerRepository, MemberRepository memberRepository) {
        this.careerRepository = careerRepository;
        this.memberRepository = memberRepository;
    }

    // 경력 정보 생성
    @Transactional
    public CareerDto.CreateResponse createResponse(Long userId, CareerDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (!memberOpt.isPresent()) {
            return new CareerDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }
        
        Member member = memberOpt.get();

        Career career = new Career();
        career.setMember(member);
        career.setCompany(request.getCompany());
        career.setCareerStartDate(request.getCareerStartDate());
        career.setCareerEndDate(request.getCareerEndDate());
        career.setCareerTask(request.getCareerTask());
        career.setReasonForLeaving(request.getReasonForLeaving());
        careerRepository.save(career);

        return new CareerDto.CreateResponse(true, "경력 정보 생성 완료", career.getCareerId());

    }


    // 경력 정보 조회
    public CareerDto.ReadResponse readResponse(Long userId) {
        List<Career> careerList = careerRepository.findByMemberUserId(userId);
        CareerDto.ReadResponse response = new CareerDto.ReadResponse();
        if(careerList != null && !careerList.isEmpty()) {
            List<CareerDto.ReadResponse.CareerInfo> careerInfos = careerList.stream()
            .map(career -> new CareerDto.ReadResponse.CareerInfo(
                career.getCompany(),
                career.getCareerStartDate(),
                career.getCareerEndDate(),
                career.getCareerTask(),
                career.getReasonForLeaving()
            ))
            .collect(Collectors.toList());
        
            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setCareers(careerInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setCareers(Collections.emptyList());
        }
        return response;
    }

    // 경력 정보 수정
    @Transactional
    public CareerDto.UpdateResponse updateResponse(Long userId, CareerDto.UpdateRequest dto) {
        Optional<Career> careerOpt = careerRepository.findByCareerId(dto.getCareerId());
        if(careerOpt.isPresent()) {
            Career career = careerOpt.get();

            if(!career.getMember().getUserId().equals(userId)) {
                return new CareerDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getCompany() != null) {career.setCompany(dto.getCompany());}
            if(dto.getCareerStartDate() != null) {career.setCareerStartDate(dto.getCareerStartDate());}
            if(dto.getCareerEndDate() != null) {career.setCareerEndDate(dto.getCareerEndDate());}
            if(dto.getCareerTask() != null) {career.setCareerTask(dto.getCareerTask());}
            if(dto.getReasonForLeaving() != null) {career.setReasonForLeaving(dto.getReasonForLeaving());}
            careerRepository.save(career);
            return new CareerDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new CareerDto.UpdateResponse(false, "수정 실패.");
        }
    }

    // 경력 정보 삭제
    @Transactional
    public CareerDto.DeleteResponse deleteResponse(Long userId, Long careerId) {
        Optional<Career> careerOpt = careerRepository.findByCareerId(careerId);

        if(careerOpt.isPresent()) {
            Career career = careerOpt.get();

            if (!career.getMember().getUserId().equals(userId)) {
                return new CareerDto.DeleteResponse(false, "권한이 없습니다.");
            }

            careerRepository.delete(career);
            return new CareerDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new CareerDto.DeleteResponse(false, "삭제 실패");
        }
    }

}
