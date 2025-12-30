package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByNickname(String nickname);
}
