package 链表;

import java.util.HashMap;
import java.util.Map;

public class 反转链表 {

    public static ListNode reverseList(ListNode head) {
        ListNode node = head;
        ListNode temp ;
        ListNode pre = head.next;
        ListNode next = head.next.next;
        while(next != null){
            temp = next;
            next = next.next;
            pre.next = next;
        }
        return head;
    }

    public static void main(String[] args) {
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

        System.out.println(reverseList(node1));
    }

}
