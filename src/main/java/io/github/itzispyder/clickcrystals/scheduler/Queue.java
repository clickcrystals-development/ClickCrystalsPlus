package io.github.itzispyder.clickcrystals.scheduler;

import java.util.List;

public interface Queue<E> {

    void enqueue(E element);

    void dequeue(E element);

    void insert(int index, E element);

    default void pop() {
        pop(size() - 1);
    }

    void pop(int index);

    int size();

    int getCapacity();

    List<E> getElements();
}
