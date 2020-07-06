package com.git.circledeque;

/**
 *  使用数组实现双端队列
 * @param <E>
 */
public class CircleDeque<E> {

    private Object[] elementData;

    // 队头
    private int front;

    private int size;


    public int size(){
        return size;
    }


    public  boolean isEmpty(){
        return size == 0;
    }

    public void clear(){

    }


    /*从队尾入队*/
    public void enQueueRear(E element){
        ensureCapacity(size + 1);

        elementData[size++] = element;
    }

    private void ensureCapacity(int size) {
        if (size > elementData.length){
            grow();
        }
    }

    private void grow() {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        Object[] newArr = new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newArr[i] = elementData[i];
        }
        elementData = newArr;
    }


    // 从队头出队
    public  E deQueueFront(){
        E e = (E) elementData[index(-1)];
        front ++;
        return e;
    }


    /*从队头入队*/
    public void enQueueFront(E element){

        elementData[index(-1)] = element;
    }

    /*从队尾出队*/
    public E deQueueRear(){
        return null;
    }

    public E front(){
        return (E) elementData[front];
    }

    public E rear(){
        return (E) elementData[size - 1];
    }

    private int index(int num){
        num += front;
        if (num < 0){
            return elementData.length - 1 - front;
        }

        return num - (num > elementData.length ? elementData.length : 0 );
    }

    @Override
    public String toString() {
        return "";
    }


}
