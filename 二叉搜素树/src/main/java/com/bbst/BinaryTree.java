package com.bbst;

import com.printer.BinaryTreeInfo;
import com.printer.BinaryTrees;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<E> implements BinaryTreeInfo {
    protected Node<E> root;
    protected int size;


    public void clear(){
        root = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /*前驱节点:  中序遍历的前一个节点*/
    protected Node<E> predecessor(Node<E> node){
        if (node == null) return null;

        if (node.left != null) {
            // 左子树不为空,则前驱节点一定存在于左子树中,并且是左子树中值最大的节点  left right ...
            Node<E> predecessor = node.left;
            while (predecessor.right != null){
                predecessor = predecessor.right;
            }
            return predecessor;

        }else if (node.left == null && node.parent != null) {
            // 左子树为空,那么前驱节点一定存在于父节点或者祖先节点的最后一个右子节点的父节点
            while ( node.parent != null && node.parent.right != node){
                node = node.parent;
            }
            return node.parent;
        }

        return null;    // 没有前驱节点
    }

    /**后继节点: 中序遍历的后一个节点*/
    protected Node<E> successor(Node<E> node){
        if (node == null) return null;

        if (node.right != null) {
            // 如果右子树不为空,那么后继节点一定存在于右子树中,并且是右子树中值最小的节点 right left left...
            Node<E> successor = node.right;
            while (successor.left != null){
                successor = successor.left;
            }
            return successor;
        }else if (node.right == null && node.parent != null){
            // 如果右子树为空,父节点不为空,那么后继节点就是父节点或者祖先节点的最后一个左子节点的父节点
            while (node.parent != null && node.parent.left != node){
                node = node.parent;
            }
            return node.parent;
        }

        return null;
    }


    public void preorderTraversal(){
        preorderTraversal( i -> System.out.println(i));
    }

    public void preorderTraversal(Visitor<E> visitor){
        preorderTraversal(root,visitor);
    }

    public void inorderTraversal(){
        inorderTraversal( i -> System.out.print(i+","));
    }

    public void inorderTraversal(Visitor<E> visitor){
        preorderTraversal(root,visitor);
    }


    public void postorderTraversal(){
        postorderTraversal( i -> System.out.print(i+","));
    }

    public void postorderTraversal(Visitor<E> visitor){
        postorderTraversal(root,visitor);
    }

    public void levelOrderTraversal(){
        levelOrderTraversal( i -> System.out.print(i+","));
    }

    public void levelOrderTraversal(Visitor<E> visitor){
        levelOrderTraversal(root,visitor);
    }


    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf(){
            return left == null && right == null;
        }

        public boolean hasTwoChildren(){
            return left != null && right != null;
        }

        public Node<E> sibling(){

            if (isLeftChildren()) return parent.right;

            if (isRightChildren()) return parent.left;

            return null;
        }

        public boolean isLeftChildren(){
            return parent != null && parent.left == this;
        }

        public boolean isRightChildren(){
            return parent != null && parent.right == this;
        }

        @Override
        public String toString() {
            return element.toString();
        }

    }

    public interface Visitor<E> {
        public void visitor(E element);
    }


    private void preorderTraversal(Node<E> node,Visitor<E> visitor){
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (! stack.isEmpty()) {
            node = stack.pop();
            visitor.visitor(node.element);

            if (node.right != null) stack.push(node.right);

            if (node.left != null) stack.push(node.left);
        }
    }

    private void inorderTraversal(Node<E> node, Visitor<E> visitor){
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();

        while (node != null || ! stack.isEmpty()) {
            if (node != null) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            Node<E> temp = stack.pop();
            visitor.visitor(temp.element);
            node = temp.right;
        }
    }



    private void postorderTraversal(Node<E> node, Visitor<E> visitor){
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (! stack.isEmpty()) {
            node = stack.pop();

            if (node != null) {
                stack.push(node);
                stack.push(null);

                if (node.right != null) stack.push(node.right);

                if (node.left != null) stack.push(node.left);
            }else {
                node = stack.pop();
                visitor.visitor(node.element);
            }
        }
    }


    private void levelOrderTraversal(Node<E> node, Visitor<E> visitor){
        if (node == null)  return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);

        while ( ! queue.isEmpty()) {
            node = queue.poll();
            visitor.visitor(node.element);

            if (node.left != null) queue.offer(node.left);

            if (node.right != null) queue.offer(node.right);
        }
    }

//    private void preorderTraversal(Node<E> node,Visitor<E> visitor){
//       if (node == null) return;
//
//       visitor.visitor(node.element);
//       preorderTraversal(node.left,visitor);
//       preorderTraversal(node.right,visitor);
//    }
//
//    private void inorderTraversal(Node<E> node, Visitor<E> visitor){
//        if (node == null) return;
//
//        inorderTraversal(node.left,visitor);
//        visitor.visitor(node.element);
//        inorderTraversal(node.right,visitor);
//    }
//
//    private void postorderTraversal(Node<E> node, Visitor<E> visitor){
//        if (node == null) return;
//
//        postorderTraversal(node.left,visitor);
//        postorderTraversal(node.right,visitor);
//        visitor.visitor(node.element);
//    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
    }
}
