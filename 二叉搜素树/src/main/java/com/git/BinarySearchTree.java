package com.git;

import com.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree<E> implements BinaryTreeInfo {

    private Node<E> root;       // 根节点
    private int size;           // 节点数量

    private Comparator<E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        elementNotNullCheck(element);

        // 第一次添加节点
        if (root == null) {
            root = new Node<E>(element, null);
            size++;
            return;
        }

        Node<E> node = root;
        Node<E> parent = root;      //  新节点的父节点
        int res = 0;
        while (node != null) {
            parent = node;
            res = compare(element, node.element);
            // 大于  则存放右节点
            if (res > 0) {
                node = node.right;
            } else if (res < 0) {        // 小于  存放左节点
                node = node.left;
            } else {     // 相等
                node.element = element;
                return;
            }
        }

        Node<E> newNode = new Node<E>(element, parent);
        if (res > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }

        return ((Comparable) e1).compareTo(e2);
    }


    public boolean remove(E element) {
        if (size == 0) throw new IllegalStateException("size: 0");

        Node<E> node = root;
        int res = 0;
        while (node != null) {
            res = compare(element, node.element);
            if (res > 0) {
                node = node.right;      // 大于节点元素 向右节点进行遍历
            } else if (res < 0) {
                node = node.left;       // 小于节点元素   向左节点进行遍历
            } else {             // 等于0
                return false;
            }
        }

        return false;
    }


    public void revertTree() {
        if (root == null) return;
        Node<E> node = root;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node<E> temp = queue.poll();

            Node<E> left = temp.left;
            temp.left = temp.right;
            temp.right = left;

            if (temp.left != null) {
                queue.offer(temp.left);
            }

            if (temp.right != null) {
                queue.offer(temp.right);
            }
        }
    }


    /**
     * 判断是否是一颗完全二叉树
     * 完全二叉树, 从根节点开始,从上至下,从左至右开始编号,其所有编号都与相同高度的满二叉树编号对应
     * <p>
     * 完全二叉树的叶子节点全在最后一层或者两层,并且度为1的节点,要么存在1个,要么0个,并且这个节点必须是左节点,完全二叉树的最后2层到根节点
     * 是一颗满二叉树
     *
     * @return
     */
    public boolean isComplete() {
        if (root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = false;     //  记录度为1的节点是否出现
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            if (flag && !node.isLeaf()) {      // 度为1的节点已经出现,接下来的节点则必须是叶子节点
                return false;
            }

            if (node.left != null && node.right != null) {
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left != null && node.right == null) {        // 度为1的节点
                flag = true;
            } else if (node.left == null && node.right != null) { // 左子节点为空  右子节点不为空 违反完全二叉树性质
                return false;
            }
        }

        return true;
    }

    /**
     * 二叉树的高度
     *
     * @return
     */
    public int height() {
        if (root == null) return 0;
        int height = 0;
        int rowSize = 1;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            rowSize--;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }

            if (rowSize == 0) {
                rowSize = queue.size();
                height++;
            }
        }

        return height;
    }

    /**
     * 后继节点:  右子树中的最左节点
     *
     * @param node
     * @return
     */
    private Node<E> successor(Node<E> node) {
        if (node == null) return null;

        if (node.right != null) {
            Node<E> successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            return successor;
        } else if (node.right == null && node.parent != null) {
            Node<E> successor = node.parent;
            while (successor != null && successor != successor.parent.right) {
                successor = successor.parent;
            }
            return successor;
        } else {     // node.right == null && node.parent == null
            // node.right == null && node.left == null
            return null;
        }


    }

    /**
     * 前驱节点    左子树的最右节点
     *
     * @param node
     * @return
     */
    private Node<E> predecessor(Node<E> node) {
        if (node == null) return node;

        // 存在左子树   则前驱节点在左子树中的最右节点
        if (node.left != null) {
            Node<E> precursor = node.left;
            while (precursor.right != null) {
                precursor = precursor.right;
            }
            return precursor;
            // 不存在左子树  则前驱节点在祖父节点中, 这个父节点需要满足条件:   父节点的父节点的右节点 == 父节点(出现的第一次则是前驱节点)
        } else if (node.left == null && node.parent != null) {
            Node<E> precursor = node.parent;
            while (precursor != null && precursor.parent.right != precursor) {
                precursor = precursor.parent;
            }
            return precursor;
        } else {  // node.left == null && node.parent == null
            // node.left == null && node.right == null;
            return null;
        }
    }


    /**
     * 前序遍历  递归方式
     *
     * @param visitor
     */
    public void preorderTraversal(Visitor<E> visitor) {
        preorderTraversal(root, visitor);
    }

    /**
     * 前序遍历  迭代方式
     *
     * @param visitor
     */
    public void preorderTraversal2(Visitor<E> visitor) {
        preorderTraversal2(root, visitor);
    }


    /**
     * 中序遍历  递归方式
     *
     * @param visitor
     */
    public void inorderTraversal(Visitor<E> visitor) {
        inorderTraversal(root, visitor);
    }


    /**
     * 中序遍历   迭代方式
     *
     * @param visitor
     */
    public void inorderTraversal2(Visitor<E> visitor) {
        inorderTraversal2(root, visitor);
    }


    /**
     * 后续遍历  递归方式
     *
     * @param visitor
     */
    public void postorderTraversal(Visitor<E> visitor) {
        postorderTraversal(root, visitor);
    }


    /**
     * 后续遍历  迭代
     *
     * @param visitor
     */
    public void postorderTraversal2(Visitor<E> visitor) {
        postorderTraversal2(root, visitor);
    }


    public void levelOrderTraversal(Visitor<E> visitor) {
        levelOrderTraversal(root, visitor);
    }


    /**
     * 前序遍历   递归方式
     *
     * @param node
     */
    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        visitor.visitor(node.element);
        preorderTraversal(node.left, visitor);
        preorderTraversal(node.right, visitor);
    }


    /**
     * 前序遍历  迭代方式
     *
     * @param node
     * @param visitor
     */
    private void preorderTraversal2(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            node = stack.pop();
            visitor.visitor(node.element);

            if (node.right != null) stack.push(node.right);

            if (node.left != null) stack.push(node.left);
        }
    }

    /**
     * 中序遍历    递归方式
     *
     * @param node
     * @param visitor
     */
    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        inorderTraversal(node.left, visitor);
        visitor.visitor(node.element);
        inorderTraversal(node.right, visitor);
    }

    /**
     * 中序遍历  迭代方式  左子树 根节点 右子树
     *
     * @param node
     * @param visitor
     */
    private void inorderTraversal2(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
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

    /**
     * 后续遍历   递归方式
     *
     * @param node
     * @param visitor
     */
    private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        postorderTraversal(node.left, visitor);
        postorderTraversal(node.right, visitor);
        visitor.visitor(node.element);

    }


    /**
     * 后续遍历 迭代
     *
     * @param node
     * @param visitor
     */
    private void postorderTraversal2(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node<E> temp = stack.pop();

            if (temp != null) {
                stack.push(temp);
                stack.push(null);

                if (temp.right != null) stack.push(temp.right);

                if (temp.left != null) stack.push(temp.left);

            } else {
                temp = stack.pop();
                visitor.visitor(temp.element);
            }
        }
    }


    private void levelOrderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node<E> temp = queue.poll();
            visitor.visitor(temp.element);
            if (temp.left != null) {
                queue.offer(temp.left);
            }
            if (temp.right != null) {
                queue.offer(temp.right);
            }
        }
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {

    }

    public boolean contains(E element) {
        return false;
    }


    private void elementNotNullCheck(E element) {
        if (element == null)
            throw new IllegalArgumentException("element is must not be null");
    }

    public Object root() {
        return root;
    }

    public Object left(Object node) {
        if (node == null) return node;

        return ((Node) node).left;
    }

    public Object right(Object node) {
        if (node == null) return node;

        return ((Node) node).right;
    }

    public Object string(Object node) {
        if (node == null) return node;

        return ((Node) node).element;
    }

    public static interface Visitor<E> {
        public void visitor(E element);
    }

    private static class Node<E> {

        Node<E> left;       // 左节点
        Node<E> right;      // 右节点
        Node<E> parent;     // 父节点
        E element;

        public Node(E element, Node<E> parent) {
            this.parent = parent;
            this.element = element;
        }

        /**
         * 叶子节点判断  左子节点和右子节点为空
         *
         * @return
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    /**
     * 前序遍历
     *
     * @param visitor
     */
    public void preorderTraversal3(Visitor<E> visitor) {
        if (root == null) return;
        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            node = stack.pop();
            visitor.visitor(node.element);

            if (node.right != null) stack.push(node.right);

            if (node.left != null) stack.push(node.left);
        }
    }


    /**
     * 中序遍历 左 根 右
     *
     * @param visitor
     */
    public void inorderTraversal3(Visitor<E> visitor) {
        if (root == null) return;

        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();

        while (node != null || !stack.isEmpty()) {
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


    /**
     * 后续遍历
     */
    public void postorderTraversal3(Visitor<E> visitor) {
        if (root == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();

            if (node != null) {
                stack.push(node);
                stack.push(null);

                if (node.right != null) stack.push(node.right);

                if (node.left != null) stack.push(node.left);


            } else {
                node = stack.pop();
                visitor.visitor(node.element);
            }
        }
    }

    /**
     *  层序遍历
     * @param visitor
     */
    public void levelOrderTraversal3(Visitor<E> visitor){
        if (root == null) return;
        Node<E> node = root;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);

        while (! queue.isEmpty()) {
            node = queue.poll();
            visitor.visitor(node.element);

            if (node.left != null) queue.offer(node.left);

            if (node.right != null) queue.offer(node.right);
        }
    }

    public int height2(){
        if (root == null) return 0;
        Node<E> node = root;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);

        int height = 0;
        int rowSize = 1;        // 记录每一层有多少个节点

        while (! queue.isEmpty()) {
            node = queue.poll();
            rowSize --;
            if (node.left != null) queue.offer(node.left);

            if (node.right != null) queue.offer(node.right);

            if (rowSize == 0) {
                height ++;
                rowSize = queue.size();
            }
        }
        return height;
    }

    public boolean isComplete2(){

        if (root == null) return false;
        Node<E> node = root;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        boolean flag = false;
        while (! queue.isEmpty()) {
            node = queue.poll();

            if (flag && node.left != null && node.right != null) {
                return false;
            }

            if (node.left != null && node.right != null) {
                queue.offer(node.left);
                queue.offer(node.right);
            }else if (node.left == null && node.right != null){
                return false;
            }else if (node.left != null && node.right == null) {
                flag = true;
            }
        }

        return true;
    }


    public void revertTree2(){
        if (root == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (! queue.isEmpty()) {
            Node<E> node = queue.poll();

            Node<E> temp = node.left;
            node.left = node.right;
            node.right = temp;

            if (node.left != null) queue.offer(node.left);

            if (node.right != null) queue.offer(node.right);
        }
    }

}
