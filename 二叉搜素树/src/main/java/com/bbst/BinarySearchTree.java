package com.bbst;

import java.util.Comparator;

public class BinarySearchTree<E> extends BinaryTree<E> {

    protected Comparator<E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element){
        elementNotNull(element);

        if (root == null) {
            root = createNode(element,null);
            size ++;
            afterAdd(root);
            return;
        }

        Node<E> node = root;
        Node<E> parent = null;
        int res = 0;
        while (node != null) {
            res = comparable(element,node.element);
            parent = node;
            if (res > 0) {
                node = node.right;
            }else if (res < 0){
                node = node.left;
            }else {
                node.element = element;
                return;
            }
        }
        Node<E> newNode = createNode(element,parent);
        if (res > 0){
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
        afterAdd(newNode);
    }

    protected void afterAdd(Node<E> node){ }

    protected Node<E> createNode(E element,Node<E> parent){
        return new Node<>(element,parent);
    }

    public boolean remove(E element){
        elementNotNull(element);

        return remove(node(element));
    }

    public boolean set(E e1, E e2){
        elementNotNull(e1);
        elementNotNull(e2);

        if (remove(node(e1))) {
            add(e2);
            return true;
        }
        return false;
    }


    protected Node<E> node(E element) {
        if (root == null) return null;
        Node<E> node = root;

        while (node != null) {
            int c = comparable(element, node.element);
            if (c == 0){
                return node;
            }
            if (c > 0){
                node = node.right;
            }else {
                node = node.left;
            }
        }

        return null;
    }




    protected int comparable(E e1, E e2){
        if (comparator != null)
            return comparator.compare(e1, e2);

        return ((Comparable<E>) e1).compareTo(e2);
    }


    private void elementNotNull(E e){
        if (e == null)
            throw new IllegalArgumentException("element is must be not null");
    }


    private boolean remove(Node<E> node){
        if (node == null) return false;

        // 判断是否是度为2的节点
        if (node.left != null && node.right != null) {
            // 获取其后继节点
            Node<E> successor = successor(node);
            // 用后继节点的值覆盖其节点的值
            node.element = successor.element;
            // 将后继节点赋值给node
            node = successor;
        }

        Node<E> replacement = node.left != null ? node.left : node.right;
        // 度为1的节点
        if (replacement != null) {
            // 获取其不为空的子节点
            replacement.parent = node.parent;   // 更改替代节点的父节点

            if (node.parent == null) {          // 根节点
                root = replacement;
            }else {
                if (node.parent.left == node) {
                    node.parent.left = replacement;
                }else {
                    node.parent.right = replacement;
                }
            }
        }else {
            // 度为0的节点
            if (node.parent == null) {      // 根节点
                root = null;
            }else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                }else {
                    node.parent.right = null;
                }
            }
        }
        size--;
        afterRemove(node,replacement);
        return true;
    }

    /**
     *
     * @param node 被删除的节点
     * @param replacement    替代节点
     */
    protected void afterRemove(Node<E> node,Node<E> replacement){ }

}
