package com.git;

import java.util.Arrays;
import java.util.Stack;

public class ArrayList<E> {


    private Object[] elementData;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};

    public ArrayList(){
        elementData = new Object[10];
    }

    public ArrayList(int capacity){
        elementData = new Object[capacity];
    }


    public void add(E e){
        ensureCapacity(size + 1);

        elementData[size++] = e;
    }


    public void add(int index,E e){
        checkRange(index);

        ensureCapacity(index + 1);

        for (int i = size; i > index ; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = e;
        size++;
    }

    public E remove(int index){
        checkRange(index);

        E old = get(index);
        for (int i = index; i < size - 1; i++) {
            elementData[index] = elementData[index + 1];
        }
        elementData[--size] = null;
        return old;
    }

    public E get(int index){
        checkRange(index);

        return (E) elementData[index];
    }

    private void ensureCapacity(int capacity) {
        if(capacity > elementData.length){
            grow();
        }
    }

    private void grow() {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + ( oldCapacity >> 1 ) ;

        Object[] newArr = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = elementData[i];
        }
        elementData = newArr;
    }

    public void clear(){
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }

        size = 0;
    }

    public int size(){
        return size;
    }

    private void checkRange(int index) {
        if (index < 0 || index > size) throw new ArrayIndexOutOfBoundsException("index: "+index+" , size: "+size);
    }


    @Override
    public String toString() {
        return "ArrayList{" +
                "elementData=" + Arrays.toString(elementData) +
                ", size=" + size +
                '}';
    }
}
