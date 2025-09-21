package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.LanguageDto;
import com.coverletter.coverletter.entity.Language;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.LanguageRepository;
import com.coverletter.coverletter.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final MemberRepository memberRepository;

    public LanguageService(LanguageRepository languageRepository, MemberRepository memberRepository) {
        this.languageRepository = languageRepository;
        this.memberRepository = memberRepository;
    }

    // 언어 정보 생성
    @Transactional
    public LanguageDto.CreateResponse createResponse(Long userId, LanguageDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (!memberOpt.isPresent()) {
            return new LanguageDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }
        
        Member member = memberOpt.get();

        Language language = new Language();
        language.setMember(member);
        language.setLanguage(request.getLanguage());
        language.setLanguageDate(request.getLanguageDate());
        language.setLanguageScore(request.getLanguageScore());
        languageRepository.save(language);

        return new LanguageDto.CreateResponse(true, "언어 정보 생성 완료", language.getLanguageId());

    }

    // 언어 정보 조회
    public LanguageDto.ReadResponse readResponse(Long userId) {
        List<Language> languageList = languageRepository.findByMemberUserId(userId);
        LanguageDto.ReadResponse response = new LanguageDto.ReadResponse();
        if(languageList != null && !languageList.isEmpty()) {
            List<LanguageDto.ReadResponse.LanguageInfo> languageInfos = languageList.stream()
            .map(language -> new LanguageDto.ReadResponse.LanguageInfo(
                language.getLanguageId(),
                language.getLanguage(),
                language.getLanguageDate(),
                language.getLanguageScore()
            ))
            .collect(Collectors.toList());
        
            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setLanguages(languageInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setLanguages(Collections.emptyList());
        }
        return response;
    }

    // 언어 정보 수정
    @Transactional
    public LanguageDto.UpdateResponse updateResponse(Long userId, LanguageDto.UpdateRequest dto) {
        Optional<Language> languageOpt = languageRepository.findByLanguageId(dto.getLanguageId());
        if(languageOpt.isPresent()) {
            Language language = languageOpt.get();

            if(!language.getMember().getUserId().equals(userId)) {
                return new LanguageDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getLanguage() != null) {language.setLanguage(dto.getLanguage());}
            if(dto.getLanguageDate() != null) {language.setLanguageDate(dto.getLanguageDate());}
            if(dto.getLanguageScore() != null) {language.setLanguageScore(dto.getLanguageDate());}
            languageRepository.save(language);
            return new LanguageDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new LanguageDto.UpdateResponse(false, "수정 실패.");
        }
    }

    // 언어 정보 삭제
    @Transactional
    public LanguageDto.DeleteResponse deleteResponse(Long userId, Long languageId) {
        Optional<Language> languageOpt = languageRepository.findByLanguageId(languageId);

        if(languageOpt.isPresent()) {
            Language language = languageOpt.get();

            if (!language.getMember().getUserId().equals(userId)) {
                return new LanguageDto.DeleteResponse(false, "권한이 없습니다.");
            }

            languageRepository.delete(language);
            return new LanguageDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new LanguageDto.DeleteResponse(false, "삭제 실패");
        }
    }
}

