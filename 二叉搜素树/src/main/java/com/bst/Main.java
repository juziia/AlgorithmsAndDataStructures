package com.bst;


import com.printer.BinaryTrees;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void test01(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree();
        AVLTree<Integer> avlTree = new AVLTree<>();

        for (int i = 0; i < 30; i++) {
            int num = (int) (Math.random() * 50);
//            System.out.println(num+ ",");
            binarySearchTree.add(num);
            System.out.println(num);
            avlTree.add(num);
            BinaryTrees.println(avlTree);
            System.out.println("==========================");
        }


        System.out.println("==============AVL树====================");
        BinaryTrees.println(avlTree);

        System.out.println("=============二叉搜素树=================");
        BinaryTrees.println(binarySearchTree);
//        System.out.println(binarySearchTree.height());

//        System.out.println("====================================");
//        binarySearchTree.preorderTraversal(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public void visitor(Integer element) {
//                System.out.print(element+",");
//            }
//        });
//        System.out.println("\n===================================");


    }


    static void test02(){
//        int[] arr = new int[]{
//                17,9,25,6,12,20,19,27,5,3,4,7,10,13,21,26
//        };

//        int[] arr = new int[]{
//          20,15,22,23,24,25,12
//        };
        int[] arr = new int[]{
                19,0,28,38,25,37,39,1,14,17,34,14,13,23,25,46,49,21,20,45,38,15,9,40
        };
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree();
        for (int i = 0; i < arr.length; i++) {
            binarySearchTree.add(arr[i]);

        }

        BinaryTrees.println(binarySearchTree);
        System.out.println("======================");

        AVLTree<Integer> avlTree = new AVLTree();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
            avlTree.add(arr[i]);

            BinaryTrees.println(avlTree);
        }

        BinaryTrees.println(avlTree);


//        System.out.println();
//
//        binarySearchTree.revertTree();
//        BinaryTrees.println(binarySearchTree);

//        binarySearchTree.levelOrderTraversal( i -> System.out.print(i + ","));

//        binarySearchTree.postorderTraversal( i -> System.out.print(i +","));
//
//        System.out.println("===============================================");
//        binarySearchTree.inorderTraversal2( i -> System.out.print(i +","));


//        binarySearchTree.inorderTraversal( i -> System.out.print(i +",") );
//        System.out.println();
//        binarySearchTree.inorderTraversal2( i -> System.out.print(i + ","));

    }


    static void test04(){

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int num = (int) (Math.random() * 30);
            list.add(num);
        }

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < list.size(); i++) {
            bst.add(list.get(i));
        }

        BinaryTrees.println(bst);

        AVLTree<Integer> avlTree = new AVLTree<>();
        for (int i = 0; i < list.size(); i++) {
            avlTree.add(list.get(i));
        }

        BinaryTrees.println(avlTree);

        for (int i = 0; i < list.size();i++){
            avlTree.remove(list.get(i));
            System.out.println(list.get(i));
            BinaryTrees.println(avlTree);
            System.out.println("===========================================");
        }
    }

    public static void main(String[] args) {
        test04();
    }
}
