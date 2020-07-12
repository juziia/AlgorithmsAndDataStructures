package com.bbst;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.TreeMap;

/**
 * 添加看叔父颜色: 是否上溢
 * 删除看兄弟颜色: 是否可以借一个元素出来
 * @param <E>
 */
public class RedBlackTree<E> extends BinarySearchTree<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    /**
     *  红黑树添加总结:
     *          添加共12种情况,12种情况分3类,一类分别4种
     *
     *          1. 新添加的节点的父节点是黑色,不需要处理 (4种情况)
     *          2. 新添加的节点的叔父节点是红色,属于上溢情况,需要对叔父节点,父节点染成黑色,祖父节点染成红色,
     *            让祖父节点作为一个节点向上合并,合并的逻辑就相当于将祖父当作一个新添加的节点进行处理
     *          3. 新添加的节点的叔父节点是黑色,这时候就需要进行旋转操作
     *              如果是LL RR情况: 父节点染成黑色,祖父节点染成红色,祖父节点单旋
     *              如果是LR RL情况: 自己染成黑色,祖父节点染成红色,父节点旋转,祖父节点旋转
     *
     * @param node
     */
//    @Override
    protected void afterAdd2(Node<E> node) {
        // 获取新添加节点的父节点
        Node<E> parent = node.parent;
        if (parent == null) {   // 父节点为空,则为根节点
            // 染黑
            black(node);
            return;
        }
        // 父节点是黑色,不需要处理 (已经处理了4种情况,父节点是黑色)
        if (isBlack(parent)) return;

        // 获取叔父节点
        Node<E> uncle = parent.sibling();
        // 祖父节点
        Node<E> grand = parent.parent;
        if (isRed(uncle)) { // 叔父节点是红色,属于上溢情况 (4种情况处理完毕, 叔父节点是红色,上溢情况,染色即可)
            // B树节点上溢处理:  将上溢节点的中间节点向上与父节点合并,上溢节点进行分裂成为两个子节点,并被向上合并的节点分别指向
            // 所以将叔父染成黑色,父亲染成黑色,父节点染成红色  合并的逻辑就如同新添加的节点的处理逻辑
            black(uncle);
            black(parent);
            red(grand);
            afterAdd(grand);
            return;
        }
        // 叔父节点是黑色
        //这时候就需要判断是LL RR LR RL中的情况了
        if (parent == grand.left) { // L
            if (node == parent.left) {  // LL
                // 右旋转
                // LL RR 都是将父节点染成黑色,祖父节点染成红色, 祖父节点单旋即可
                black(parent);
                red(grand);
                // 对祖父节点进行右旋转
                rorateRight(grand);
            } else { // LR 左旋转 右旋转
                black(node);
                red(grand);
                rorateLeft(parent);
                rorateRight(grand);
            }
        } else {    // R
            if (node == parent.right) { // RR
                black(parent);
                red(grand);
                rorateLeft(grand);
            }else{      // RL
                black(node);
                red(grand);
                rorateRight(parent);
                rorateLeft(grand);
            }
        }

    }


    protected void afterAdd(Node<E> node) {

        // 父节点不为空
        while (node.parent != null) {
            Node<E> parent = node.parent;

            // 父节点是黑色,不需要处理 (已经处理了4种情况,父节点是黑色)
            if (isBlack(parent)) return;

            // 获取叔父节点
            Node<E> uncle = parent.sibling();
            // 祖父节点
            Node<E> grand = parent.parent;
            if (isRed(uncle)) { // 叔父节点是红色,属于上溢情况 (4种情况处理完毕, 叔父节点是红色,上溢情况,染色即可)
                // B树节点上溢处理:  将上溢节点的中间节点向上与父节点合并,上溢节点进行分裂成为两个子节点,并被向上合并的节点分别指向
                // 所以将叔父染成黑色,父亲染成黑色,父节点染成红色  合并的逻辑就如同新添加的节点的处理逻辑
                black(uncle);
                black(parent);
                red(grand);

                node = grand;
                continue;
            }
            // 叔父节点是黑色
            //这时候就需要判断是LL RR LR RL中的情况了
            if (parent == grand.left) { // L
                if (node == parent.left) {  // LL
                    // 右旋转
                    // LL RR 都是将父节点染成黑色,祖父节点染成红色, 祖父节点单旋即可
                    black(parent);
                    red(grand);
                    // 对祖父节点进行右旋转
                    rorateRight(grand);
                } else { // LR 左旋转 右旋转
                    black(node);
                    red(grand);
                    rorateLeft(parent);
                    rorateRight(grand);
                }
            } else {    // R
                if (node == parent.right) { // RR
                    black(parent);
                    red(grand);
                    rorateLeft(grand);
                } else {      // RL
                    black(node);
                    red(grand);
                    rorateRight(parent);
                    rorateLeft(grand);
                }
            }
            // 只要旋转完毕,证明此红黑树已经修复完成,直接退出循环
            break;
        }
        // 获取新添加节点的父节点
        if (node.parent == null) {   // 父节点为空,则为根节点
            // 染黑
            black(node);
            return;
        }
    }


    /**
     *  删除总结:
     *      1. 如果删除的是红色叶子节点,不需要处理
     *      2. 如果删除节点的替代节点是红色,染黑即可
     *      3. 如果删除的节点的父节点是null,不需要处理
     *      4. 如果删除的是黑色节点,那么就需要判断删除的是其父节点的哪个子节点
     *          1) 删除的是右边
     *              A) 判断它的兄弟节点是否是红色
     *                 a) 如果兄弟节点是红色: 属于下溢情况,父节点向下与子节点进行合并,那么需要将它的兄弟节点染黑,
     *               父节点染红,对父节点进行右旋转,此时被删除的兄弟节点就是父节点的左子节点,并且必然是黑色,
     *               因为原兄弟节点的颜色是红色,那么它的子节点必然是黑色,所以当父节点右旋转后,父节点的左子节点
     *               会指向原兄弟节点的右子节点,那么新的兄弟节点就是父节点的左子节点
     *
     *                 b) 如果兄弟节点是黑色: 则有两种情况
     *                      ①: 兄弟节点的左右子节点都是黑色,没有红色叶子节点(兄弟节点没有节点可以借,父节点需要与子节点合并)
     *                          判断父节点的颜色是否是黑色
     *                              不是黑色: ,那么将兄弟节点染红,父节点染黑
     *                              是黑色: 那么将兄弟节点染红,父节点染黑,将父节点当作被删除的节点重新处理
     *                                  原因: 因为如果父节点本来就是黑色,那么将兄弟节点染黑,父节点染红,向下合并,原来的父节点就
     *                                      产生下溢情况,为了解决父节点产生下溢情况,需要将它当作删除的节点重新处理
     *
     *                      ②:  兄弟节点存在红色子节点
     *                          a) 兄弟节点的左边是黑色,需要先处理一下
     *                              原因: 兄弟节点的左边是黑色,那么红色节点必然在兄弟节点的右边,为了与后面逻辑统一,需要先对其处理
     *
     *                             处理方式: 将兄弟节点左旋转, 此时被删除的节点的兄弟节点是其父节点的左子节点,需要重新赋值
     *                          b) 兄弟节点的左边必然是红色子节点(经过了上面的处理)
     *                               旋转之后的中心节点(兄弟节点)继承其父节点的颜色,再将其左右子节点染黑
     *                                  兄弟节点染黑, 对父节点右旋转, 再对兄弟节点的左右子节点染黑
     *
     *
     *          1) 删除的是左边
     *              与上面的左右子节点,旋转方向相反即可,逻辑相同
     *
     *
     *
     *
     *
     * @param node 被删除的节点
     * @param replacement    替代节点
     */
    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        // 删除的是红色子节点不需要处理 (红色节点必然是叶子节点)
        if (isRed(node)) return;

        // 如果替代节点是红色,将其染成黑色
        if (isRed(replacement)){
            black(replacement);
            return;
        }

        Node<E> parent = node.parent;
        if (parent == null) return;     // 根节点

        // 删除的是父节点的左还是右
        boolean left = parent.left == null || node.isLeftChildren();
        // 获取兄弟节点
        Node<E> sibling = left ? parent.right : parent.left;

        if (left) {
            // 删除的是左边

            // 判断兄弟节点是红色
            if (isRed(sibling)) {
                /**
                 *  这里主要的逻辑是:
                 *      兄弟节点是红色, 那么它的两个子节点必然是黑色,我们就将兄弟节点染成黑色,父节点染成红色
                 *     对父节点进行右旋转, 那么父节点的左边就指向了兄弟节点的右边,此时被删除的节点的兄弟节点就是
                 *     父节点的左子节点
                 */
                // 兄弟节点是红色
                // 将兄弟节点染黑, 父节点染成红色
                black(sibling);
                red(parent);
                // 将父节点右旋转
                rorateLeft(parent);
                // 更改兄弟节点
                sibling = parent.right;
            }

            // 判断兄弟是否有红色子节点
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点的左右子节点都为黑色  属于下溢情况
                // 如果原来的父节点为黑色, 那么父节点向下合并之后,原父节点又产生下溢
                boolean parentBlack = isBlack(parent);
                red(sibling);
                black(parent);
                if (parentBlack) {
                    // 原来的父节点是黑色, 向下合并之后原来的节点就下溢了,这时候就将父节点作为一个被删除的节点处理
                    afterRemove(parent,null);
                }
            }else {
                // 兄弟节点存在红色子节点
                if (isBlack(sibling.right)) {
                    // 兄弟节点的左边为黑色, 那么红色节点在兄弟节点的右边
                    // 对兄弟节点进行左旋转
                    rorateRight(sibling);
                    sibling = parent.right;
                }

                // 红色子节点必然存在于兄弟节点的左边

                color(sibling,colorOf(parent));
                rorateLeft(parent);
                black(sibling.left);
                black(sibling.right);
            }
        }else{
            // 删除的是右边

            // 判断兄弟节点是红色
            if (isRed(sibling)) {
                /**
                 *  这里主要的逻辑是:
                 *      兄弟节点是红色, 那么它的两个子节点必然是黑色,我们就将兄弟节点染成黑色,父节点染成红色
                 *     对父节点进行右旋转, 那么父节点的左边就指向了兄弟节点的右边,此时被删除的节点的兄弟节点就是
                 *     父节点的左子节点
                 */
                // 兄弟节点是红色
                // 将兄弟节点染黑, 父节点染成红色
                black(sibling);
                red(parent);
                // 将父节点右旋转
                rorateRight(parent);
                // 更改兄弟节点
                sibling = parent.left;
            }

            // 判断兄弟是否有红色子节点
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点的左右子节点都为黑色  属于下溢情况
                // 如果原来的父节点为黑色, 那么父节点向下合并之后,原父节点又产生下溢
                boolean parentBlack = isBlack(parent);
                red(sibling);
                black(parent);
                if (parentBlack) {
                    // 原来的父节点是黑色, 向下合并之后原来的节点就下溢了,这时候就将父节点作为一个被删除的节点处理
                    afterRemove(parent,null);
                }
            }else {
                // 兄弟节点存在红色子节点
                if (isBlack(sibling.left)) {
                    // 兄弟节点的左边为黑色, 那么红色节点在兄弟节点的右边
                    // 对兄弟节点进行左旋转
                    rorateLeft(sibling);
                    sibling = parent.left;
                }

                // 红色子节点必然存在于兄弟节点的左边

                color(sibling,colorOf(parent));
                rorateRight(parent);
                black(sibling.left);
                black(sibling.right);
            }

        }





    }

    /**
     * 左旋转
     *
     * @param rorateNode 旋转节点
     */
    private void rorateLeft(Node<E> rorateNode) {
        Node<E> node = rorateNode.right;

        rorateNode.right = node.left;
        if (node.left != null) node.left.parent = rorateNode;
        node.left = rorateNode;

        node.parent = rorateNode.parent;
        if (rorateNode.parent == null) {     // 根节点
            root = node;
        } else if (rorateNode.parent.left == rorateNode) {
            rorateNode.parent.left = node;
        } else {
            rorateNode.parent.right = node;
        }
        rorateNode.parent = node;
    }

    /**
     * 右旋转
     *
     * @param rorateNode 旋转节点
     */
    private void rorateRight(Node<E> rorateNode) {
        Node<E> node = rorateNode.left;

        rorateNode.left = node.right;
        if (node.right != null) node.right.parent = rorateNode;
        node.right = rorateNode;

        node.parent = rorateNode.parent;
        if (rorateNode.parent == null) {    // 根节点
            root = node;
        } else if (rorateNode.parent.left == rorateNode) {
            rorateNode.parent.left = node;
        } else {        // rorateNode.parent.right == rorateNode
            rorateNode.parent.right = node;
        }
        rorateNode.parent = node;
    }


    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    // 染黑
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    // 染红
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return null;
        ((RBNode) node).color = color;
        return node;
    }


    private static class RBNode<E> extends Node<E> {
        boolean color;      // false 为红色  true 为黑色

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) str = "R_";

            return str + element;
        }
    }


}
