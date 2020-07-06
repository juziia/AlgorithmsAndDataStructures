package 链表;

import java.util.AbstractSet;
import java.util.HashSet;

public class _141_环形链表 {

    public static boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode slowNode = head.next;
        ListNode fastNode = head.next.next;
        while(fastNode != null && fastNode.next != null){
            if(fastNode == slowNode){
                return true;
            }
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;

        }
        return false;

    }

    public ListNode removeDuplicateNodes(ListNode head) {
        HashSet set = new HashSet();
        ListNode node = head;
        while(head != null){
            set.add(head);
            head = head.next;
        }

        for(int i =0;i<set.size();i++){ }
        return node;

    }


    public static void main(String[] args) {
        //[3,2,0,-4]
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(0);
        ListNode node4 = new ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;

        System.out.println(hasCycle(node1));


    }
}
