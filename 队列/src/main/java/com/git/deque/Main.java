package com.git.deque;

public class Main {

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        for (int i = 0; i < 20; i++) {
            deque.enQueueFront(i);
        }


        for (int i = 0; i < 5; i++) {
            deque.deQueueFront();
        }

//        for (int i = 0; i < 5; i++) {
//            deque.enQueueRear(i);
//        }

        System.out.println(deque);
    }
}
