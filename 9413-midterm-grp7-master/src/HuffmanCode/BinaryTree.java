package HuffmanCode;

import java.util.HashMap;

public class BinaryTree <T> {
    private BinaryTreeNode<T> root;

    public BinaryTree(){
        root = null;
    }

    public void setRoot(BinaryTreeNode<T> root){
        this.root = root;
    }
    public BinaryTreeNode<T> getRoot(){
        return root;
    }

    public void generateHuffmanToFile(String huffmanCode, BinaryTreeNode<Integer> root, HashMap<Character, String> huffmanMap) {
        if(root.getLeft() == null && root.getRight() == null){
            huffmanMap.put(root.getName(), huffmanCode);
            return;
        }

        generateHuffmanToFile(huffmanCode + "0", root.getLeft(), huffmanMap);
        generateHuffmanToFile(huffmanCode + "1", root.getRight(), huffmanMap);
    }
}