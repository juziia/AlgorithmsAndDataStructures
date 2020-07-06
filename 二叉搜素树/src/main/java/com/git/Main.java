package com.git;

import com.printer.BinaryTrees;

import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static void test01(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
        for (int i = 0; i < 20; i++) {
            int num = (int) (Math.random() * 30);
//            System.out.println(num+ ",");
            binarySearchTree.add(num);
        }

        BinaryTrees.println(binarySearchTree);

        System.out.println(binarySearchTree.height());

//        System.out.println("====================================");
//        binarySearchTree.preorderTraversal(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public void visitor(Integer element) {
//                System.out.print(element+",");
//            }
//        });
//        System.out.println("\n===================================");


        binarySearchTree.preorderTraversal2( element -> System.out.print(element+","));
    }

    static void test02(){
        BinarySearchTree<Person> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add(new Person(20,"jack"));
        binarySearchTree.add(new Person(21,"rose"));
        binarySearchTree.add(new Person(22,"mike"));
        binarySearchTree.add(new Person(25,"tom"));
        binarySearchTree.add(new Person(25,"tomcat"));

        BinaryTrees.println(binarySearchTree);
    }


    static void test03(){
        int[] arr = new int[]{
                17,9,25,6,10,20,19,26,5,7,11,21,28
        };

        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < arr.length; i++) {
            binarySearchTree.add(arr[i]);
        }

        BinaryTrees.println(binarySearchTree);

//        System.out.println();
//
//        binarySearchTree.revertTree();
//        BinaryTrees.println(binarySearchTree);

//        binarySearchTree.levelOrderTraversal( i -> System.out.print(i + ","));

//        binarySearchTree.postorderTraversal( i -> System.out.print(i +","));
//
//        System.out.println("===============================================");
        binarySearchTree.inorderTraversal2( i -> System.out.print(i +","));


//        binarySearchTree.inorderTraversal( i -> System.out.print(i +",") );
//        System.out.println();
//        binarySearchTree.inorderTraversal2( i -> System.out.print(i + ","));

    }


    static void test04(){
        int[] arr = new int[]{
                17,9,25,6,10,20,28,5,8
        };

        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < arr.length; i++) {
            binarySearchTree.add(arr[i]);
        }

        BinaryTrees.println(binarySearchTree);

        System.out.println(binarySearchTree.isComplete());

    }



    public static void main(String[] args) {
        test04();
    }
}