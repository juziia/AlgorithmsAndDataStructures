package com;



import com.map.TreeMap;
import com.printer.BinaryTrees;


public class Main {

    static void test01() {
        int[] arr = {
               88,65,102,57,72,95,110,40,61,75,90
        };

//        int[] arr = {
//                72, 47, 88, 91, 63, 6, 58, 9, 8, 39, 87, 70, 25, 97, 86, 75, 80, 26, 45, 98, 28
//        };
        TreeMap<Integer,String> map = new TreeMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i],"");
        }


        BinaryTrees.println(map);


        System.out.println("====================================");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
            map.remove(arr[i]);
            BinaryTrees.println(map);
            System.out.println("==============================================");
        }

    }


    static void test02() {
        int[] arr = {
                34, 45, 35, 21, 78, 41, 63, 51, 90, 92, 7, 66, 16, 80, 22, 26, 33, 44,
                88, 25, 69, 96, 18, 20, 99, 26, 76, 93, 61, 94, 17, 32, 43, 48, 65,
                61, 32, 65, 68, 33, 24, 19, 80, 26, 87, 47, 43, 85, 39
        };

//        int[] arr = {
//                72, 47, 88, 91, 63, 6, 58, 9, 8, 39, 87, 70, 25, 97, 86, 75, 80, 26, 45, 98, 28
//        };
        RedBlackTree<Integer> rbTree = new RedBlackTree<>();
        TreeMap<Integer,String> map = new TreeMap<>();
        for (int i = 0; i < arr.length; i++) {
            rbTree.add(arr[i]);
            map.put(arr[i],"");
        }

        System.out.println("=====================红黑树=======================");
        BinaryTrees.println(rbTree);

        System.out.println("=====================TreeMap=======================");
        BinaryTrees.println(map);


        System.out.println("====================================");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
            rbTree.remove(arr[i]);
            map.remove(arr[i]);
            System.out.println("*********************红黑树***********************");
            BinaryTrees.println(rbTree);
            System.out.println("**********************TreeMap***********************");
            BinaryTrees.println(map);
            System.out.println("==============================================");
        }

    }



    public static void main(String[] args) {
//        test03();
        test01();
    }
}
