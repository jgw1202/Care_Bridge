package org.example.carebridge.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.dto.*;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.repository.BoardRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.ForbiddenException;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 보드 생성
    public BoardCreateResponseDto createBoard(Long userId, BoardCreateRequestDto dto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionType.USER_NOT_FOUND));

        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .tag(dto.getTag())
                .user(user)
                .build();

        boardRepository.save(board);

        return BoardCreateResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .tag(board.getTag())
                .views(board.getViews())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }

    // 보드 전체 조회
    public List<BoardFindResponseDto> findAllBoards() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream().map(BoardFindResponseDto::toDto).collect(Collectors.toList());
    }

    // 보드 단건 조회
    public BoardFindResponseDto findBoardById(Long id) {
        Board board = boardRepository.findByIdOrElseThrow(id);

        return BoardFindResponseDto.toDto(board);
    }

    // 보드 수정
    public BoardUpdateResponseDto updateBoardById(Long userId, Long boardId, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        if(!board.getUser().getId().equals(userId)){
            throw new ForbiddenException(ExceptionType.FORBIDDEN_ACTION);
        }

        board.updateBoard(dto.getTitle(), dto.getContent(), dto.getTag());

        boardRepository.save(board);

        return BoardUpdateResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .tag(board.getTag())
                .views(board.getViews())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }

    // 보드 삭제
    public BoardDeleteResponseDto deleteBoardById(Long userId, Long boardId) {
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        if(!board.getUser().getId().equals(userId)){
            throw new ForbiddenException(ExceptionType.FORBIDDEN_ACTION);
        }

        boardRepository.delete(board);

        return new BoardDeleteResponseDto("게시판이 삭제되었습니다.");
    }

    public Board getBoardEntityById(Long boardId) {
        return boardRepository.findByIdOrElseThrow(boardId);
    }
}
