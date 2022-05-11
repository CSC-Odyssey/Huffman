package HuffmanCode;

public class LinkedList implements Queue<BinaryTreeNode<Integer>> {
    private Node<BinaryTreeNode<Integer>> head;
    private Node<BinaryTreeNode<Integer>> rear;
    private int numElements = 0;

    public LinkedList() {
        head = null;
        rear = null;
    }


    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return (head == null && rear == null);
    }

    @Override
    public void enqueue(BinaryTreeNode<Integer> item) {
        Node<BinaryTreeNode<Integer>> newNode = new Node<>(item);

        if(isEmpty()) { //the list has no element contained
            head = newNode;
            rear = newNode;
            numElements++;

        } else if(numElements == 1) { //the list contains 1 element
            head.setNext(newNode);
            rear = newNode;
            numElements++;

        } else {
            rear.setNext(newNode);
            rear = newNode;
            numElements++;
        }


            /*{ //the list contains more than 1 elements
            while(head != rear){
                if (item.getData() > rear.getData().getData()){
                    //Node2<BinaryTreeNode<Integer>> newNode = new Node2<>(item);
                    rear.setNext(newNode);
                    rear = newNode;
                    numElements++;
                    break;
                } else if(item.getData() < head.getData().getData() && head == head){
                    //Node2<BinaryTreeNode<Integer>> newNode = new Node2<>(item);
                    newNode.setNext(head);
                    head = newNode;
                    numElements++;
                    break;
                } else if (item.getData() >= head.getData().getData() && item.getData() < head.getNext().getData().getData()){
                    //Node2<BinaryTreeNode<Integer>> newNode = new Node2<>(item);
                    newNode.setNext(head.getNext());
                    head.setNext(newNode);
                    numElements++;
                    break;
                }
                head = head.getNext();
            }//end of while loop
        }*///end of if-else statement*/
    }//end of enqueue

    @Override
    public BinaryTreeNode<Integer> dequeue() {
        if(isEmpty()){
            throw new QueueException("Empty Queue.");
        }
        Node<BinaryTreeNode<Integer>> outNode = head;
        head = head.getNext();
        numElements --;
        return outNode.getData();
    }

    @Override
    public BinaryTreeNode<Integer> peek() {
        return head.getData();
    }

    @Override
    public void clear() {
        while(!isEmpty()){
            head = head.getNext();
        }
    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        Node<BinaryTreeNode<Integer>> newNode = head;

        while (newNode != rear){
            string.append(" ").append(newNode.getData());
            newNode = newNode.getNext();
        }
        string.append(" " + rear.getData());
        return string.toString();
    }

    // Inserts new element on the queue and sorts the it in ascending order.
    // Returns new sorted queue.
    // Sort the queue first before using this method.
    /*public void insetAndSortAscending(BinaryTreeNode<Integer> newItem){
        boolean inserted = false;
        for (int i = 0; i < this.size(); i++){
            BinaryTreeNode<Integer> current = this.dequeue();
            if (current.getData() > newItem.getData() && !inserted){
                this.enqueue(newItem);
                inserted = true;
            }
            this.enqueue(current);
        }
        enqueue(newItem);
    }*/
    public void insertAndSortAscending(BinaryTreeNode<Integer> newItem) {
        this.enqueue(newItem);
        this.sortAscending();
    }

    // Sorts the queue in ascending order
    public void sortAscending(){
        int numOfFirstLoop;
        final int size = numElements;
        if(size == 1){
            return;
        } else {
            numOfFirstLoop = size-1;
        }
        for(int i = 0; i < numOfFirstLoop; i++){
            BinaryTreeNode<Integer> first = dequeue();
            for (int j = 1; j < size; j++){
                BinaryTreeNode<Integer> second = dequeue();

                if(first.getData() > second.getData()){
                    enqueue(second);
                } else {
                    enqueue(first);
                    first = second;
                }
            }
            enqueue(first);
        }
    }
}