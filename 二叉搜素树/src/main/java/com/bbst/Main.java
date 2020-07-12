package com.bbst;


import com.printer.BinaryTrees;
import com.printer.Times;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static void test01() {
        int[] arr = new int[]{
                79, 92, 5, 77, 62, 43, 97, 55, 34, 20, 26, 82, 70, 69, 71, 19, 11, 73, 23, 86};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        AVLTree<Integer> avlTree = new AVLTree<>();
        for (int i = 0; i < arr.length; i++) {
//            bst.add(arr[i]);
            System.out.println(arr[i]);
            avlTree.add(arr[i]);
            BinaryTrees.println(avlTree);
            System.out.println("============================");
        }

        BinaryTrees.println(bst);
        BinaryTrees.println(avlTree);
//
//        for (int i = 0; i < arr.length; i++) {
////            bst.add(arr[i]);
//            System.out.println(arr[i]);
//            BinaryTrees.println(avlTree);
//            avlTree.remove(arr[i]);
//            System.out.println("========================================");
//        }

    }

    static void test02() {
        int[] arr = {
                34, 45, 35, 21, 78, 41, 63, 51, 90, 92, 7, 66, 16, 80, 22, 26, 33, 44,
                88, 25, 69, 96, 18, 20, 99, 26, 76, 93, 61, 94, 17, 32, 43, 48, 65
        };

        RedBlackTree<Integer> rbTree = new RedBlackTree<>();
        for (int i = 0; i < arr.length; i++) {
            rbTree.add(arr[i]);
        }
        BinaryTrees.println(rbTree);

//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//            rbTree.remove(arr[i]);
//            BinaryTrees.println(rbTree);
//            System.out.println("======================================");
//        }

    }


    static void test03() {
        long startTime = 0l;
        List<Integer> list = new ArrayList<>();
        Boolean flag = false;
        synchronized (Main.class) {

            for (int i = 0; i < 100_000; i++) {
                list.add(i);
            }
        }

        System.out.println(list.size());
        new Thread(() -> {
            Times.test("二叉搜素树", () -> {
                BinarySearchTree<Integer> bst = new BinarySearchTree<>();
                for (int i = 0; i < 100_000; i++) {
                    bst.add(list.get(i));
                }

                for (int i = 0; i < 100_000; i++) {
                    bst.remove(list.get(i));
                }
            });
        }).start();


        new Thread(() -> {
            Times.test("AVL平衡二叉搜素树", () -> {
                AVLTree<Integer> avl = new AVLTree<>();

                for (int i = 0; i < 100_000; i++) {
                    avl.add(list.get(i));
                }

                for (int i = 0; i < 100_000; i++) {
                    avl.remove(list.get(i));
                }
            });
        }).start();


        new Thread(() -> {
            Times.test("红黑树平衡二叉搜素树", () -> {
                RedBlackTree<Integer> rbTree = new RedBlackTree<>();

                for (int i = 0; i < 100_000; i++) {
                    rbTree.add(list.get(i));
                }

                for (int i = 0; i < 100_000; i++) {
                    rbTree.remove(list.get(i));
                }
            });
        }).start();


    }




    static void test04() {
        List<Integer> list = new ArrayList<>();
//        Times.test("list", () -> {
//            for (int i = 0; i < 100_0000; i++) {
//                list.add(i);
//            }
//
//            for (int i = 0; i < list.size(); i++) {
//                list.remove(i);
//            }
//        });
//        list.clear();
        for (int i = 0; i < 1000_00000; i++) {
            int num = (int)(Math.random() * 100);
            list.add(num);
        }

        System.out.println(list.size());


        Times.test("AVL平衡二叉搜素树", () -> {
            AVLTree<Integer> avl = new AVLTree<>();

            for (int i = 0; i < 1000_00000; i++) {
                avl.add(list.get(i));
            }

            for (int i = 0; i < 1000_00000; i++) {
                avl.remove(list.get(i));
            }
        });


        Times.test("红黑树平衡二叉搜素树", () -> {
            RedBlackTree<Integer> rbTree = new RedBlackTree<>();

            for (int i = 0; i < 1000_00000; i++) {
                rbTree.add(list.get(i));
            }

            for (int i = 0; i < 1000_00000; i++) {
                rbTree.remove(list.get(i));
            }
        });

//        Times.test("二叉搜素树", () -> {
//            BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//            for (int i = 0; i < 100_0000; i++) {
//                bst.add(list.get(i));
//            }
//
//            for (int i = 0; i < 100_0000; i++) {
//                bst.remove(list.get(i));
//            }
//        });


    }


    public static void main(String[] args) {
//        test03();
        test02();
    }
}
