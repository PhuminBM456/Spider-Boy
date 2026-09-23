class Queue{
    final int MAX_SIZE = 100;
    Capsem[] arr = new Capsem[MAX_SIZE];
    int front,rear;
    boolean status;

    Queue(){
        front = rear = -1;
        status = false;
    }

    boolean isEmpty(){
        return front == -1;
    }

    boolean isFull(){
        return (rear + 1) % MAX_SIZE == front;
    }

    void Enqueue(Capsem cap){
        if(isFull())
            return;

        if(isEmpty())
            front = 0;

        rear = (rear + 1) % MAX_SIZE;

        arr[rear] = cap;
    }

    Capsem Dequeue(){
        if(isEmpty())
            return null;

        Capsem cap = arr[front];

        if(front == rear){
            front = rear = -1;
            return cap;
        }

        front = (front + 1) % MAX_SIZE;

        return cap;
    }

    void Display(){
        int i = front;

        do{
            System.out.print(arr[i].x + " ");

            i = (i+1) % MAX_SIZE;
        }while(i != rear+1);

        System.out.println();
    }
}