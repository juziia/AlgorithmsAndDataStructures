package com.set;

import com.map.TreeMap;

public class TreeSet<E> implements Set<E> {
    private TreeMap<E,Object> map;

    public TreeSet() {
        map = new TreeMap<>();
    }

    @Override
    public void add(E element) {
        map.put(element,null);
    }

    @Override
    public void remove(E element) {
        map.remove(element);
    }

    @Override
    public void contains(E element) {
        map.containsKey(element);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
}
