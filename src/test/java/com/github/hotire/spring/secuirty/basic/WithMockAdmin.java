package com.github.hotire.spring.secuirty.basic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "hotire", roles = "ADMIN")
public @interface WithMockAdmin {
}
