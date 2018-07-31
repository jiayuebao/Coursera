/*************************************************************************
 *  Author:  Jiayue Bao
 *  Date:    7/31/2018
 *
 *  Mergesort for a singly-linked list.
 *
 *************************************************************************/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class ListMergeSort {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // find the middle position
        ListNode slow = head;
        ListNode fast = head;
        while(fast.next!=null && fast.next.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        ListNode right = slow.next;
        slow.next = null; // an important step, to create the left list
        ListNode left = head;
        
        left  = sortList(left);
        right = sortList(right);
        return mergeList(left,right);
       
    }
    
    private ListNode mergeList(ListNode l1, ListNode l2) {
        ListNode merge = new ListNode(0);
        ListNode result = merge;
        
        while(l1!=null && l2!=null) {
            if(l1.val > l2.val) {
                merge.next = l2;
                l2 = l2.next;
                merge = merge.next;
            }    
            else {
                merge.next = l1;
                l1 = l1.next;
                merge = merge.next;
            }
        }
        
        if (l1 == null) merge.next = l2;
        else if(l2 == null) merge.next = l1;
        return result.next;
        
    }   
}