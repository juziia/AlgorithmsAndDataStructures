package 链表;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ListNode node = this;
        while(node != null){
            sb.append(node.val).append(",");
            node = node.next;
        }
        return sb.substring(0,sb.length()-1)+"]";
    }
}
