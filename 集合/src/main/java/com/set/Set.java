package com.set;

public interface Set<E> {

    void add(E element);

    void remove(E element);

    void contains(E element);

    void clear();

    boolean isEmpty();
}
