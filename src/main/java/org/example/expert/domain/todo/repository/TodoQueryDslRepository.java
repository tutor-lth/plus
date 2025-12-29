package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.request.SearchRequest;
import org.example.expert.domain.todo.dto.response.TodoSearchDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoQueryDslRepository {
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    Page<TodoSearchDto> search(SearchRequest request);
}
