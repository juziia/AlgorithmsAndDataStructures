package com.bst;

import com.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉搜素树
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {

    // 根节点
    protected Node<E> root;
    private int size;
    private Comparator<E> comparator;

    public BinarySearchTree() {

    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element) {
        elementNotNull(element);

        // 第一次添加
        if (root == null) {
            root = createNode(element,null);
            size++;
            afterAdd(root);
            return;
        }
        // 非第一次添加
        Node<E> node = root;
        Node<E> parent = null;
        int res = 0;

        while (node != null) {
            res = compare(element, node.element);
            parent = node;
            if (res > 0) { // 大于 则在节点的右边
                node = node.right;
            } else if (res < 0) {   // 小于  则在节点的左边
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }
        Node<E> newNode = createNode(element,parent);
        if (res > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size ++;

        afterAdd(newNode);
    }

    // 用于给平衡二叉搜素树,或者需要在添加之后做平衡操作则重写此方法即可
    protected void afterAdd(Node<E> node){ }

    // 用于AVL树删除节点后恢复平衡
    protected void afterRemove(Node<E> node){}

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element,parent);
    }

    /**
     * 更改值, 先删除,再添加,保证二叉树的性质
     * @param e1
     * @param e2
     * @return
     */
    public boolean set(E e1,E e2 ){
        Node<E> node = node(e1);
        if (node == null) return false;

        remove(node);
        add(e2);
        return  true;
    }

    public boolean remove(E element) {

        return remove(node(element));
    }


    private boolean remove(Node<E> node){
        if (node == null) return false;

        // 度为2的节点,找到它的前驱后者后继节点,这里是找后继节点,用后继节点的值覆盖掉原节点的值
        // 再将后继节点赋值给要删除的节点
        if (node.left != null && node.right != null) {
            // 获取它的后继节点
            Node<E> successor = successor(node);
            // 将后继节点的值覆盖掉原来的节点的值
            node.element = successor.element;
            // 将后继节点的引用传递给node, 用于后续的删除操作
            node = successor;
        }

        // 度为 1 的节点
        if (node.left != null || node.right != null) {

            // 获取它不为空的子节点
            Node<E> n = node.left != null ? node.left : node.right;
            if (node.parent == null) {      // 根节点
                n.parent = null;
                root = n;
            }else {
                // 度为1 ,且有父节点
                n.parent = node.parent;
                if (node.parent.left == node) {
                    node.parent.left = n;
                }else {
                    node.parent.right = n;
                }
            }
            afterRemove(node);
        }else { // 度为0的节点

            if (node.parent == null) {  // 根节点  度为0, 且parent为null
                root = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                }else {
                    node.parent.right = null;
                }
                afterRemove(node);
            }

        }
        size--;
        return true;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean contains(E element) {

        return node(element) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /*判断是否是一颗完全二叉树*/
    public boolean isComplete(){
        if (root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean mustLeaf = false;

        while (! queue.isEmpty()) {
            Node<E> node = queue.poll();

            if (mustLeaf && !( node.left == null && node.right == null) ) {
                return false;
            }

            if (node.left != null && node.right != null) {
                queue.offer(node.left);
                queue.offer(node.right);
            }else if (node.left == null && node.right != null) {    // 左子节点为空 右子节点不为空,违反完全二叉树的性质
                return false;
            }else if (node.left != null && node.right == null) {    // 找到度为1的节点,并且是左子节点,那么接下来的节点必须是叶子节点
                mustLeaf = true;
            }
        }
        return true;
    }

    // 树的高度
    public int height(){
        return height(root);
    }

    protected Node<E> node(E element) {
        elementNotNull(element);

        Node<E> node = root;

        while (node != null) {
            int c = compare(element, node.element);

            if (c == 0) {
                return node;
            } else if (c > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        return null;
    }

    /**
     *  前序遍历
     */
    public void preorderTraversal(Visitor<E> visitor){
        preorderTraversal(root,visitor);
    }

    /**
     * 中序遍历
     */
    public void inorderTraversal(Visitor<E> visitor){
        inorderTraversal(root,visitor);
    }

    /**
     * 后序遍历
     */
    public void postorderTraversal(Visitor<E> visitor){
        postorderTraversal(root,visitor);
    }

    public void levelOrderTraversal(Visitor<E> visitor){
        levelOrderTraversal(root,visitor);
    }


    protected int height(Node<E> node){
        if (node == null) return 0;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        int height = 0;
        int rowSize = 1;        // 记录每一层的节点数量
        while (! queue.isEmpty()) {
            node = queue.poll();
            rowSize --;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null){
                queue.offer(node.right);
            }

            if (rowSize == 0){
                rowSize = queue.size();
                height ++;
            }
        }

        return height;
    }


    /**
     *  前驱节点
     * @param node
     * @return
     */
    protected Node<E> predecessor(Node<E> node){
        if (node == null) return null;

        // 如果左子树不为空, 那么前驱节点必然存在于左子树中,并且是左子树中最大值的节点  left right...
        if (node.left != null) {
            node = node.left;

            while (node.right != null) {
                node = node.right;
            }
            return node;
        }else if (node.left == null && node.parent != null) {
            // 如果左子树为空, 父节点不为空, 那么前驱节点必然是父节点或者祖宗节点中最后一个(最先出现的)右子节点的父节点
            while (node.parent != null && node.parent.right != node) {
                node = node.parent;
            }
            return node.parent;
        }

        return null;
    }

    /**
     * 后继节点
     * @param node
     * @return
     */
    protected Node<E> successor(Node<E> node){
        if (node == null) return null;

        // 如果右子树不为空,那么它的后继节点必然存在右子树中,并且是右子树中值最小的节点 right left....
        if (node.right != null) {
            node = node.right;

            while (node.left != null) {
                node = node.left;
            }
            return node;
        }else if (node.right == null && node.parent != null) {

            // 如果右子树为空, 父节点不为空,那么它的后继节点是父节点或者祖宗节点中最后一个(最先出现)左子节点的父节点
            while (node.parent != null && node.parent.left != node) {
                node = node.parent;
            }
            return node.parent;
        }

        return null;
    }

    private int compare(E e1, E e2) {
        if (comparator != null)
            return comparator.compare(e1, e2);

        return ((Comparable) e1).compareTo(e2);
    }




    /**
     * 节点类
     */
    protected static class Node<E> {
        Node<E> parent;
        Node<E> left;
        Node<E> right;
        E element;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }


        /*判断是否是叶子节点*/
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /*拥有两个节点*/
        public boolean isTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChildren(){
            return parent != null && this == parent.left;
        }

        public boolean isRightChildren(){
            return parent != null && this == parent.right;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    public interface Visitor<E> {
        void visitor(E element);
    }

    private void elementNotNull(E element) {
        if (element == null) throw new IllegalArgumentException("element is must not null");
    }

    /*前序遍历*/
    private void preorderTraversal(Node<E> node,Visitor<E> visitor){
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (! stack.isEmpty()) {
            node = stack.pop();

            visitor.visitor(node.element);

            if (node.right != null) {
                stack.push(node.right);
            }

            if (node.left != null) {
                stack.push(node.left);
            }
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

    private void levelOrderTraversal(Node<E> node,Visitor<E> visitor){
        if (node == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);

        while ( ! queue.isEmpty()) {
            node = queue.poll();
            visitor.visitor(node.element);

            if (node.left != null ) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }




    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        if (node == null) return null;

        return ((Node) node).left;
    }

    @Override
    public Object right(Object node) {
        if (node == null) return null;

        return ((Node) node).right;
    }

    @Override
    public Object string(Object node) {
        if (node == null) return null;

        return ((Node) node);
    }

}
