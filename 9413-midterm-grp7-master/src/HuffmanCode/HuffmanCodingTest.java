package HuffmanCode;

import java.util.*;

public class HuffmanCodingTest {
    public static void main (String[] args){
        HuffmanCodingTest test;
        try{
            test = new HuffmanCodingTest();
            test.run();
        } catch (Exception E){
            E.printStackTrace();
        }
        System.exit(0);
    }

    public void run(){
        Scanner scan = new Scanner(System.in);

        showMenu();
        String stringValue = scan.nextLine();

        String[] charContainer = characterSplit(stringValue);

        System.out.println("\n" + Arrays.toString(charContainer));

        //showTableOfFrequency(stringValue)ff;
        ascendingFrequencyTable(stringValue);
        test();
    }//end of run

    public void showMenu(){
        System.out.print("Input Text/Sentence/Paragraph: ");

    }//end of showMenu

    public void showTableOfFrequency(String stringInput){
        stringInput = stringInput.replace(" ", "");
        Map <Character,Integer> freqCount = new LinkedHashMap<>();

        for(char character: stringInput.toCharArray()){
            if (freqCount.containsKey(character))
                freqCount.put(character, freqCount.get(character) + 1);
            else
                freqCount.put(character, 1);
            //end of if-else condition
        }//end of enhanced for-loop

        System.out.printf("\n%-10s %20s", "Character", "Frequency in Text");
        for(char character: freqCount.keySet()){
            System.out.printf("\n%5s %15s", character, freqCount.get(character));
        }//end of enhanced for-loop
    }//end of showTableOfFrequency

    public void ascendingFrequencyTable(String input){
        input = input.toUpperCase();
        int length = input.length();
        int freq;

        System.out.println("Frequency Table for: " + input);
        for(char ch = 'A'; ch <= 'Z'; ch++){
            freq = 0;
            for(int c = 0; c < length; c++){
                if(ch == input.charAt(c)) freq++;
            }
            if(freq > 0) System.out.println(ch + ": " + freq);
        }
    }
    public static String[] characterSplit(String str) {
        List<String> list = new ArrayList<>();
        StringBuilder currentDigits = new StringBuilder();

        for (char ch: str.toCharArray()) {
            if (Character.isDigit(ch)) {
                currentDigits.append(ch);
            } else {
                if (currentDigits.length() > 0) {
                    list.add(currentDigits.toString());
                    currentDigits = new StringBuilder();
                }
                list.add(String.valueOf(ch));
            }
        }

        if (currentDigits.length() > 0)
            list.add(currentDigits.toString());

        return list.toArray(new String[list.size()]);
    }

    // use this to test the queue
    public void test(){
        LinkedList something = new LinkedList();
        something.enqueue(new BinaryTreeNode<Integer>(6));
        something.enqueue(new BinaryTreeNode<Integer>(1));
        something.enqueue(new BinaryTreeNode<Integer>(3));
        something.enqueue(new BinaryTreeNode<Integer>(2));
        something.enqueue(new BinaryTreeNode<Integer>(50));
        something.enqueue(new BinaryTreeNode<Integer>(4));
        System.out.println(something.toString());
        something.sortAscending();
        something.insertAndSortAscending(new BinaryTreeNode<Integer>(45));
        System.out.println(something.toString());
    }
}
