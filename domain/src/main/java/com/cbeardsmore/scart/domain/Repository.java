package com.cbeardsmore.scart.domain;

import java.util.UUID;

public interface Repository<T> {
    void save(T t);
    T load(UUID id);
}
