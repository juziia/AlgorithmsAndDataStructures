package com.git;

public class Main2 {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list);
        list.remove(1);
        System.out.println(list);
        list.add(4,2);
        System.out.println(list);
        list.set(0,0);
        System.out.println(list);
    }
}
