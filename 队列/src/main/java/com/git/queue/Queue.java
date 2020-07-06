package com.git.queue;

/**
 *  队列:
 *      队列是一种特殊的线性表,它只能操作头尾两端,它的特点就是先进先出
 * @param <E>
 */
public class Queue<E> {

    private LinkedList<E> linkedList = new LinkedList<E>();

    /*
    * 入队
    **/
    public void enQueue(E element){
        linkedList.add(element);
    }


    /*出队*/
    public E deQueue(){
        return linkedList.remove(0);
    }

    public E front(){
        return linkedList.get(0);
    }

    public boolean isEmpty(){
        return linkedList.isEmpty();
    }

    public void clear(){
        linkedList.clear();
    }

}
