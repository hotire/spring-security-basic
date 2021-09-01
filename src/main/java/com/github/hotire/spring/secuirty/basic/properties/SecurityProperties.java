package com.github.hotire.spring.secuirty.basic.properties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

import com.github.hotire.spring.secuirty.basic.jwt.Role;

import lombok.Data;

@Data
@ConfigurationProperties("hotire.security")
public class SecurityProperties {
    private boolean enabled;
    private String jwtPublicKey;
    private List<PathRole> pathRoles;
    private List<Role> anyRequestRoles;
    private Map<Role, String> authenticationTokens;

    public List<PathRole> getPathRoles() {
        return Optional.ofNullable(pathRoles).orElseGet(List::of);
    }

    @Data
    public static class PathRole {
        private HttpMethod httpMethod;
        private List<String> pathPatterns;
        private List<Role> roles;

        public HttpMethod getHttpMethod() {
            return Optional.ofNullable(httpMethod).orElse(HttpMethod.GET);
        }

        public List<String> getPathPatterns() {
            return Optional.ofNullable(pathPatterns).orElseGet(List::of);
        }

        public List<Role> getRoles() {
            return Optional.ofNullable(roles).orElseGet(List::of);
        }
    }
}
