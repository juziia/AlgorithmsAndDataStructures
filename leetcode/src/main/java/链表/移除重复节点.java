package 链表;

import java.util.*;

public class 移除重复节点 {

    public static ListNode removeDuplicateNodes(ListNode head) {
       HashSet set = new HashSet();
       ListNode cur = head;
       while(cur != null && cur.next != null){
           set.add(cur.val);
           if(set.contains(cur.next.val)){
               cur.next = cur.next.next;
           }else{
               cur = cur.next;
           }
       }

        return head;
    }

    public static void main(String[] args) {
        // [1, 2, 3, 3, 2, 1]
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(3);
        ListNode node5 = new ListNode(2);
        ListNode node6 = new ListNode(1);
        ListNode node7 = new ListNode(8);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        System.out.println(node1);
        System.out.println(removeDuplicateNodes(node1));

    }
}
