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
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.ForbiddenActionException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j // 로그 기능을 제공하는 Lombok 어노테이션
@Service // Spring 서비스 컴포넌트임을 나타내는 어노테이션
@RequiredArgsConstructor // final 필드들에 대해 생성자를 자동으로 생성하는 Lombok 어노테이션
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository; // Like 엔티티와 상호작용하는 리포지토리
    private final UserRepository userRepository; // User 엔티티와 상호작용하는 리포지토리
    private final BoardRepository boardRepository; // Board 엔티티와 상호작용하는 리포지토리
    private final RedissonClient redissonClient; // Redisson 클라이언트 의존성 추가

    /**
     * 사용자가 특정 보드에 좋아요를 생성하는 메서드
     * @param boardId 좋아요를 추가할 보드의 ID
     * @param userId 좋아요를 클릭한 사용자의 ID
     * @return 좋아요 정보가 담긴 LikeResponseDto 반환
     */
    @Override
    @Transactional
    public LikeResponseDto createLike(Long boardId, Long userId) {
        String lockKey = "like:board:" + boardId + ":user:" + userId; // 보드와 사용자 ID를 포함한 락 키
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 락을 10초 대기하고, 1초 동안 보유
            if (lock.tryLock(10, 1, TimeUnit.SECONDS)) {
                User user = userRepository.findByIdOrElseThrow(userId);
                Board board = boardRepository.findByIdOrElseThrow(boardId);

                // 이미 좋아요를 누른 사용자인지 확인
                boolean alreadyLiked = likeRepository.existsByBoardAndUser(board, user);
                if (alreadyLiked) {
                    // 이미 좋아요를 누른 경우 예외 발생
                    throw new ForbiddenActionException();
                }

                // 좋아요 엔티티 생성 및 저장
                Like like = new Like(null, user, board);
                Like savedLike = likeRepository.save(like);
                return new LikeResponseDto(savedLike.getId(), board.getId(), user.getId());
            } else {
                // 락을 획득하지 못한 경우 예외 발생
                throw new RuntimeException("좋아요 처리 중 락을 획득하지 못했습니다.");
            }
        } catch (InterruptedException e) {
            // InterruptedException 발생 시 예외 처리
            Thread.currentThread().interrupt();
            throw new RuntimeException("좋아요 생성 중 오류가 발생했습니다.", e);
        } finally {
            // 락을 해제
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
    /**
     * 사용자가 자신이 좋아요를 클릭한 보드에 대해 좋아요를 취소하는 메서드
     * @param likeId 삭제할 좋아요의 ID
     * @param userId 좋아요를 취소하려는 사용자의 ID
     * @return 좋아요 취소 메시지가 담긴 LikeDeleteResponseDto 반환
     */
    @Override
    @Transactional // 이 메서드는 트랜잭션 내에서 실행됨
    public LikeDeleteResponseDto deleteLike(Long likeId, Long userId) {
        // 좋아요 ID로 좋아요 엔티티를 찾아옵니다. 없으면 예외 발생
        Like like = likeRepository.findByIdOrElseThrow(likeId);

        // 좋아요를 생성한 사용자가 현재 사용자가 맞는지 확인
        if (!like.getUser().getId().equals(userId)) {
            // 사용자가 일치하지 않으면 ForbiddenActionException 예외 발생
            throw new ForbiddenActionException();
        }

        // 좋아요를 삭제합니다.
        likeRepository.delete(like);

        // 좋아요 취소 메시지가 담긴 DTO 반환
        return new LikeDeleteResponseDto("좋아요가 취소되었습니다.");
    }
}


