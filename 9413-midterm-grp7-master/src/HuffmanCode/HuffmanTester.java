package HuffmanCode;

import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.stream.Collectors;


class MyComparator implements Comparator<BinaryTreeNode<Integer>> {
    public int compare (BinaryTreeNode<Integer> node1, BinaryTreeNode<Integer> node2){
        return node1.getData() - node2.getData();
    }
}

class Main {
    public static void main(String[] args) {
        try {
            menu();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void menu() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("==========================================");

        System.out.println("|| 1. String to Huffman");
        System.out.println("|| 2. Huffman to String");
        System.out.println("|| 3. Exit");

        System.out.print("What do you want to do? : ");
        int choice = in.next().charAt(0);

        while(true) {
            try {
                switch (choice) {
                    case '1'-> {
                        System.out.println("==========================================");
                        initiateStringToHuffman();
                        System.out.println();
                        menu();
                    }
                    case '2' -> {
                        System.out.println("==========================================");
                        initiateHuffmanToString();
                        menu();
                    }
                    case '3' -> {
                        System.out.println("==========================================");
                        System.out.println("Thank you for using the program, Goodbye!");
                        System.out.println("==========================================\n");
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("==========================================");
                        System.out.println("Invalid Choice");
                        menu();
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void initiateStringToHuffman() throws IOException {
        HashMap<Character, Integer> hashMap = new HashMap<>();

        //reading input
        File inputFile = new File("9413-midterm-grp7-master/files/in.txt");

        if (inputFile.createNewFile()) {
            System.out.println("Note: File created: " + inputFile.getName());
        } else {
            System.out.println("Note: File " + inputFile.getName() + " already exists");
        }

        File huffmanCodeFile = new File("9413-midterm-grp7-master/files/HuffmanCode.txt");

        if (huffmanCodeFile.createNewFile()) {
            System.out.println("Note: File created: " + huffmanCodeFile.getName());
        } else {
            System.out.println("Note: File " + huffmanCodeFile.getName() + " already exists");
        }
        System.out.println("==========================================");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile));
        Scanner scan = new Scanner(inputFile);

        System.out.println("Note: Write '!stop' to stop writing");
        System.out.println("Start of String/Paragraph Input: ");

        int numberOfLines = 0;
        do {
            String line = in.readLine();
            if (line.equals("!stop")) {
                break;
            }
            bw.newLine();
            bw.write(line);
            numberOfLines++;
        } while (true);
        bw.close();

        //putting input into a HashMap
        scan.nextLine();
        insertToHashmap(numberOfLines, scan, hashMap);

        String input = null;

        //putting it in a PriorityQueue and create binary tree
        PriorityQueue<BinaryTreeNode<Integer>> queue = new PriorityQueue<>(hashMap.size(), new MyComparator());
        BinaryTree<Integer> tree = new BinaryTree<>();
        createBinaryTree(hashMap, queue, tree);


        //create huffman
        HashMap<Character, String> huffmanMap = new HashMap<>();
        bw = new BufferedWriter(new FileWriter(huffmanCodeFile));
        createAndPrintHuffmanTable(tree, huffmanMap, bw);

        //convert input to huffman and print
        BufferedReader readInput = new BufferedReader(new FileReader(inputFile));
        BufferedReader reader = new BufferedReader(new FileReader(huffmanCodeFile));
        BufferedReader reader1 = new BufferedReader(new FileReader(huffmanCodeFile));
        LinkedList<String> inputLines = new LinkedList<>();
        HashMap<String, String> newMap = new HashMap<>();

        convertInput(readInput, reader, reader1, inputLines, newMap);

        equivalentHuffman(inputLines, newMap);
    }

    static void initiateHuffmanToString() {
        Scanner scan = new Scanner(System.in);
        HashMap<String,String> hashMap = new HashMap<>();
        int largestHuffmanDigit = 0;
        StringBuilder code = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader("9413-midterm-grp7-master/files/HuffmanCode.txt"));
            do {
                String line = br.readLine();
                if(line == null) break;
                String[] lineParts = line.split("-");
                hashMap.put(lineParts[1], lineParts[0]);
                if(lineParts[1].length() > largestHuffmanDigit){
                    largestHuffmanDigit = lineParts[1].length();
                }
            } while(true);
        } catch (IOException c){
            System.out.println("There is an error!");
        }

        try {
            huffmantoString(largestHuffmanDigit, hashMap, scan, code);
        } catch (Exception x){
            System.out.println("Cannot be converted!");
        }
    }

    static void huffmantoString(int largestHuffmanDigit, HashMap<String,String> hashMap, Scanner scan, StringBuilder code){
        boolean correctInput = true;
        StringBuilder current = new StringBuilder();
        Stack<String> stack = new Stack<>();
        System.out.print("Input Huffman Code: ");
        String input = scan.nextLine();
        char[] digits = input.toCharArray();
        for (int c = digits.length - 1; c >= 0; c--) {
            stack.push(Character.toString(digits[c]));
        }
        while (!stack.empty()) {
            current.append(stack.pop());
            if (current.length() == largestHuffmanDigit || stack.empty()) { //if equal to largest number of digits
                while (!current.isEmpty()) {
                    char[] currentArray = current.toString().toCharArray();
                    if (hashMap.containsKey(current.toString())) {
                        code.append(hashMap.get(current.toString()));
                        do {
                            current.deleteCharAt(current.length() - 1);
                        } while (!current.isEmpty());
                        break;
                    } else if(stack.empty()){
                        correctInput = false;
                        break;
                    } else {
                        stack.push(Character.toString(currentArray[currentArray.length - 1]));
                        current.deleteCharAt(current.length() - 1);
                    }
                }
            }
        }
        if(correctInput) {
            System.out.println("Equivalent String: " + code);
        } else {
            System.out.println("Invalid Input: Cannot be converted based on the current available Huffman Codes!");
        }
    }

    /**
     * Method that will output the equivalent huffman code of the inputted string.
     * @param inputLines
     * @param newMap
     */
    static void equivalentHuffman(LinkedList<String> inputLines, HashMap<String, String> newMap) {
        System.out.print("Equivalent Huffman Code: ");
        for (String s : inputLines) {
            char[] chars = s.toCharArray();
            for (char c : chars) {
                c = Character.toUpperCase(c);
                String cs = Character.toString(c);
                System.out.print(newMap.get(cs));
            }
        }
    }

    /**
     * Method that will convert the input from the separators to the hashmap.
     * @param readInput
     * @param reader
     * @param reader1
     * @param inputLines
     * @param newMap
     * @throws IOException
     */
    static void convertInput(BufferedReader readInput, BufferedReader reader, BufferedReader reader1, LinkedList<String> inputLines, HashMap<String, String> newMap) throws IOException {
        readInput.readLine();
        String current = readInput.readLine();
        do {
            inputLines.add(current);
            current = readInput.readLine();
        } while (current != null);
        readInput.close();

        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();

        for (int i = 0; i < lines; i++) {
            String[] huffman = reader1.readLine().split("-");
            newMap.put(huffman[0], huffman[1]);
        }
    }

    /**
     * Method in getting the frequency of each character
     * @return frequency array
     * @throws IOException
     */
    static int[] frequency() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("9413-midterm-grp7-master/files/in.txt"));
        String input = ReadBigStringIn(br);

        char[] charSp = input.toCharArray();
        Arrays.sort(charSp);

        int[] freq = new int[input.length()];

        for (int i = 0; i < input.length(); i++) {
            freq[i] = 1;
            for (int j = i + 1; j < input.length(); j++) {
                if (charSp[i] == charSp[j]) {
                    freq[i]++;
                    //Set string[j] to 0 to avoid printing visited character
                    charSp[j] = '0';
                }
            }
        }

        int[] output = new int[freq.length];

        for (int i = 0; i < freq.length; i++) {
            if (charSp[i] != '0') {
                output[i] = freq[i];
            }
        }

        int targetIndex = 0;
        for(int sourceIndex = 0; sourceIndex < output.length; sourceIndex++){
            if(output[sourceIndex] != 0) {
                output[targetIndex++] = output[sourceIndex];
                output[sourceIndex] = freq[sourceIndex];
            }
        }

        return output;
    }

