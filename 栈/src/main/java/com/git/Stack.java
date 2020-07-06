package com.git;


public class Stack<E> {

    private ArrayList<E> list = new ArrayList<E>();

    public void push(E element){
        list.add(element);
    }

    public E pop(){

        return list.remove(list.size() - 1);
    }

    public E peek(){
        return list.get(list.size() - 1);
    }

    public void clear(){
        list.clear();
    }

    public int size(){
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
