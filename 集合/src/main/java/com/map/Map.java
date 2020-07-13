package com.map;

public interface Map<K,V>{

    public V put(K key, V value);

    public V get(K key, V value);

    public boolean remove(K key);

    public boolean isEmpty();

    public boolean containsKey(K key);

    public boolean containsValue(V value);

    public void clear();

}
