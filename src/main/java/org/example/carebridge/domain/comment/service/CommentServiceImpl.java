package org.example.carebridge.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.repository.BoardRepository;
import org.example.carebridge.domain.comment.dto.CommentDeleteResponseDto;
import org.example.carebridge.domain.comment.dto.CommentRequestDto;
import org.example.carebridge.domain.comment.dto.CommentResponseDto;
import org.example.carebridge.domain.comment.dto.CommentUpdateRequestDto;
import org.example.carebridge.domain.comment.entity.Comment;
import org.example.carebridge.domain.comment.repository.CommentRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.exception.BoardNotFoundException;
import org.example.carebridge.global.exception.UserNotFoundException;
import org.example.carebridge.global.exception.ForbiddenActionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    // 의존성 주입: 댓글, 게시판, 사용자 관련 데이터를 처리하는 레포지토리
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional // 트랜잭션 관리: 댓글 생성 중 오류 발생 시 롤백 처리
    public CommentResponseDto createComment(Long boardId, CommentRequestDto dto, Long userId) {
        // 게시판 엔티티 조회, 없을 경우 예외 발생
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        // 사용자 엔티티 조회, 없을 경우 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // 새로운 댓글 엔티티 생성 및 초기화
        Comment comment = new Comment(null, dto.getComment(), board, user);
        // 댓글 저장 및 반환된 엔티티
        Comment savedComment = commentRepository.save(comment);

        // 저장된 댓글 정보를 기반으로 응답 DTO 생성
        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getUser().getId(),
                savedComment.getComment(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Override
    @Transactional // 트랜잭션 관리: 댓글 수정 중 오류 발생 시 롤백 처리
    public CommentResponseDto updateCommentById(Long id, CommentUpdateRequestDto dto, Long userId) {
        // 댓글 엔티티 조회, 없을 경우 예외 발생
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        // 댓글 작성자와 현재 사용자가 일치하는지 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException(); // 권한 없음 예외 발생
        }

        // 댓글 내용 업데이트
        comment.setComment(dto.getComment());
        // 수정된 댓글 저장
        Comment updatedComment = commentRepository.save(comment);

        // 수정된 댓글 정보를 기반으로 응답 DTO 생성
        return new CommentResponseDto(
                updatedComment.getId(),
                updatedComment.getUser().getId(),
                updatedComment.getComment(),
                updatedComment.getCreatedAt(),
                updatedComment.getModifiedAt()
        );
    }

    @Override
    @Transactional // 트랜잭션 관리: 댓글 삭제 중 오류 발생 시 롤백 처리
    public CommentDeleteResponseDto deleteCommentById(Long id, Long userId) {
        // 댓글 엔티티 조회, 없을 경우 예외 발생
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        // 댓글 작성자와 현재 사용자가 일치하는지 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException(); // 권한 없음 예외 발생
        }

        // 댓글 삭제
        commentRepository.delete(comment);
        return new CommentDeleteResponseDto("댓글이 삭제되었습니다."); // 삭제 완료 메시지 반환
    }
}
