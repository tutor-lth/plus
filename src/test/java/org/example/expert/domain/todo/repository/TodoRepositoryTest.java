package org.example.expert.domain.todo.repository;

import org.example.expert.config.QueryDslConfig;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdWithUserTest1() {
        User savedUser = userRepository.save(new User("test@example.com", "password", UserRole.USER, "nickname"));
        Todo savedTodo = todoRepository.save(new Todo("title", "contents", "SUNNY", savedUser));

        Todo result = todoRepository.findByIdWithUser(savedTodo.getId()).orElseThrow();

        assertThat(result).isEqualTo(savedTodo);
    }

    @Test
    void findByIdWithUserTest2() {
        Optional<Todo> result = todoRepository.findByIdWithUser(999L);

        assertThat(result).isEmpty();
    }
}
