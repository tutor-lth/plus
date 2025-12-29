package org.example.expert.config;

import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WithSecurityContext(factory = WithMockAuthUserSecurityContextFactory.class)
public @interface WithMockAuthUser {
    long userId() default 1L;
    String email() default "user@example.com";
    String nickname() default "nickname";
    UserRole role() default UserRole.USER;
}