    /**
     * Method in compiling all texts and paragraphs from the file to a single string
     * @param buffIn BufferedReader
     * @return one string line
     * @throws IOException
     */
    public static String ReadBigStringIn(BufferedReader buffIn) throws IOException {
        StringBuilder everything = new StringBuilder();
        String line;
        while ((line = buffIn.readLine()) != null) {
            everything.append(line);
        }
        return everything.toString();
    }

    /**
     * Method that will create and print huffman table
     * @param tree will be the huffman tree
     * @param huffmanMap will be the container containing the huffman keys and values
     * @param bw will be the buffered reader
     * @throws IOException
     */
    static void createAndPrintHuffmanTable(BinaryTree<Integer> tree, HashMap<Character, String> huffmanMap, BufferedWriter bw) throws IOException {
        tree.generateHuffmanToFile("", tree.getRoot(), huffmanMap);
        Map<Character, String> huffmanTreeMap = new TreeMap<>(huffmanMap);
        Map<String, Integer> map = new HashMap<String, Integer>();
        String s = null;

        int counter = 0;
        double totalNumBits = 0;
        double totalFeq = 0;
        double totalFreqBits = 0;

        Iterator iteratorSorted = huffmanTreeMap.entrySet().iterator();

        System.out.println("==========================================");
        System.out.printf("%3s%12s%17s", "Char", "HFCode", "Frequency");
        System.out.println("\n==========================================");

        int[] getValue = getPerValueLength(huffmanMap);
        int[] freq = frequency().clone();

        for (int j : getValue) {
            totalNumBits += j;
            totalFeq += freq[j];
            totalFreqBits += freq[j] * j;
        }

        while (iteratorSorted.hasNext()) {
            Map.Entry pair = (Map.Entry) iteratorSorted.next();
            System.out.printf("%2s%14s%13s\n", pair.getKey(), pair.getValue(), freq[counter]);
            bw.write(pair.getKey() + "-" + pair.getValue());
            bw.newLine();

            counter++;
        }

        System.out.println("==========================================");

        double ascii = totalFeq * 8;
        double finalSavings = ((ascii - totalNumBits) / ascii) * 100;

        System.out.println("Total Number of Bits: " + totalNumBits);
        System.out.println("Total Number of Bits in ASCII: " + ascii);
        System.out.println("Storage Savings Percentage: " + (double) Math.round(finalSavings * 100) / 100 + "%");
        System.out.println("==========================================");
        bw.close();
    }

