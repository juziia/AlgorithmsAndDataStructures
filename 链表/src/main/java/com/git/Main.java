package com.git;

public class Main {

    public static void main(String[] args) {
        SingleLinkedList<Integer> list = new SingleLinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list.toString());

        list.remove(0); // 获取前面一个节点对象, 将前面一个节点对象的下一个节点指向下下一个节点(也就是所需要删除位置的下一个节点对象)
        list.add(0,12);
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(list.getSize()));
    }
}
