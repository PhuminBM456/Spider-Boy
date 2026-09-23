class Node{
    // field
    String power;
    Node next;

    // constructor
    Node(String power){
        this.power = power;
        next = null;
    }
}

class LinkedList{
    // field
    Node head;

    // constructor
    LinkedList(){
        head = null;
    }

    // method
    Node findLastNode(Node temp){
        // basecase
        if(temp.next == null)
            return temp;

        // recursion
        return findLastNode(temp.next);
    }

    void Insert(String power){
        Node newNode = new Node(power);
        Node lastNode = null;

        if(head == null){
            head = new Node(power);
            return;
        }

        lastNode = findLastNode(head);
        lastNode.next = newNode;
    }

    int countNode(){
        int sum = 0;

        for(Node temp = head;temp != null;temp = temp.next){
            ++sum;
        }

        return sum;
    }

    void Display(){
        for(Node temp = head;temp != null;temp = temp.next){
            System.out.print(temp.power + " ");
        }
    }

    public static void main(String[] args){
        LinkedList LL = new LinkedList();

        LL.Insert("Impact");
        LL.Insert("Impact");

        LL.Display();
    }
}