package org.example.carebridge.domain.board.service;

import org.example.carebridge.domain.board.dto.*;

import java.util.List;

public interface BoardService {

    BoardCreateResponseDto createBoard(BoardCreateRequestDto dto);

    List<BoardFindResponseDto> findAllBoards();

    BoardFindResponseDto findBoardById(Long id);

    BoardUpdateResponseDto updateBoardById(Long id, BoardUpdateRequestDto dto);

    BoardDeleteResponseDto deleteBoardById(Long id);
}
