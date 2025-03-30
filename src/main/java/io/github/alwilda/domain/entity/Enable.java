package io.github.alwilda.domain.entity;

public interface Enable {

    Boolean getEnabled();

    default boolean isEnabled() {
        return getEnabled() != null && getEnabled();
    }
}
