package org.example.carebridge.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.dto.*;
import org.example.carebridge.domain.board.dto.view.BoardListViewResponseDto;
import org.example.carebridge.domain.board.dto.view.BoardViewResponseDto;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.repository.BoardRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.ForbiddenException;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.amazonaws.services.ec2.model.PrincipalType.Role;

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
                .id(board.getId())
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

    public List<BoardListViewResponseDto> findAllBoardsUsedByView() {
        List<Board> boards = boardRepository.findAll();  // Board 엔티티 조회

        // Board 엔티티를 BoardListViewResponseDto로 변환
        return boards.stream()
                .map(BoardListViewResponseDto::from)  // Board에서 DTO로 변환
                .collect(Collectors.toList());
    }

    // 보드 단건 조회
    public BoardFindResponseDto findBoardById(Long id) {
        Board board = boardRepository.findByIdOrElseThrow(id);

        return BoardFindResponseDto.toDto(board);
    }

    // BoardService의 findBoardById 메서드 수정
    public BoardViewResponseDto findBoardByIdUsedByView(Long id) {
        Board board = boardRepository.findByIdOrElseThrow(id);
        return BoardViewResponseDto.fromEntity(board);
    }

    public BoardUpdateResponseDto updateBoardById(Long userId, Long boardId, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionType.USER_NOT_FOUND));

        // 관리자인 경우 권한 체크를 하지 않음
        if (!user.getUserRole().equals(UserRole.ADMIN) && !board.getUser().getId().equals(userId)) {
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

    // 보드 삭제 (관리자는 모든 보드 삭제 가능)
    public BoardDeleteResponseDto deleteBoardById(Long userId, Long boardId) {
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionType.USER_NOT_FOUND));

        // 관리자는 모든 보드 삭제 가능, 일반 사용자는 본인 보드만 삭제 가능
        if (!user.getUserRole().equals(UserRole.ADMIN) && !board.getUser().getId().equals(userId)) {
            throw new ForbiddenException(ExceptionType.FORBIDDEN_ACTION);
        }

        boardRepository.delete(board);
        return new BoardDeleteResponseDto("게시판이 삭제되었습니다.");
    }


    public Board getBoardEntityById(Long boardId) {
        return boardRepository.findByIdOrElseThrow(boardId);
    }
}
