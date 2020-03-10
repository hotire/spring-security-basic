package com.github.hotire.spring.secuirty.basic.jwt;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @MethodSource("provideArguments")
    @ParameterizedTest
    void lookup(final String role, final Role expected) {
        // when
        final Role result = Role.lookup(role);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("USER", Role.USER),
                Arguments.of("ADMIN", Role.ADMIN),
                Arguments.of("NONE", Role.NONE),
                Arguments.of("", Role.NONE)
        );
    }
}