package org.example.expert.domain.todo.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class SearchRequest {

    @Min(value = 1, message = "page는 1 이상이어야 합니다.")
    private Integer page = 1;

    @Min(value = 10, message = "size는 10 이상이어야 합니다.")
    private Integer size = 10;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endAt;

    private String title;

    private String managerNickname;

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }

}
