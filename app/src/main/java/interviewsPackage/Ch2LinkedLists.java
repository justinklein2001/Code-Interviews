package interviewsPackage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

public class Ch2LinkedLists {
    /* Questions and Solutions Derived From:
     * Cracking the Coding Interview
     * 189 Programming Questions & Solutions
     * Written By: Gayle LaakMann McDowell
     * Program Written By: Justin Klein
     * Last Updated: September 1st, 2022
     */

    /* 2.1 Question
     * "Write code to remove duplicates from an unsorted linked list"
     */
    /*
     * 2.1 Solution 1 - Using buffer using own created Linked List
     * Goes through the LinkedList once, therefore it takes O(N) time, but takes up more space in memory
     */
    public static void deleteDupsBuffer (Node n) {
        HashSet<Integer> set = new HashSet<Integer>();
        Node previous = null;
        while (n != null){
            if (set.contains(n.getData())) {
                previous.setNextNode(n.getNextNode());
            } else {
                set.add(n.getData());
                previous =n;
            }
            n = n.getNextNode();
        }
    }

    /* 2.2 Question
     * "Return kth to last element of a singly linked list"
     */

    /*
     * 2.2
     *  Solution 1 - Using recursion, goes through the entire linked list, takes O(N) time complexity, but takes up
     *  much more memory space, needs to take up N number of function calls in memory, assumes passing in k=0 returns the last element
     */
    public static int kthToLast (Node head, int k){
        if (head == null){
            return -1; //since it's increments, the last one would return 0
        }

        int index = kthToLast(head.getNextNode(), k)+1; //effectively loops through the linked list, keeping track of the end

        if (index == k){
            System.out.println("2.2: "+k+"th to last element is: "+head.getData());
        }
        return index;
    }

    public static void linkedListRunner(){
        LinkedListNode linkedList = new LinkedListNode();
        linkedList.insert(4);
        linkedList.insert(5);
        linkedList.insert(1);
        linkedList.insert(12);
        linkedList.insert(678);
        linkedList.insert(1);
        linkedList.insert(4);
        Node head = linkedList.getHead();
        System.out.println("2.1: Removing duplicates from a Linked List without a buffer:");
        System.out.println("Linked List before dup removal: ");
        linkedList.display();
        deleteDupsBuffer(head);
        System.out.println("After dup removal:");
        linkedList.display();
        head = linkedList.getHead();
        kthToLast(head, 2);


    }

}
