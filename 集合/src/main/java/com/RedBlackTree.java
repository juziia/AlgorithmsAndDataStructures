package com;

import com.printer.BinaryTreeInfo;

import java.util.Comparator;

public class RedBlackTree<E> implements BinaryTreeInfo {
    private static final boolean RED = false;       // 红色false
    private static final boolean BLACK = true;      // 黑色true

    private Comparator<E> comparator;

    public RedBlackTree() {
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private Node<E> root;
    private int size;


    public void add(E element) {
        elementNotNull(element);

        if (root == null) {
            root = new Node<>(element, null);
            size++;
            fixAfterInsertion(root);
            return;
        }

        Node<E> node = root;
        Node<E> parent = null;

        int c = 0;
        while (node != null) {
            c = compare(element, node.element);
            parent = node;

            if (c > 0) {
                node = node.right;
            } else if (c < 0) {
                node = node.left;
            } else {    // 等于0
                node.element = element;
                return;
            }
        }

        Node<E> newNode = new Node<>(element, parent);
        if (c > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        fixAfterInsertion(newNode);
    }

    /**
     * 修复添加了节点之后的红黑树
     *
     * @param node 新添加的节点
     */
    public void fixAfterInsertion(Node<E> node) {

        while (node.parent != null) {

            Node<E> parent = node.parent;
            // 父节点是黑色,不需要处理
            if (isBlack(parent)) return;

            Node<E> uncle = parent.sibling();
            Node<E> grand = parent.parent;
            if (isRed(uncle)) { // 叔父节点是红色, 属于上溢情况
                // 将叔父节点,父节点染成黑色,祖父节点染成红色向上合并(把它当作一个新添加的节点处理)
                black(uncle);
                black(parent);
                red(grand);
                node = grand;
                continue;
            }

            // 叔父节点是黑色, 父亲节点是红色  double red
            if (grand.left == parent) {   // L
                if (parent.left == node) {  // LL
                    black(parent);
                    red(grand);
                    rorateRight(grand);
                } else {     // LR
                    black(node);
                    red(grand);
                    rorateLeft(parent);
                    rorateRight(grand);
                }
            } else { // R
                if (parent.right == node) { // RR
                    black(parent);
                    red(grand);
                    rorateLeft(grand);
                } else { // RL
                    black(node);
                    red(grand);
                    rorateRight(parent);
                    rorateLeft(grand);
                }

            }

            break;
        }

        if (node.parent == null) {   // 根节点
            black(node);
        }

    }


    public boolean remove(E element) {

        return remove(node(element));
    }

    private boolean remove(Node<E> node) {
        if (node == null) return false;

        // 被删除的是度为2的节点 (度为2的节点的后继节点必然是度为1或者0的节点)
        if (node.left != null && node.right != null) {
            //找到它的后继节点
            Node<E> successor = successor(node);
            node.element = successor.element;
            node = successor;
        }

        Node<E> replacement = node.left != null ? node.left : node.right;
        Node<E> parent = node.parent;
        // 被删除的是度为1的节点
        if (replacement != null) {
            replacement.parent = parent;
            if (parent == null) {
                root = replacement;
            } else if (parent.left == node) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
            fixAfterDeletion(replacement);
        } else {    // 被删除的是度为0的节点
            if (parent == null) { // 父节点是null, 根节点
                root = null;
            } else if (parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            fixAfterDeletion(node);
        }
        return true;
    }

    /**
     *
     * @param node  可能是被删除的节点, 可能是被删除节点的替代节点
     */
    private void fixAfterDeletion(Node<E> node){
        while (node != null) {
            // 如果删除节点的替代节点是红色,那么染黑即可
            // 如果删除的节点是红色,可以不用处理,这里将其染色也没有关系
            if (isRed(node)) {
                black(node);
                return;
            }

            Node<E> parent = node.parent;
            // 根节点
            if (parent == null) return;

            boolean right = parent.right == null || node.isRightChildren();
            Node<E> sibling = right ? parent.left : parent.right;

            // 删除的是右边
            if (right) {
                // 判断兄弟节点是否是红色
                if (isRed(sibling)){
                    // 兄弟节点是红色, (没有节点可以借) 兄弟节点是红色, 那么父节点必然是黑色,否则会是 double red情况
                    black(sibling);
                    red(parent);
                    rorateRight(parent);
                    sibling = parent.left;
                }

                // 兄弟节点必然是黑色
                if (isBlack(sibling.left) && isBlack(sibling.right)) {
                    // 兄弟节点的左右子节点全是黑色, 没有红色子节点可以借,那么父节点需要与子节点合并
                    // 记录父节点的颜色是否是黑色
                    boolean parentBlack = isBlack(parent);
                    red(sibling);
                    black(parent);
                    // 如果父节点本来就是黑色, 那么将父节点向下合并之后,父节点也会发成下溢情况,此时只需要将父节点当作被删除的节点处理即可
                    if (parentBlack){
                        node = parent;
                        continue;
                    }
                }else { // 兄弟节点必然存在红色子节点
                    if (isBlack(sibling.left)) {
                        // 兄弟节点的左子节点为黑色,那么红色子节必然点是兄弟节点的右子节点
                        rorateLeft(sibling);    // 对兄弟节点进行左旋转
                        sibling = parent.left;
                    }
                    // 兄弟节点的左子节点必然是红色
                    color(sibling,colorOf(parent));
                    rorateRight(parent);
                    black(sibling.left);
                    black(sibling.right);
                }
                // 执行完上面的逻辑,那么红黑树必然已经修复完成
                break;
            }else {  // symmetric
                // 判断兄弟节点是否是红色
                if (isRed(sibling)){
                    // 兄弟节点是红色, (没有节点可以借) 兄弟节点是红色, 那么父节点必然是黑色,否则会是 double red情况
                    black(sibling);
                    red(parent);
                    rorateLeft(parent);
                    sibling = parent.right;
                }

                // 兄弟节点必然是黑色
                if (isBlack(sibling.left) && isBlack(sibling.right)) {
                    // 兄弟节点的左右子节点全是黑色, 没有红色子节点可以借,那么父节点需要与子节点合并
                    // 记录父节点的颜色是否是黑色
                    boolean parentBlack = isBlack(parent);
                    red(sibling);
                    black(parent);
                    // 如果父节点本来就是黑色, 那么将父节点向下合并之后,父节点也会发成下溢情况,此时只需要将父节点当作被删除的节点处理即可
                    if (parentBlack){
                        node = parent;
                        continue;
                    }
                }else { // 兄弟节点必然存在红色子节点
                    if (isBlack(sibling.right)) {
                        // 兄弟节点的左子节点为黑色,那么红色子节必然点是兄弟节点的右子节点
                        rorateRight(sibling);    // 对兄弟节点进行左旋转
                        sibling = parent.right;
                    }
                    // 兄弟节点的左子节点必然是红色
                    color(sibling,colorOf(parent));
                    rorateLeft(parent);
                    black(sibling.left);
                    black(sibling.right);
                }
                // 执行完上面的逻辑,那么红黑树必然已经修复完成
                break;
            }


        }
    }


    public boolean contains(E element) {
        return node(element) != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    private void rorateLeft(Node<E> rorateNode) {
        Node<E> node = rorateNode.right;

        rorateNode.right = node.left;
        if (node.left != null) node.left.parent = rorateNode;
        node.left = rorateNode;

        node.parent = rorateNode.parent;
        if (rorateNode.parent == null) {
            root = node;
        } else if (rorateNode.parent.left == rorateNode) {
            rorateNode.parent.left = node;
        } else {
            rorateNode.parent.right = node;
        }
        rorateNode.parent = node;
    }

    private void rorateRight(Node<E> rorateNode) {
        Node<E> node = rorateNode.left;

        rorateNode.left = node.right;
        if (node.right != null) node.right.parent = rorateNode;
        node.right = rorateNode;

        node.parent = rorateNode.parent;
        if (rorateNode.parent == null) {
            root = node;
        } else if (rorateNode.parent.left == rorateNode) {
            rorateNode.parent.left = node;
        } else {
            rorateNode.parent.right = node;
        }
        rorateNode.parent = node;
    }


    private static class Node<E> {
        boolean color = RED;        // 默认为红色
        E element;
        Node<E> parent; // 父节点
        Node<E> left;   // 左子节点
        Node<E> right;  // 右子节点

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }


        // 判读是否为叶子节点
        public boolean isLeaf() {
            return left == null && right == null;
        }

        // 判读是否有两个子节点
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        // 判读自己是否为父节点的左子节点
        public boolean isLeftChildren() {
            return parent != null && this == parent.left;
        }

        // 判读字节是否为父节点的右子节点
        public boolean isRightChildren() {
            return parent != null && this == parent.right;
        }

        // 兄弟节点
        public Node<E> sibling() {
            if (isLeftChildren()) return parent.right;
            if (isRightChildren()) return parent.left;
            return null;
        }


        @Override
        public String toString() {
            String str = "";
            if (color == RED) str = "R_";
            return str + element;
        }
    }

    // 前驱节点
    private Node<E> preocessor(Node<E> node) {
        if (node == null) return null;

        // 如果左子树不为空,那么前驱就存在于左子树中,并且是值最大的节点
        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        // 没有左子树,那么前驱节点存在于父节点或者祖父节点中的最后一个右子节点的父节点
        while (node.parent != null && node.parent.right != node) {
            node = node.parent;
        }
        return node.parent;
    }

    // 后继节点
    private Node<E> successor(Node<E> node) {
        if (node == null) return null;

        // 如果右子树不为空,那么后继节点一定存在与右子树中,并且是值最小的节点
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        // 如果右子树为空,那么后继节点存在于父节点或者祖父节点的最后一个左子节点的父节点
        while (node.parent != null && node.parent.left != node) {
            node = node.parent;
        }
        return node.parent;
    }

    private Node<E> node(E element) {
        elementNotNull(element);

        Node<E> node = root;
        while (node != null) {
            int c = compare(element, node.element);
            if (c == 0) return node;

            if (c > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private int compare(E e1, E e2) {
        if (comparator != null)
            return comparator.compare(e1, e2);

        return ((Comparable<E>) e1).compareTo(e2);
    }

    private void elementNotNull(E element) {
        if (element == null)
            throw new IllegalArgumentException(" element is must be not null");
    }


    private void red(Node<E> node) {
        color(node, RED);
    }

    private void black(Node<E> node) {
        color(node, BLACK);
    }


    private void color(Node<E> node, boolean color) {
        if (node == null) return;
        node.color = color;
    }


    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : node.color;
    }


    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node) node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
    }

}
