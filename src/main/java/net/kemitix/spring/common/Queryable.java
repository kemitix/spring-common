package net.kemitix.spring.common;

import java.util.List;

public interface Queryable<T> {

    List<T> query(String query);
}
