package com.git.queue;

public class Main {


    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<Integer>();

        for (int i = 0; i < 20; i++) {
            queue.enQueue(i);
        }

        for (int i = 0; i < 20; i++) {
            System.out.println(queue.deQueue());
        }

        System.out.println(queue.isEmpty());
    }

}
