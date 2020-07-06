package com.git;


/**
 *  单向链表:
 *      每次新增元素都需要从头节点遍历到末尾节点,将末尾节点指向新节点  复杂度O(n)
 *      每次查找元素都需要从头节点遍历到指定节点,然后返回, 复杂度O(n)
 *      删除节点,获取删除节点的前一个节点,将其next指向它的下下一个节点, 复杂度O(n)
 *      修改值,遍历到指定节点,将节点中的element修改成传入的参数,复杂度O(n)
 *
 * @param <E>
 */
public class SingleLinkedList<E> {

    private Node<E> first;      // 头节点

    private int size;           // 元素个数


    public void add(E element) {
        if (first == null) {
            first = new Node<E>(element, null);
        } else {
            indexOf(size - 1).next = new Node<E>(element, null);
        }

        size++;
    }

    public void set(int index, E element) {
        if (index == 0) first.element = element;

        Node<E> node = indexOf(index);
        node.element = element;
    }

    public E get(int index) {
        if (size == 0||index == size) return null;

        return indexOf(index).element;
    }

    public void add(int index, E element) {
        Node node = null;
        if (index == 0) {
            first = new Node<E>(element, first);
        } else {
            node = indexOf(index - 1);
            node.next = new Node(element, node.next.next);
        }
        size++;
    }

    public void remove(int index) {
        if (index == 0) {
            first = first.next;
        } else {
            Node<E> node = indexOf(index - 1);
            node.next = node.next.next;
        }
        size--;
    }

    private Node<E> indexOf(int index) {
        rangeCheck(index);

        Node<E> node = first;

        for (int i = 0; i < size; i++) {
            if (index == i)
                return node;
            node = node.next;
        }

        return null;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("size: " + size + ", index: " + index);
    }

    public int getSize(){
        return size;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("size: " + size + ", [");
        for (int i = 0; i < size; i++) {
            if (i > 0)
                sb.append(",");

            sb.append(indexOf(i).element);
        }
        return sb.append("]").toString();
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
