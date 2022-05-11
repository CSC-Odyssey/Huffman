package HuffmanCode;

public interface Queue <T> {
    public int size();
    public boolean isEmpty();
    public void enqueue(T item);
    public T dequeue();
    public T peek();
    public void clear();
}

class QueueException extends RuntimeException{
    public QueueException (String err){
        super(err);
    }
}