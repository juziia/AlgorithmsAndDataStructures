package com.git.queue;

public class LinkedList<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;


    public void add(E element){

        // 第一次插入
        if (size == 0){
            addFirst(element);
        }else {
            addLast(element);
        }
        size++;
    }

    private void addLast(E element) {
        Node<E> oldLast = last;
        last = new Node<E>(oldLast,element,null);
        oldLast.next = last;

        if (first.next == null){        // 第二次插入
            first.next = last;
        }
    }

    private void addFirst(E element) {
        first = new Node<E>(null,element,null);
        last = first;
    }

    public void add(int index,E element){

        if (index == size){ // 从尾部插入
            Node<E> oldLast = last;
            last = new Node<E>(oldLast,element,null);

            if(oldLast == null){        // 当前链表第一次插入数据
                first = last;
            }else {
                oldLast.next = last;
            }

        }else {     // 从非尾部插入
            Node<E> next = indexOf(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<E>(prev, element, next);
            next.prev = node;

            if (prev == null) {
                first = node;
            } else {
                prev.next = node;
            }
        }

        size++;
    }

    public E remove(int index){

        Node<E> node = indexOf(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (prev == null){
            first = next;
        }else {
            prev.next = next;
        }

        if (next == null){
            last = prev;
        }else {
            next.prev = prev;
        }

        size --;
        return node.element;
    }

    public void set(int index,E element){
        indexOf(index).element = element;
    }

    public E get(int index){

        return indexOf(index).element;
    }


    private Node<E> indexOf(int index){
        rangeCheck(index);

        if (index < (size << 1) ){
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    public int size(){
        return size;
    }

    public void clear(){
        first = null;
        last = null;
        size --;
    }


    public boolean isEmpty(){
        return size == 0;
    }



    private void rangeCheck(int index){
        if (index < 0 || index > size)
            throw new ArrayIndexOutOfBoundsException("index: "+index+" , size: "+size);
    }


    private static class Node<E> {
        Node<E> prev;
        E element;
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev == null) sb.append("null");
            else sb.append(prev.element);

            sb.append("<- ").append(element).append(" ->");

            if (next == null) sb.append("null");
            else sb.append(next.element);

            return sb.toString();
        }
    }

    @Override
    public String toString() {
        rangeCheck(size);
        StringBuilder sb = new StringBuilder("size: "+size+" , [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(",");

            sb.append(node);
            node = node.next;
        }

        return sb.append("]").toString();
    }
}
