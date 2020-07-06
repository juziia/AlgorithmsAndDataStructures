package com.git;

import java.util.Arrays;

public class ArrayList<E> {
    private Object[] elementData;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;     // 默认容量

    private static final Object[] EMPTY_ELEMENTDATA = {};

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        if (capacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else if (capacity > 0) {
            elementData = new Object[capacity];
        } else {
            throw new IllegalArgumentException("参数非法:  " + capacity);
        }
    }

    public boolean contains(E e) {

        return indexOf(e) != -1;
    }


    public int indexOf(E e) {
        if (e == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (e.equals(elementData[i])) return i;
            }
        }
        return -1;
    }

    public void add(E e) {
        ensureCapacity(size + 1);        // 检查容量

        elementData[size++] = e;
    }


    public void add(int index, E e) {
        // 0 1 2 3 4 5
        ensureCapacity(size + 1);

        for (int i = size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = e;
        size++;
    }

    public E set(int index, E e) {
        E oldElement = (E) elementData[index];
        elementData[index] = e;
        return oldElement;
    }

    public E get(int index) {
        rangeCheck(index);      // 检查索引是否大于等于当前最大索引

        return (E) elementData[index];
    }

    public E remove(int index) {
        E oldElement = (E) elementData[index];

        // 0 1 2 3 4 5 6 7 8 9

        for (int i = index; i < size; i++) {
            elementData[i] = elementData[i + 1];
        }

        elementData[--size] = null;

        return oldElement;
    }

    public int getSize() {
        return size;
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new ArrayIndexOutOfBoundsException(outOfBoundMsg(index));
    }

    private String outOfBoundMsg(int index) {
        return "index: " + index + ", size " + size;
    }


    private void ensureCapacity(int capacity) {
        if (capacity > elementData.length) {
            grow(capacity);
        }
    }

    private void grow(int capacity) {
        int oldCapacity = elementData.length;       // source array size
        int newCapacity = oldCapacity + (oldCapacity >> 1);     // target array size

        Object[] newArr = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = elementData[i];
        }
        elementData = newArr;


    }

    public Iterator getIterator() {
        return new Iterator();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("size: " + size + "  [");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(",");

            sb.append(elementData[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    protected class Iterator {
        private int currentIndex;

        public boolean hasNext() {
            if (currentIndex < size) {
                return true;
            }
            return false;
        }

        public E next() {
            return (E) elementData[currentIndex++];
        }
    }
}
