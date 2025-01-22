package org.example.carebridge.domain.like.service;

import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.repository.BoardRepository;
import org.example.carebridge.domain.like.entity.Like;
import org.example.carebridge.domain.like.repository.LikeRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.*;

import static org.mockito.Mockito.*;

class LikeServiceConcurrencyTest {

    @InjectMocks
    private LikeServiceImpl likeService;

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private RedissonClient redissonClient;
    @Mock
    private RLock lock;

    private User user1, user2;
    private Board board;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = User.builder().email("user1@example.com").password("password1").build();
        user2 = User.builder().email("user2@example.com").password("password2").build();
        board = Board.builder().title("Concurrent Test Board").content("Content").build();

        when(boardRepository.findByIdOrElseThrow(1L)).thenReturn(board);
        when(redissonClient.getLock(anyString())).thenReturn(lock);
        try {
            when(lock.tryLock(anyLong(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        } catch (InterruptedException e) {
            e.printStackTrace(); // 예외 처리: 테스트 환경에서는 로그 출력
        }
    }

    @Test
    void testConcurrentLikes() throws InterruptedException {
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 첫 번째 호출에서는 false, 두 번째 호출부터는 true를 반환
        when(likeRepository.existsByBoardAndUser(any(Board.class), any(User.class)))
                .thenReturn(false)  // 첫 번째 호출에서 false
                .thenReturn(true)   // 두 번째 호출부터 true
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true);

        // likeRepository.save()가 호출될 때 User가 제대로 설정되도록 수정
        when(likeRepository.save(any(Like.class))).thenAnswer(invocation -> {
            Like like = invocation.getArgument(0);
            // user1과 user2가 번갈아 가며 좋아요를 누르도록 설정
            if (like.getUser() == null) {
                like.setUser(like.getUser().getEmail().equals("user1@example.com") ? user1 : user2);
            }
            System.out.println("Like saved for user: " + like.getUser().getEmail());
            return like;
        });


        // 다중 스레드를 사용하여 좋아요 요청을 병렬로 실행
        for (int i = 0; i < threadCount; i++) {
            final User user = (i % 2 == 0) ? user1 : user2;  // user1과 user2가 번갈아 가며 사용
            executorService.submit(() -> { // 쓰레드 병렬 실행
                try {
                    likeService.createLike(1L, user.getId());
                } catch (Exception e) {
                    System.err.println("Exception during like creation: " + e.getMessage());
                } finally {
                    latch.countDown(); //  각 스레드가 종료될 때마다 카운트를 하나씩 감소시키며, 마지막 스레드가 종료될 때까지 대기
                }
            });
        }

        latch.await();  // 모든 스레드가 완료될 때까지 대기
        executorService.shutdown();

        // likeRepository.save()가 정확히 5번 호출되었는지 검증 -> 좋아요 5개면 동시성 제어 성공
        verify(likeRepository, times(5)).save(any(Like.class));  // user1과 user2가 번갈아 가며 5번 호출
    }
}
