package org.example.carebridge.domain.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.repository.BoardRepository;
import org.example.carebridge.domain.like.dto.LikeDeleteResponseDto;
import org.example.carebridge.domain.like.dto.LikeResponseDto;
import org.example.carebridge.domain.like.entity.Like;
import org.example.carebridge.domain.like.repository.LikeRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.exception.ForbiddenActionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public LikeResponseDto createLike(Long boardId, Long userId) {

        User user = userRepository.findByIdOrElseThrow(userId);

        Board board = boardRepository.findByIdOrElseThrow(boardId);

        Like like = new Like(null, user, board);
        Like savedLike = likeRepository.save(like);

        return new LikeResponseDto(savedLike.getId(), board.getId(), user.getId());
    }

    @Override
    @Transactional
    public LikeDeleteResponseDto deleteLike(Long likeId, Long userId) {

        Like like = likeRepository.findByIdOrElseThrow(likeId);

        if (!like.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        likeRepository.delete(like);
        return new LikeDeleteResponseDto("좋아요가 취소되었습니다.");
    }
}
