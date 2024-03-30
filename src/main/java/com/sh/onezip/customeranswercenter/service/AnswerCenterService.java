package com.sh.onezip.customeranswercenter.service;

import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import com.sh.onezip.customeranswercenter.repository.AnswerCenterRepository;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AnswerCenterService {
    @Autowired
    AnswerCenterRepository answerCenterRepository;

    // HBK start
    public AnswerCenter createAnswer(AnswerCenter answerCenter) {
        return answerCenterRepository.save(answerCenter);
    }

    public Optional<AnswerCenter> findById(Long id) {
        return answerCenterRepository.findById(id);
    }

    public AnswerCenter updateAnswerCenter(AnswerCenter newAnswer) {
        return answerCenterRepository.save(newAnswer);
    }
    // HBK end
}
//    public BoardUpdateDto update(BoardUpdateDto boardUpdateDto) {
//        // DTO를 엔티티로 변환
//        Board boardUpdate = boardRepository.findById(boardUpdateDto.getId())
//                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + boardUpdateDto.getId()));
//
//        // 엔티티에 DTO에서 받아온 정보를 업데이트
//        boardUpdate.setId(boardUpdateDto.getId());
//        boardUpdate.setTitle(boardUpdateDto.getTitle());
//        boardUpdate.setContent(boardUpdateDto.getContent());
//
//        // 업데이트된 엔티티 저장
//        Board updatedBoard = boardRepository.save(boardUpdate);
//
//        // 엔티티를 DTO로 변환하여 반환
//        return convertToDto(updatedBoard);
//    }
//
//
//    // 엔티티를 DTO로 변환하는 메서드
//    private BoardUpdateDto convertToDto(Board board) {
//        BoardUpdateDto boardUpdateDto = new BoardUpdateDto();
//        boardUpdateDto.setId(board.getId());
//        boardUpdateDto.setTitle(board.getTitle());
//        boardUpdateDto.setContent(board.getContent());
//        return boardUpdateDto;
//    }