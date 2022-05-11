package HuffmanCode;

public class BinaryTreeNode <T> {
    private char name;
    private T data;
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;

    public BinaryTreeNode(char name, T data){
        this.name = name;
        this.data = data;
        left = null;
        right = null;
    }

    public BinaryTreeNode(T data){
        this.data = data;
        left = null;
        right = null;
    }

    public void setLeft(BinaryTreeNode<T> left){
        this.left = left;
    }

    public void setRight(BinaryTreeNode<T> right){
        this.right = right;
    }

    public void setData(T data){
        this.data = data;
    }

    public BinaryTreeNode<T> getLeft(){
        return left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public char getName(){
        return name;
    }

    public T getData() {
        return data;
    }

    public String toString(){
        return data.toString();
    }

    public void insert(char character, T value) {
        if (value.toString().compareTo(data.toString()) < 0) {
            if (left == null)
                left = new BinaryTreeNode<>(character, value);
            else
                left.insert(character, value);
        }
        else if (value.toString().compareTo(data.toString()) > 0) {
            if (right == null)
                right = new BinaryTreeNode<>(character, value);
            else
                right.insert(character, value);
        }
    }
}
