package net.kemitix.spring.common;

import java.util.List;

public interface Queryable<S, T> {

    List<T> query(S query);
}
