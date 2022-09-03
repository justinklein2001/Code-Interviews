package interviewsPackage;

public class LinkedListNode {

    private static Node head;
    public Node getHead() {
        return this.head;
    }

    public LinkedListNode(){
    }
    public void insert(int data){
        Node newNode = new Node(data);
        if(this.head == null){
            head = newNode;
        }else {
            Node currentNode = head;
            while(currentNode.getNextNode() != null){
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(newNode);
        }
    }

    public static void display(){
        if(head != null){
            Node currentNode = head;
            while(currentNode.getNextNode() != null){
                System.out.println(currentNode.getData());
                currentNode = currentNode.getNextNode();
            }
            System.out.println(currentNode.getData());
        }
    }


}
