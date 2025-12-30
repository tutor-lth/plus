package org.example.expert.domain.user;

import org.example.expert.domain.user.repository.UserRepository;
import org.example.expert.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;

@SpringBootTest
public class UserServicePerformanceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String TEST_NICKNAME = "jpa_nickname12321";
    private static final int USER_COUNT = 1000;

    @Test
    @DisplayName("Redis 캐시 미적용 시 사용자 조회 성능 측정")
    void testGetUsersWithoutCache() {
        StopWatch stopWatch = new StopWatch();
        int testRuns = 10;
        long totalExecutionTime = 0;

        System.out.println("===== Redis 캐시 미적용 시 성능 테스트 시작 =====");
        for (int i = 0; i < testRuns; i++) {
            // 매번 캐시를 삭제하여 DB에서 조회하도록 강제
            redisTemplate.delete("users:nickname:" + TEST_NICKNAME);

            stopWatch.start();
            userService.getUsers(TEST_NICKNAME);
            stopWatch.stop();

            long executionTime = stopWatch.getLastTaskTimeMillis();
            totalExecutionTime += executionTime;
            System.out.printf("실행 %d: %d ms\n", i + 1, executionTime);
        }

        System.out.printf(">> 평균 실행 시간: %.2f ms\n\n", (double) totalExecutionTime / testRuns);
    }

    @Test
    @DisplayName("Redis 캐시 적용 시 사용자 조회 성능 측정")
    void testGetUsersWithCache() {
        StopWatch stopWatch = new StopWatch();
        int testRuns = 10;
        long totalExecutionTime = 0;

        // 첫 호출을 통해 Redis에 데이터 캐싱
        System.out.println("===== Redis 캐시 적용 시 성능 테스트 시작 =====");
        System.out.println("첫 호출로 캐시 생성...");
        userService.getUsers(TEST_NICKNAME);
        System.out.println("캐시 생성 완료. 이후 호출부터는 캐시에서 조회합니다.");

        for (int i = 0; i < testRuns; i++) {
            stopWatch.start();
            userService.getUsers(TEST_NICKNAME);
            stopWatch.stop();

            long executionTime = stopWatch.getLastTaskTimeMillis();
            totalExecutionTime += executionTime;
            System.out.printf("실행 %d: %d ms\n", i + 1, executionTime);
        }

        System.out.printf(">> 평균 실행 시간: %.2f ms\n\n", (double) totalExecutionTime / testRuns);
    }
}