package org.example.expert.domain.todo.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
public class SearchTodoRequest {

    @Min(value = 1, message = "page는 1 이상이어야 합니다.")
    private Integer page = 1;

    @Min(value = 10, message = "size는 10 이상이어야 합니다.")
    private Integer size = 10;

    private String weather;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endAt;

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }

}
