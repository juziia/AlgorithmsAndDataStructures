package com.git.deque;

import com.git.queue.LinkedList;

/**
 *  双端队列
 * @param <E>
 */
public class Deque<E> {

    private LinkedList<E> linkedList = new LinkedList<E>();


    public int size(){
        return linkedList.size();
    }


    public  boolean isEmpty(){

        return linkedList.isEmpty();
    }

    public void clear(){
        linkedList.clear();
    }


    /*从队尾入队*/
    public void enQueueRear(E element){
        linkedList.add(element);
    }


    // 从队头出队
    public  E deQueueFront(){
        return linkedList.remove(0);
    }

    /*从队头入队*/
    public  void enQueueFront(E element){
        linkedList.add(0,element);
    }

    /*从队尾出队*/
    public E deQueueRear(){
        return linkedList.remove(linkedList.size() - 1);
    }

    public E front(){
        return linkedList.get(0);
    }

    public E rear(){
        return linkedList.get(size() - 1);
    }

    @Override
    public String toString() {
        return "Deque{" +
                "linkedList=" + linkedList +
                '}';
    }
}
