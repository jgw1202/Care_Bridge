package org.example.carebridge.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.dto.*;
import org.example.carebridge.domain.board.service.BoardServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    // 보드 생성
    @PostMapping
    public ResponseEntity<BoardCreateResponseDto> createBoard(@RequestBody BoardCreateRequestDto dto) {
        return new ResponseEntity<>(boardService.createBoard(dto), HttpStatus.CREATED);
    }

    // 보드 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardFindResponseDto>> getAllBoards() {
        return new ResponseEntity<>(boardService.findAllBoards(), HttpStatus.OK);
    }

    // 보드 단건 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardFindResponseDto> getBoardById(@PathVariable Long boardId) {
        return new ResponseEntity<>(boardService.findBoardById(boardId), HttpStatus.OK);
    }

    // 보드 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardUpdateResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateRequestDto dto) {
        return new ResponseEntity<>(boardService.updateBoardById(boardId, dto), HttpStatus.OK);
    }

    // 보드 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@PathVariable Long boardId) {
        return new ResponseEntity<>(boardService.deleteBoardById(boardId), HttpStatus.OK);
    }
}
