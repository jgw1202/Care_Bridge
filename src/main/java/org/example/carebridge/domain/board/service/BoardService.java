package org.example.carebridge.domain.board.service;

import org.example.carebridge.domain.board.dto.*;

import java.util.List;

public interface BoardService {

    BoardCreateResponseDto createBoard(Long userId, BoardCreateRequestDto dto);

    List<BoardFindResponseDto> findAllBoards();

    BoardFindResponseDto findBoardById(Long id);

    BoardUpdateResponseDto updateBoardById(Long userId, Long boardId, BoardUpdateRequestDto dto);

    BoardDeleteResponseDto deleteBoardById(Long userId, Long boardId);
}