    /**
     * Method to create a Binary Tree
     * @param hashMap will be the hashmap containing the keys and values
     * @param queue will be the queue
     * @param tree will be the tree
     */
    static void createBinaryTree(HashMap<Character, Integer> hashMap, PriorityQueue<BinaryTreeNode<Integer>> queue, BinaryTree<Integer> tree) {
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            BinaryTreeNode<Integer> nodeEntry = new BinaryTreeNode<>(Character.toUpperCase((char) pair.getKey()), (int) pair.getValue());
            queue.add(nodeEntry);
        }

        if (queue.size() <= 1 && (!Character.isDigit(queue.peek().getName()) || !Character.isLetter(Objects.requireNonNull(queue.peek()).getName()))) {
            new BinaryTree<Integer>().setRoot(queue.poll());
        } else {
            while (queue.size() != 1) {
                try {
                    BinaryTreeNode<Integer> node1 = queue.poll();
                    BinaryTreeNode<Integer> node2 = queue.poll();
                    BinaryTreeNode<Integer> node3 = new BinaryTreeNode<>((node1.getData() + node2.getData()));
                    node3.setLeft(node1);
                    node3.setRight(node2);
                    queue.add(node3);
                } catch (Exception x) {
                    break;
                }
            }
            tree.setRoot(queue.poll());
        }
    }

    /**
     * Method to insert scanned texts to hashmap
     * @param numberOfLines will be the number of lines
     * @param scan will be the scanned text
     * @param hashMap will be the hashmap container
     */
    static void insertToHashmap(int numberOfLines, Scanner scan, HashMap<Character, Integer> hashMap) {
        for (int i = 0; i < numberOfLines; i++) {
            String currentLine = scan.nextLine();
            char[] currentLineElements = currentLine.toCharArray();
            for (char c : currentLineElements) {
                if (!hashMap.containsKey(c)) {
                    hashMap.put(c, 1);
                } else {
                    hashMap.replace(c, hashMap.get(c) + 1);
                }
            }
        }
    }

    /**
     * Array int that will contain the length of each alphabet huffman code for the bit.
     * @param huffmanMap will be the treemap that contains the huffman code of each alphabet in the inputted string.
     * @return the array integer containing the length of each alphabet huffman code.
     */
    static int[] getPerValueLength(HashMap<Character, String> huffmanMap){
        Map<Character, String> huffmanTreeMap = new TreeMap<>(huffmanMap);
        int[] getValue = new int[huffmanTreeMap.size()];
        String[] getV = new String[huffmanTreeMap.size()];
        int counter = 0;

        Iterator iteratorSorted = huffmanTreeMap.entrySet().iterator();
        while (iteratorSorted.hasNext()) {
            Map.Entry pair = (Map.Entry) iteratorSorted.next();
            getV[counter] = (String) pair.getValue();
            counter++;
        }
        counter = 0;
        for(int i = 0; i < getV.length; i++){
            getValue[i] = getV[i].length();
        }

        return getValue;
    }
}