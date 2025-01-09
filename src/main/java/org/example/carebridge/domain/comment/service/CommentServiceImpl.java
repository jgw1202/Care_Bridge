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

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommentResponseDto createComment(Long boardId, CommentRequestDto dto, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Comment comment = new Comment(null, dto.getComment(), board, user);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getUser().getId(),
                savedComment.getComment(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Override
    @Transactional
    public CommentResponseDto updateCommentById(Long id, CommentUpdateRequestDto dto, Long userId) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        comment.setComment(dto.getComment());
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                updatedComment.getId(),
                updatedComment.getUser().getId(),
                updatedComment.getComment(),
                updatedComment.getCreatedAt(),
                updatedComment.getModifiedAt()
        );
    }

    @Override
    @Transactional
    public CommentDeleteResponseDto deleteCommentById(Long id, Long userId) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        commentRepository.delete(comment);
        return new CommentDeleteResponseDto("댓글이 삭제되었습니다.");
    }
}
