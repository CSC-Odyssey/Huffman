package InfixPostfixExpression;

public interface Stack <T> {
    public int size();
    public boolean isEmpty();
    public T top() throws StackException;
    public T pop() throws StackException;
    public void push(T item) throws StackException;
    public void clear();
    public int search(T item);

}

class StackException extends RuntimeException{
    public StackException (String err){
        super(err);
    }
}
