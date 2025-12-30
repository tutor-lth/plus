package org.example.expert.domain.user;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserBulkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ImprovedUserBulkInsertTest {

    @Autowired
    private UserBulkRepository userBulkRepository;

    private static final int TOTAL_USERS = 1_000_000;
    private static final int BATCH_SIZE = 10_000;
    private static final int LOG_INTERVAL = 100_000;

    @Test
    @Transactional
    @Rollback(false)
    public void efficientBulkInsertTest() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_USERS; i += BATCH_SIZE) {
            List<User> users = new ArrayList<>(BATCH_SIZE);

            for (int k = 0; k < BATCH_SIZE && (i + k) < TOTAL_USERS; k++) {
                int userNum = i + k;
                String email = String.format("jdbc_user%d@example.com", userNum);
                String password = "password" + userNum;
                String nickname = "jdbc_nickname" + userNum;

                User user = new User(email, password, UserRole.USER, nickname);
                users.add(user);
            }

            userBulkRepository.bulkInsert(users);
            
            if ((i + BATCH_SIZE) % LOG_INTERVAL == 0) {
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.printf("%d번 사용자 - %.2f 초%n", i + BATCH_SIZE, elapsed / 1000.0);
            }
        }
        
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.printf("완료: 총 %d 사용자 - %.2f seconds%n", TOTAL_USERS, totalTime / 1000.0);
    }
}