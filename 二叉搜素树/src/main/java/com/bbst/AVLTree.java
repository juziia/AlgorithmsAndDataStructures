package com.bbst;

import com.printer.BinaryTrees;

/**
 * 平衡二叉搜素树:  AVL树
 * @param <E>
 */
public class AVLTree<E> extends BinarySearchTree<E> {

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element,parent);
    }

    @Override
    protected void afterAdd(Node<E> node) {

        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 平衡则更新高度
                updateHeight(node);
            }else {
                // 恢复平衡
                rebalanced(node);
                // 只需要对高度最低的失衡节点恢复平衡,整棵树就恢复平衡了
                break;
            }
        }

    }



    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            }else {
                rebalanced(node);
            }
        }
    }

    private void rebalanced(Node<E> grand) {
        Node<E> parent = ((AVLNode) grand).tallerChildren();
        Node<E> node = ((AVLNode) parent).tallerChildren();

        if (parent == grand.left) {     // L
            if (node == parent.left) {    // LL
                rorateRight(grand);
            }else {    // LR
                rorateLeft(parent);
                rorateRight(grand);
            }
        }else {     // R
            if (node == parent.right) { // RR
                rorateLeft(grand);
            }else { // RL
                rorateRight(parent);
                rorateLeft(grand);
            }
        }

    }

    private void rebalanced2(Node<E> grand){
        Node<E> parent = ((AVLNode)grand).tallerChildren();
        Node<E> node = ((AVLNode)parent).tallerChildren();

        if (grand.left == parent) {  // L
            if (parent.left == node) {    // LL
                rorate(grand,node.left,node,node.right,parent,parent.right,grand,grand.right);
            }else {                       // LR
                rorate(grand,parent.left,parent,node.left,node,node.right,grand,grand.right);
            }
        }else {         // R
           if (parent.right == node){       // RR
               rorate(grand,grand.left,grand,parent.left,parent,node.left,node,node.right);
           }else {                          // RL
               rorate(grand,grand.left,grand,node.left,node,node.right,parent,parent.right);
           }
        }

    }

    /**
     *
     * @param rorateNode 旋转节点
     */
    private void rorateLeft(Node<E> rorateNode){
        Node<E> node = rorateNode.right;

        rorateNode.right = node.left;
        if (node.left != null) node.left.parent = rorateNode;

        node.left = rorateNode;
        node.parent = rorateNode.parent;

        // 让node成为根节点
        if (rorateNode.parent == null) {        // 根节点
            root = node;
        }else if (rorateNode.parent.left == rorateNode) {
            rorateNode.parent.left = node;
        }else { // parent.parent.right == parent
            rorateNode.parent.right = node;
        }
        rorateNode.parent = node;

        // 更新已经交换过子节点的父节点       (在上面已经完成)
//        if (rorateNode.right != null) rorateNode.right.parent = rorateNode;

        // 先更新比较矮的节点,再更新高的
        updateHeight(rorateNode);
        updateHeight(node);

    }

    /**
     *
     * @param rorateNode  旋转节点
     */
    private void rorateRight(Node<E> rorateNode){
        Node<E> node = rorateNode.left;

        rorateNode.left = node.right;
        if (node.right != null) node.right.parent = rorateNode;

        node.right = rorateNode;
        node.parent = rorateNode.parent;

        // node成为根节点
        if (rorateNode.parent == null) {
            root = node;
        }else if (rorateNode.parent.left == rorateNode){
            rorateNode.parent.left = node;
        }else { // rorateNode.parent.right = rorateNode
            rorateNode.parent.right = node;
        }
        rorateNode.parent = node;

        // 更改已经交换的子节点的父节点   (在上面已经完成)
//        if (rorateNode.left != null) rorateNode.left.parent = rorateNode;

        updateHeight(rorateNode);
        updateHeight(node);
    }

    /**
     *
     * @param root  原根节点
     *
     *                d
     *             b       f
     *          a    c   e   g
     */
    private void rorate(Node<E> root,       // 原根节点
                        Node<E> a, Node<E> b,Node<E> c,
                        Node<E> d,
                        Node<E> e, Node<E> f,Node<E> g){

        Node<E> parent = root.parent;
        d.parent = parent;
        if (parent == null) {       // 根节点
            this.root = d;
        }else if (parent.left == root){
            parent.left = d;
        }else { //  (parent.right == root)
            parent.right = d;
        }
        // a b c
        b.left = a;
        b.right = c;
        if (a != null) a.parent = b;
        if (c != null) c.parent = b;
        updateHeight(b);


        // e f g
        f.left = e;
        f.right = g;
        if (e != null) e.parent = f;
        if (g != null) g.parent = f;
        updateHeight(f);

        // b d f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;

        // 更新高度
        updateHeight(d);
    }


    // 判断是否平衡:  一个节点的平衡因子只可能是 -1, 0, 1,它的左右子树高度差不可能超过2
    //  如果一个节点的平衡因子的绝对值小于等于1,那么它是平衡的, 如果一个节点的平衡因子的绝对值大于1,那么这个节点就失衡了
    private boolean isBalanced(Node<E> node){

        return Math.abs(((AVLNode)node).balancedFactor()) <= 1;
    }

    private void updateHeight(Node<E> node) {
        ((AVLNode)node).updateHeight();
    }


    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /*平衡因子:  一个节点的平衡因子为它的左右子树的高度差**/
        public int balancedFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode) right).height;

            return leftHeight - rightHeight;
        }

        public void updateHeight(){
            // 一个节点的高度为它的左右子树高度最大值 +1
            int leftHeight = left == null ? 0 : ((AVLNode)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;

            height = 1 + Math.max(leftHeight,rightHeight);
        }

        public Node<E> tallerChildren(){
            int balancedFactor = balancedFactor();
            if (balancedFactor > 0){        // 左子树高
                return left;
            }else if (balancedFactor < 0){      // 右子树高
                return right;
            }else {     // 相同高,返回相同方向的子节点
                if (this == parent.left)
                    return left;
                else
                    return right;
            }
        }

        @Override
        public String toString() {

            return element.toString();
        }
    }
}
