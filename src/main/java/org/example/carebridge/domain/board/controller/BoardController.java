package org.example.carebridge.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.dto.*;
import org.example.carebridge.domain.board.service.BoardServiceImpl;
import org.example.carebridge.global.auth.UserDetailsImple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    // 보드 생성
    @PostMapping
    public ResponseEntity<BoardCreateResponseDto> createBoard(@Valid @RequestBody BoardCreateRequestDto dto, @AuthenticationPrincipal UserDetailsImple userDetails) {
        return new ResponseEntity<>(boardService.createBoard(userDetails.getUser().getId(), dto), HttpStatus.CREATED);
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
    public ResponseEntity<BoardUpdateResponseDto> updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateRequestDto dto, @AuthenticationPrincipal UserDetailsImple userDetails) {
        return new ResponseEntity<>(boardService.updateBoardById(userDetails.getUser().getId(), boardId, dto), HttpStatus.OK);
    }

    // 보드 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImple userDetails) {
        return new ResponseEntity<>(boardService.deleteBoardById(userDetails.getUser().getId(), boardId), HttpStatus.OK);
    }
}
