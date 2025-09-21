package com.coverletter.coverletter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.coverletter.coverletter.dto.ComputerDto;
import com.coverletter.coverletter.entity.Computer;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.ComputerRepository;
import com.coverletter.coverletter.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class ComputerService {
    private final ComputerRepository computerRepository;
    private final MemberRepository memberRepository;

    public ComputerService(ComputerRepository computerRepository,MemberRepository memberRepository) {
        this.computerRepository = computerRepository;
        this.memberRepository = memberRepository;
    }

    // 컴퓨터 능력 정보 생성
    @Transactional
    public ComputerDto.CreateResponse createResponse(Long userId, ComputerDto.CreateRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (!memberOpt.isPresent()) {
            return new ComputerDto.CreateResponse(false, "회원 정보가 존재하지 않습니다.", null);
        }
        
        Member member = memberOpt.get();

        Computer computer = new Computer();
        computer.setMember(member);
        computer.setComputer(request.getComputer());
        computerRepository.save(computer);

        return new ComputerDto.CreateResponse(true, "컴퓨터 능력 정보 생성 완료", computer.getComputerId());
    }
    
    // 컴퓨터 능력 정보 조회
    public ComputerDto.ReadResponse readResponse(Long userId) {
        List<Computer> computerList = computerRepository.findByMemberUserId(userId);
        ComputerDto.ReadResponse response = new ComputerDto.ReadResponse();
        if(computerList != null && !computerList.isEmpty()) {
            List<ComputerDto.ReadResponse.ComputerInfo> computerInfos = computerList.stream()
            .map(computer -> new ComputerDto.ReadResponse.ComputerInfo(
                computer.getComputerId(),
                computer.getComputer()
            ))
            .collect(Collectors.toList());

            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setComputers(computerInfos);
        } else {
            response.setSuccess(false);
            response.setMessage("조회 실패");
            response.setComputers(Collections.emptyList());
        }
        return response;
    }

    // 컴퓨터 능력 정보 수정
    @Transactional
    public ComputerDto.UpdateResponse updateResponse(Long userId, ComputerDto.UpdateRequest dto) {
        Optional<Computer> computerOpt = computerRepository.findByComputerId(dto.getComputerId());
        if(computerOpt.isPresent()) {
            Computer computer = computerOpt.get();

            if(!computer.getMember().getUserId().equals(userId)) {
                return new ComputerDto.UpdateResponse(false, "권한이 없습니다.");
            }

            if(dto.getComputer() != null) {computer.setComputer(dto.getComputer());}
            computerRepository.save(computer);
            return new ComputerDto.UpdateResponse(true, "수정 완료");
        } else {
            return new ComputerDto.UpdateResponse(false, "수정 실패");
        }
    }

    // 컴퓨터 능력 정보 삭제
    @Transactional
    public ComputerDto.DeleteResponse deleteResponse(Long userId, Long computerId) {
        Optional<Computer> computerOpt = computerRepository.findByComputerId(computerId);
        if(computerOpt.isPresent()) {
            Computer computer = computerOpt.get();

            if(!computer.getMember().getUserId().equals(userId)) {
                return new ComputerDto.DeleteResponse(false, "권한이 없습니다.");
            }

            computerRepository.delete(computer);
            return new ComputerDto.DeleteResponse(true, "삭제 완료");
        } else {
            return new ComputerDto.DeleteResponse(false, "삭제 실패");
        }
    }

    // 컴퓨터 능력 정보 전체 삭제
    @Transactional
    public ComputerDto.DeleteResponse deleteAllResponse(Long userId) {
        computerRepository.deleteByMemberUserId(userId);
        return new ComputerDto.DeleteResponse(true, "모든 컴퓨터 능력 정보가 삭제되었습니다.");
    }
}
