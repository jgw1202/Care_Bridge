package org.example.carebridge.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.repository.BoardRepository;
import org.example.carebridge.domain.like.dto.LikeResponseDto;
import org.example.carebridge.domain.like.entity.Like;
import org.example.carebridge.domain.like.repository.LikeRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public LikeResponseDto createLike(Long boardId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("보드를 찾을 수 없습니다."));

        Like like = new Like(null, user, board);
        likeRepository.save(like);

        return new LikeResponseDto(like.getId(), board.getId(), user.getId());
    }

    @Override
    @Transactional
    public LikeResponseDto deleteLike(Long likeId, Long userId) {
        Like like = likeRepository.findById(likeId).orElseThrow(() -> new IllegalArgumentException("해당 좋아요를 찾을 수 없습니다."));

        if (!like.getUser().getId().equals(userId)) {
            throw new IllegalStateException("사용자가 좋아요를 삭제할 권한이 없습니다.");
        }

        likeRepository.delete(like);
        return new LikeResponseDto(likeId, like.getBoard().getId(), userId);
    }
}
