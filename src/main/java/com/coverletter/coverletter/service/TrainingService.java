package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.coverletter.coverletter.dto.TrainingDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.entity.Training;
import com.coverletter.coverletter.repository.MemberRepository;
import com.coverletter.coverletter.repository.TrainingRepository;
import jakarta.transaction.Transactional;

public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final MemberRepository memberRepository;

    public TrainingService(TrainingRepository trainingRepository, MemberRepository memberRepository) {
        this.trainingRepository = trainingRepository;
        this.memberRepository = memberRepository;
    }

    // 교육 정보 생성
    @Transactional
    public TrainingDto.CreateResponse createResponse(Long userId, TrainingDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (!memberOpt.isPresent()) {
            return new TrainingDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }
        
        Member member = memberOpt.get();

        Training training = new Training();
        training.setMember(member);
        training.setTrainingStartDate(request.getTrainingStartDate());
        training.setTrainingEndDate(request.getTrainingEndDate());
        training.setTrainingName(request.getTrainingName());
        training.setTrainingContent(request.getTrainingContent());
        training.setTrainingCompany(request.getTrainingCompany());
        training.setTrainingStatus(request.getTrainingStatus());
        trainingRepository.save(training);

        return new TrainingDto.CreateResponse(true, "교육 정보 생성 완료", training.getTrainingId());
    }
    
    // 교육 정보 조회
    public TrainingDto.ReadResponse readResponse(Long userId) {
        List<Training> trainingList = trainingRepository.findByMemberUserId(userId);
        TrainingDto.ReadResponse response = new TrainingDto.ReadResponse();
        if(trainingList != null && !trainingList.isEmpty()) {
            List<TrainingDto.ReadResponse.TrainingInfo> trainingInfos = trainingList.stream()
            .map(training -> new TrainingDto.ReadResponse.TrainingInfo(
                training.getTrainingStartDate(),
                training.getTrainingEndDate(),
                training.getTrainingName(),
                training.getTrainingContent(),
                training.getTrainingCompany(),
                training.getTrainingStatus()
            ))
            .collect(Collectors.toList());
        
            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setTrainings(trainingInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setTrainings(Collections.emptyList());
        }
        return response;
    }

    // 교육 정보 수정
    @Transactional
    public TrainingDto.UpdateResponse updateResponse(Long userId, TrainingDto.UpdateRequest dto) {
        Optional<Training> trainingOpt = trainingRepository.findByTrainingId(dto.getTrainingId());
        if(trainingOpt.isPresent()) {
            Training training = trainingOpt.get();

            if(!training.getMember().getUserId().equals(userId)) {
                return new TrainingDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getTrainingStartDate() != null) {training.setTrainingStartDate(dto.getTrainingStartDate());}
            if(dto.getTrainingEndDate() != null) {training.setTrainingEndDate(dto.getTrainingEndDate());}
            if(dto.getTrainingName() != null) {training.setTrainingName(dto.getTrainingName());}
            if(dto.getTrainingContent() != null) {training.setTrainingContent(dto.getTrainingContent());}
            if(dto.getTrainingCompany() != null) {training.setTrainingCompany(dto.getTrainingCompany());}
            if(dto.getTrainingStatus() != null) {training.setTrainingStatus(dto.getTrainingStatus());}
            trainingRepository.save(training);
            return new TrainingDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new TrainingDto.UpdateResponse(false, "수정 실패.");
        }
    }
    
    // 교육 정보 삭제
    @Transactional
    public TrainingDto.DeleteResponse deleteResponse(Long userId, Long trainingId) {
        Optional<Training> trainingOpt = trainingRepository.findByTrainingId(trainingId);

        if(trainingOpt.isPresent()) {
            Training training = trainingOpt.get();

            if (!training.getMember().getUserId().equals(userId)) {
                return new TrainingDto.DeleteResponse(false, "권한이 없습니다.");
            }

            trainingRepository.delete(training);
            return new TrainingDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new TrainingDto.DeleteResponse(false, "삭제 실패");
        }
    }
}
