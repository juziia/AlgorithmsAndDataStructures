package com.map;

import com.printer.BinaryTreeInfo;

import java.util.Comparator;

public class TreeMap<K, V> implements Map<K, V>, BinaryTreeInfo {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node<K, V> root;     // ROOT NODE
    private int size;

    private Comparator<K> comparator;

    public TreeMap() {
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /**
     * @param key
     * @param value old value
     * @return
     */
    public V put(K key, V value) {
        keyNotNull(key);

        if (root == null) {
            root = new Node<K, V>(key, value, null);
            size++;
            fixAfterInsertion(root);
            return null;
        }

        Node<K, V> node = root;
        Node<K, V> parent = null;
        int c = 0;
        while (node != null) {
            c = compare(key, node.key);
            parent = node;

            if (c > 0) {
                node = node.right;
            } else if (c < 0) {
                node = node.left;
            } else { // c == 0
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        Node<K, V> newNode = new Node<K, V>(key, value, parent);
        if (c > 0) {
            parent.right = newNode;
        } else { // c < 0
            parent.left = newNode;
        }
        size++;
        fixAfterInsertion(newNode);
        return null;
    }

    /**
     * 修复新增节点之后的红黑树
     *
     * @param node 新增节点
     */
    private void fixAfterInsertion(Node<K, V> node) {

        while (node.parent != null) {
            Node<K, V> parent = node.parent;
            // 父节点为黑色, 不需要处理
            if (isBlack(parent)) return;

            // 叔父节点
            Node<K, V> uncle = parent.sibling();
            // 祖父节点
            Node<K, V> grand = parent.parent;

            // 判断叔父节点是否为红色
            if (isRed(uncle)) { //上溢情况
                black(uncle);
                black(parent);
                red(grand);
                node = grand;
                continue;
            }
            // 叔父节点不是红色
            // 判断 LL RR LR RL情况
            if (grand.left == parent) {  // L
                if (parent.left == node) {    //  LL
                    black(parent);
                    red(grand);
                    rorateRight(grand);
                } else {     // LR
                    black(node);
                    red(grand);
                    rorateLeft(parent);
                    rorateRight(grand);
                }
            } else {  // R
                if (parent.right == node) {  // RR
                    black(parent);
                    red(grand);
                    rorateLeft(grand);
                } else {        // RL
                    black(node);
                    red(grand);
                    rorateRight(parent);
                    rorateLeft(grand);
                }
            }
            // 只要执行到这里,证明红黑树已经修复完成
            return;
        }

        if (node.parent == null) {  // 根节点必须为黑色
            black(node);
        }

    }


    public V get(K key, V value) {
        return null;
    }

    public boolean remove(K key) {
        return remove(node(key));
    }

    private boolean remove(Node<K, V> node) {
        if (node == null) return false;

        // 度为2的节点
        if (node.hasTwoChildren()) {
            Node<K, V> successor = successor(node);
            // 使用后继节点的key value取代它的值
            node.key = successor.key;
            node.value = successor.value;
            node = successor;
        }

        // node必然是度为1或者为0的节点

        Node<K, V> replacement = node.left != null ? node.left : node.right;
        Node<K, V> parent = node.parent;

        // 度为1的节点
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
        } else { // 度为0的节点
            if (parent == null) {
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

    private void fixAfterDeletion(Node<K, V> node) {

        while (node != null) {

            // 删除的是红色节点,或者是替代节点为红色
            if (isRed(node)) {
                black(node);
                return;
            }

            Node<K, V> parent = node.parent;
            // 根节点
            if (parent == null) return;

            boolean right = parent.right == null || node.isRightChildren();

            Node<K, V> sibling = right ? parent.left : parent.right;

            // 删除的是右边
            if (right) {
                if (isRed(sibling)) { // 兄弟节点是红色
                    black(sibling);
                    red(parent);
                    rorateRight(parent);
                    sibling = parent.left;
                }

                // 兄弟节点必然是黑色
                if (isBlack(sibling.left) && isBlack(sibling.right)) {
                    // 记录父节点的颜色是否是黑色
                    boolean parentBlack = isBlack(parent);
                    // 兄弟节点的子节点全是黑色, 没有红色子节点
                    red(sibling);
                    black(parent);
                    if (parentBlack) {
                        // 父节点原来是黑色,那么它会产生下溢,需要重新处理
                        node = parent;
                        continue;
                    }
                } else {
                    // 兄弟节点存在红色子节点
                    if (isBlack(sibling.left)) {
                        // 兄弟节点的左子节点是黑色,那么兄弟节点的右子节点是红色节点
                        rorateLeft(sibling);
                        sibling = parent.left;
                    }

                    // 兄弟节点的左子节点必然是红色
                    color(sibling,colorOf(parent));
                    rorateRight(parent);
                    black(sibling.left);
                    black(sibling.right);
                }

                break;
            }else { // symmetric

                if (isRed(sibling)) { // 兄弟节点是红色
                    black(sibling);
                    red(parent);
                    rorateLeft(parent);
                    sibling = parent.right;
                }

                // 兄弟节点必然是黑色
                if (isBlack(sibling.left) && isBlack(sibling.right)) {
                    // 记录父节点的颜色是否是黑色
                    boolean parentBlack = isBlack(parent);
                    // 兄弟节点的子节点全是黑色, 没有红色子节点
                    red(sibling);
                    black(parent);
                    if (parentBlack) {
                        // 父节点原来是黑色,那么它会产生下溢,需要重新处理
                        node = parent;
                        continue;
                    }
                } else {
                    // 兄弟节点存在红色子节点
                    if (isBlack(sibling.right)) {
                        // 兄弟节点的左子节点是黑色,那么兄弟节点的右子节点是红色节点
                        rorateRight(sibling);
                        sibling = parent.right;
                    }

                    // 兄弟节点的左子节点必然是红色
                    color(sibling,colorOf(parent));
                    rorateLeft(parent);
                    black(sibling.left);
                    black(sibling.right);
                }

                break;
            }

        }
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(K key) {
        return false;
    }

    public boolean containsValue(V value) {
        return false;
    }

    public void clear() {
        root = null;
        size = 0;
    }


    private static class Node<K, V> {
        boolean color = RED;
        K key;
        V value;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        // 判读自己是父节点的左子节点
        public boolean isLeftChildren() {
            return parent != null && this == parent.left;
        }

        // 判断自己是父节点的右子节点
        public boolean isRightChildren() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChildren()) return parent.right;
            if (isRightChildren()) return parent.left;

            return null;
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) str = "R_";

            return str + key + ":" + value;
        }
    }

    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点
        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        while (node.parent != null && node.parent.right != node) {
            node = node.parent;
        }
        return node.parent;
    }


    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 后继节点
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        while (node.parent != null && node.parent.left != node) {
            node = node.parent;
        }
        return node.parent;

    }

    private Node<K, V> node(K key) {
        keyNotNull(key);

        Node<K, V> node = root;

        while (node != null) {
            int c = compare(key, node.key);
            if (c == 0) return node;

            if (c > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    /**
     * 右旋转
     *
     * @param rorateNode 旋转节点
     */
    private void rorateRight(Node<K, V> rorateNode) {
        Node<K, V> node = rorateNode.left;

        rorateNode.left = node.right;
        if (node.right != null) node.right.parent = rorateNode;
        node.right = rorateNode;

        node.parent = rorateNode.parent;
        if (rorateNode.parent == null) {
            root = node;
        } else if (rorateNode.parent.left == rorateNode) {
            rorateNode.parent.left = node;
        } else { // rorateNode.parent.right == rorateNode
            rorateNode.parent.right = node;
        }
        rorateNode.parent = node;
    }

    // 左旋转
    private void rorateLeft(Node<K, V> rorateNode) {
        Node<K, V> node = rorateNode.right;

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


    private int compare(K k1, K k2) {
        if (comparator != null)
            return comparator.compare(k1, k2);

        return ((Comparable<K>) k1).compareTo(k2);
    }

    private void black(Node<K, V> node) {
        color(node, BLACK);
    }

    private void red(Node<K, V> node) {
        color(node, RED);
    }

    private void color(Node<K, V> node, boolean color) {
        if (node == null) return;
        node.color = color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }


    private void keyNotNull(K key) {
        if (key == null)
            throw new IllegalArgumentException("key is must be not null");
    }

    public Object root() {
        return root;
    }

    public Object left(Object node) {
        return ((Node<K, V>) node).left;
    }

    public Object right(Object node) {
        return ((Node<K, V>) node).right;
    }

    public Object string(Object node) {
        return node;
    }

}
