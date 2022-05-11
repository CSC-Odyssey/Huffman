package HuffmanCode;

public class Node <F> {
    private F data;
    private Node <F> next;

    public Node(){
        data = null;
        next = null;
    }

    public Node(F data){
        this.data= data;
        this.next = null;
    }

    public F getData(){
        return data;
    }

    public Node<F> getNext(){
        return next;
    }

    public void setData(F data){
        this.data = data;
    }

    public void setNext(Node<F> next){
        this.next = next;
    }
}