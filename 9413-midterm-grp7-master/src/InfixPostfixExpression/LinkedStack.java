package InfixPostfixExpression;

import java.util.NoSuchElementException;

public class LinkedStack<T> implements Stack<T> {
    private Node<T> top;
    private int numElements = 0;

    public LinkedStack(){
        top = null;
        numElements = 0;
    }

    public int size(){
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return (top==null);
    }

    public T top() throws StackException {
        if (isEmpty())
            throw new StackException("Stack is empty.");
        return top.getData();
    }

    public T pop() throws StackException {
        Node<T> temp;
        if (isEmpty())
            throw new StackException("Stack underflow.");
        temp = top;
        top = top.getNext();
        numElements --;
        return temp.getData();
    }

    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.setNext(top);
        top = newNode;
        numElements += 1;
    }

    public void clear(){
        while(top!=null){
            top = top.getNext();
        }
    }

    public int search(T item) throws StackException, NoSuchElementException{
        if (top == null)
            throw new StackException("Empty Stack");
        Node<T> temp = top;
        int index = numElements-1;
        boolean found = false;

        while(index >= 0){
            if(item == temp.getData()){
                found = true;
                break;
            }else{
                temp = temp.getNext();
                index--;
            }
        }

        if (found){
            return index;
        }else{
            throw new NoSuchElementException("No such element in stack");
        }
    }

    public String toCSV(){//to comma separated values
        if (top == null){
            return "";
        }

        StringBuilder result = new StringBuilder();
        Node<T> current = top;

        if (numElements == 1) {
            return result.toString() + top.getData();
        }

        for(int i = 0; i < numElements; i++){
            if(current.getNext() == null) {
                result.insert(0, current.getData());
                break;
            } else {
                result.insert(0, "," + current.getData());
            }
            current = current.getNext();
        }
        return result.toString();
    }

    public String toString(){
        if (top == null){
            return "";
        }

        StringBuilder result = new StringBuilder();
        Node<T> current = top;

        for(int i = 0; i < numElements; i++){
            if(current.getNext() == null){
                result.insert(0, current.getData());
                break;
            } else {
                result.insert(0, current.getData());
            }
            current = current.getNext();
        }

        return result.toString();
    }

    public boolean sameTopAndNext(){ //checks if the top Node has the same element as its next
        if(numElements <= 1){
            return false;
        }
        return top.getData() == top.getNext().getData();
    }
}
