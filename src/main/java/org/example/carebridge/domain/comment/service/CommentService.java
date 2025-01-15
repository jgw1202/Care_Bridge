package org.example.carebridge.domain.comment.service;

import org.example.carebridge.domain.comment.dto.CommentDeleteResponseDto;
import org.example.carebridge.domain.comment.dto.CommentRequestDto;
import org.example.carebridge.domain.comment.dto.CommentResponseDto;
import org.example.carebridge.domain.comment.dto.CommentUpdateRequestDto;

/**
 * 댓글 서비스 인터페이스.
 */
public interface CommentService {

    CommentResponseDto createComment(Long boardId, CommentRequestDto dto, Long userId);

    CommentResponseDto updateCommentById(Long id, CommentUpdateRequestDto dto, Long userId);

    CommentDeleteResponseDto deleteCommentById(Long id, Long userId);
}