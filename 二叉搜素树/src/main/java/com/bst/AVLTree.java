package com.bst;

import java.util.Deque;
import java.util.HashMap;
import java.util.Queue;

/**
 * AVL树:  平衡二叉搜素树 (Balanced Binary Search Tree)
 * <p>
 * 左右子树的高度差(平衡因子)的绝对值不大于1(小于等于1)
 */
public class AVLTree<E> extends BinarySearchTree<E> {

    @Override
    protected void afterAdd(Node<E> node) {
        // 从叶子节点到祖宗节点计算平衡因子,判断是否平衡
        while ((node = node.parent) != null) {
            // 平衡则更新高度
            if (isBalanced(node)) {
                updateHeight(node);
            } else {
                // 失衡了, 恢复平衡
                rebalanced2(node);
                break;          // 恢复平衡就跳出
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)){
                updateHeight(node);
            }else {
                rebalanced2(node);
            }
        }
    }

    // 恢复平衡
    private void rebalanced(Node<E> grandNode){
        /**
         *      20
         *    15
         *  14
         */
        Node<E> parentNode = ((AVLNode<E>) grandNode).tallerChildren();
        Node<E> node = ((AVLNode<E>) parentNode).tallerChildren();

        if (parentNode == grandNode.left){  //  L
            if (node == parentNode.left){   //  LL  右旋转(单旋)
                rorateRight(grandNode);
                System.out.println("LL 右旋转");
            }else {     // LR  左旋转  右旋转
                rorateLeft(parentNode);
                rorateRight(grandNode);
                System.out.println("LR 左旋转  右旋转");
            }
        }else {         // R
            if (node == parentNode.right){  // RR  左旋转(单旋)
                rorateLeft(grandNode);
                System.out.println("RR 左旋转");
            }else {                         // RL  右旋转 左旋转
                rorateRight(parentNode);
                rorateLeft(grandNode);

                System.out.println("RL  右旋转 左旋转");
            }
        }
    }

    // g p n
    private void rebalanced2(Node<E> grand){
        Node<E> parent = ((AVLNode)grand).tallerChildren();     // 获取失衡节点较高的子节点
        Node<E> node = ((AVLNode<E>)parent).tallerChildren();   // 获取子节点较高的子节点

        if (parent == grand.left) {         // L
            if (node == parent.left){       //  LL 右旋转
                rorate(grand,node.left,node,node.right,parent,parent.right,grand,grand.right);
            }else {                         // LR
                //  a b c  d   e  f  g
                rorate(grand,parent.left,parent,node.left,node,node.right,grand,grand.right);
             }

        }else {     // R
            if (node == parent.right) {     // RR
                rorate(grand,grand.left,grand,parent.left,parent,node.left,node,node.right);
            }else {                         // RL
                rorate(grand,grand.left,grand,node.left,node,node.right,parent,parent.right);
            }
        }


    }

    /**
     *  统一旋转
     * @param root
     *
                  d
            b           f
        a       c   e       g

     */
    private void rorate(Node<E> root,
                        Node<E> a , Node<E> b ,Node<E> c,
                        Node<E> d,
                        Node<E> e , Node<E> f, Node<E> g){

        d.parent = root.parent;
        if (root.parent == null){
            this.root = d;      // 修改根节点
        }else if (root.parent.left == root) {
            root.parent.left = d;
        }else{
            root.parent.right = d;
        }

        b.left = a;
        b.right = c;
        if (a != null) a.parent = b;
        if (c != null) c.parent = b;

        updateHeight(b);        // 修改高度

        f.left = e;
        f.right = g;
        if (e != null) e.parent = f;
        if (g != null) g.parent = f;

        updateHeight(f);        // 修改高度

        d.left = b;
        d.right = f;

        b.parent = d;
        f.parent = d;
        updateHeight(d);        // 修改高度
    }


    private void rorateRight(Node<E> node){
        /**
         *      20
         *    15
         *  14
         */
        Node<E> p = ((AVLNode)node).tallerChildren();       // 等价于 node.right
        node.left = p.right;
        if (p.right != null) p.right.parent = node;

        p.right = node;
        p.parent = node.parent;
        if (node.parent == null) {      // 失衡节点为树的根节点. 将root指向p
            root = p;
        } else if (node == node.parent.left) {
            node.parent.left = p;
        }else {
            node.parent.right = p;
        }
        node.parent = p;

        updateHeight(node);
        updateHeight(p);
    }

    private void rorateLeft(Node<E> node){
        Node<E> p = ((AVLNode) node).tallerChildren();      // 等价于 node.left
        node.right = p.left;
        if (p.left != null) p.left.parent = node;
        p.left = node;
        p.parent = node.parent;

        if (node.parent == null){
            root = p;
        }else if (node == node.parent.left) {
            node.parent.left = p;
        }else {
            node.parent.right = p;
        }
        node.parent = p;

        updateHeight(node);
        updateHeight(p);
    }



    // 平衡因子的绝对值小于等于1 则是平衡的  计算平衡因子 左子树高度 - 右子树高度
    private boolean isBalanced(Node<E> node) {
        AVLNode avlNode = (AVLNode) node;

        return Math.abs(avlNode.balancedFactor()) <= 1;
    }


    @Override
    protected Node<E> createNode(E element, Node<E> parent) {

        return new AVLNode<>(element,parent);
    }

    private void updateHeight(Node<E> node) {
        AVLNode<E> avlNode = (AVLNode<E>) node;
        avlNode.updateHeight();
    }


    private static class AVLNode<E> extends Node<E> {
        int height = 1;     //默认高度为1


        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        // 获取平衡因子
        public int balancedFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;

            return leftHeight - rightHeight;
        }

        // 自身高度等于 左子树和右子树高度之间的最大值+1
        public void updateHeight(){
            int leftHeight = left == null ? 0 : ((AVLNode) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode) right).height;

            height = 1 + Math.max(leftHeight,rightHeight);
        }

        // 获取左右子树中较高的节点
        public Node<E> tallerChildren(){
            int leftHeight = left == null ? 0 : ((AVLNode) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode) right).height;

            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;

            return isLeftChildren() ? left : right;
        }

        @Override
        public String toString() {
            return element+"-"+height;
        }
    }
}
