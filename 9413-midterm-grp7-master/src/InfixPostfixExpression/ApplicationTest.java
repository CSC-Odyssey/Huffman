 package InfixPostfixExpression;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

 //Infix to Postfix Expression (Activity 1)
 public class ApplicationTest {

     static Scanner scan = new Scanner(System.in);
     Scanner scan2 = new Scanner(System.in);

     public static void main(String[] args) {
         ApplicationTest program;

         try {
             program = new ApplicationTest();
             program.run();
         } catch (Exception e) {
             System.out.println("An error has occurred");
             e.printStackTrace();
         }
     }

     public void run() throws Exception { displayMenu(); }

     public void infixExpressionConverterTableOfValues() throws Exception {
         int i = 0;
         System.out.print("-= Input Infix Expression: ");
         String input = scan.nextLine();
         if(!checkIfValidInfix(input)){
             System.err.println("\nError: Invalid Input!");
             return;
         }
         String postfixExpr = convertInfixToPostfix(input);
         System.out.println("-=========================================================-");
         System.out.println("Converted Postfix Equivalent: " + postfixExpr);
         while (i == 0) {
             System.out.print("Do you want to Display the table of Values? (Y/N): ");
             String ans1 = scan2.next();
             ans1 = Character.toString(ans1.charAt(0));
             if (ans1.equalsIgnoreCase("Y")) {
                 displayTableOfConvertedInfixToPostfix(input);
                 while (i == 0) {
                     System.out.println("\n-=========================================================-");
                     System.out.print("Do you wish to Continue? (Y/N): ");
                     String ans2 = scan2.next();
                     ans2 = Character.toString(ans2.charAt(0));
                     if (ans2.equalsIgnoreCase("Y")) {
                         displayMenu();
                     } else if (ans2.equalsIgnoreCase("N")) {
                         System.err.println("Closing....");
                         System.exit(0);
                     } else { System.out.println("Invalid input!"); }
                 }
             } else if (ans1.equalsIgnoreCase("N")) {
                 displayMenu();
                 System.out.println("Do you wish to Continue?  ");
             } else { System.out.println("Invalid Input!"); }
         }
         System.out.println("\n-=========================================================-");
         while (i == 0){
             System.out.print("Do you wish to Continue? (Y/N): ");
             String ans2 = scan2.next();
             ans2 = Character.toString(ans2.charAt(0));
             if (ans2.equalsIgnoreCase("Y")) {
                 displayMenu();
             } else if (ans2.equalsIgnoreCase("N")) {
                 System.err.println("Closing....");
                 System.exit(0);
             }else {
                 System.out.println("Invalid input!");
             }
          }
     }

     public void postfixExpressionTableOfValues() throws Exception {
         String input;
         int i = 0;
         System.out.print("-= Input Postfix Expression: ");
         input = scan.nextLine();
         System.out.println("-=========================================================-");
         displayTableOfValuesPostfix(input);
         System.out.println("\n-=========================================================-");
         while (i == 0) {
             System.out.print("Do you wish to Continue? (Y/N): ");
             String ans = scan2.next();
             ans = Character.toString(ans.charAt(0));
             if (ans.equalsIgnoreCase("Y")) {
                 displayMenu();
             } else if (ans.equalsIgnoreCase("N")) {
                 System.err.println("Closing....");
                 System.exit(0);
             } else {
                 System.out.println("Invalid Input!");
             }
         }
     }
     public void displayMenu() throws Exception {
         while (true){
             System.out.println("");
             System.out.println("-=========================================================-");
             System.out.println("-= Activity 1: Conversion of infix to postfix expression =-");
             System.out.println("-= 1. Input Infix + Convert to Postfix + Display Table of Values");
             System.out.println("-= 2. Input Postfix + Display Table of Values");
             System.out.println("-= 3. Quit program");
             System.out.println("-=========================================================-");
             System.out.print("-= User Input: ");

             switch(checkInput(3)){
                 case 1:
                     infixExpressionConverterTableOfValues();
                     break;
                 case 2:
                     postfixExpressionTableOfValues();
                     break;
                 case 3:
                     System.out.println("-= Program Exiting...");
                     System.exit(0);
                     break;
                 default:
                     System.err.println("-= Invalid Input");
                     break;
             }
         }
     }

     public static int checkInput(int maxChoice) {
         boolean isValid = false;
         int choice = 0;
         while (!isValid) {
             try {
                 choice = Integer.parseInt(scan.nextLine());
                 if (choice > 0 && choice <= maxChoice) {
                     isValid = true;
                 } else if (maxChoice == 1) {
                     System.err.println("Error: Total Choice Given is only (1)");
                 } else System.err.println("Please Choose a number from 1 to " + maxChoice);
             } catch (NumberFormatException nfe) {
                 System.err.println("Error: Invalid Input / Input is not a number!");
                 isValid = false;
             }
         }
         return choice;
     }

     public int getICP(String x){
         return switch (x) {
             case "^" -> 6;
             case "*", "/" -> 3;
             case "+", "-" -> 1;
             default -> 0;
         };
     }

     public int getICP(char x){
         return switch (x){
             case '^' -> 6;
             case '*', '/' -> 3;
             case '+', '-' -> 1;
             default -> 0;
         };
     }

     public boolean precedence(String optr1, String optr2){
         return getICP(optr1) > getICP(optr2);
     }
     public boolean precedence(char optr1, char optr2){
         return getICP(optr1) > getICP(optr2);
     }

     //converts infix to postfix
     public String convertInfixToPostfix(String infix){
         String[] infixInput = expressionSplit(infix);
         StringBuilder postfix = new StringBuilder();
         LinkedStack<String> stack = new LinkedStack<>();

         for (String expression: infixInput) {
             if(expression.equals("(")){
                 stack.push(expression);
             } else if (expression.equals(")")) {
                 while(!stack.top().equals("(")){
                     postfix.append(" ").append(stack.pop());
                 }
                 String garbage = stack.pop();
             } else if(getICP(expression) == 0){
                 postfix.append(" ").append(expression);
             } else {
                 if(stack.isEmpty()){
                     stack.push(expression);
                 } else if(precedence(expression, stack.top())){
                     stack.push(expression);
                 } else if(!precedence(expression, stack.top())){
                     postfix.append(" ").append(stack.pop());
                     if(stack.isEmpty()){
                        stack.push(expression);
                        continue;
                     }
                     if(!precedence(expression, stack.top())) {
                         while (!precedence(expression, stack.top())) {
                             postfix.append(" ").append(stack.pop());
                             if(stack.isEmpty()){
                                 stack.push(expression);
                             }
                         }
                     }
                 }
             }
         }
         while(!stack.isEmpty()){
             postfix.append(" ").append(stack.pop());
         }
         return postfix.toString();
     }


     public static String[] expressionSplit(String str) {
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


     // Displays table of values of postfix with single digit numbers
     public void displayTableOfValuesPostfix(String postfix){
         LinkedStack<String> operandStack = new LinkedStack<>();
         LinkedStack<String> tempCurrent = new LinkedStack<>();
         int value, operand1, operand2;

         //check if input is correct
         if(!checkIfValidPostfix(postfix)){
             System.out.print("Error: Cannot create a table. Invalid Input.");
             return;
         }

         //creates & displays the table
         System.out.printf("%-10s %6s %15s %11s %17s", "Symbol", "operand1" , "operand2" , "value", "operandStack");
         for (char expression: postfix.toCharArray()) {
             switch (expression) {
                 case '^' -> {
                     operand2 = Integer.parseInt(operandStack.pop());
                     operand1 = Integer.parseInt(operandStack.pop());
                     value = (int) Math.pow(operand1, operand2);
                     operandStack.push("" + value);
                     System.out.printf("\n%3s %11d %15d %13d %13s", expression, operand1, operand2, value, tempCurrent.toCSV());
                 }

                 case '*','/' -> {
                     operand2 = Integer.parseInt(operandStack.pop());
                     operand1 = Integer.parseInt(operandStack.pop());
                     if(expression == '*') {
                         value = operand1 * operand2;
                         operandStack.push("" + value);
                     } else {
                         value = operand1 / operand2;
                         operandStack.push("" + value);
                     }
                     System.out.printf("\n%3s %11d %15d %13d %13s", expression, operand1, operand2, value, tempCurrent.toCSV());
                 }

                 case  '+', '-' -> {
                     operand2 = Integer.parseInt(operandStack.pop());
                     operand1 = Integer.parseInt(operandStack.pop());
                     if(expression == '+') {
                         value = operand1 + operand2;
                         operandStack.push("" + value);
                     } else {
                         value = operand1 - operand2;
                         operandStack.push("" + value);
                     }
                     System.out.printf("\n%3s %11d %15d %13d %13s", expression, operand1, operand2, value, tempCurrent.toCSV());
                 }

                 default -> {
                     operandStack.push(Character.toString(expression));
                     System.out.printf("\n%3s %55s", expression, operandStack.toCSV());
                 }
             }
             tempCurrent = operandStack;
         }
     }

     public void displayTableOfConvertedInfixToPostfix(String infix) throws Exception{ //UNDER DEVELOPMENT
         StringBuilder postfixExpression = new StringBuilder();
         LinkedStack<Character> operatorStack = new LinkedStack<>();
         char symbol, topToken;

         if(!checkIfValidInfix(infix)){
             System.err.println("Invalid Expression! \nPlease Try Again.\n");
             displayMenu();
         }

         System.out.printf("\n%-10s %15s %18s", "Symbol", "postfixExpression", "operatorStack");
         for(int i = 0; i < infix.length(); i++){
             symbol = infix.toCharArray()[i];

             if (getICP(symbol) > 0){ //if operator
                 if(operatorStack.isEmpty()) {
                     operatorStack.push(symbol);
                 } else if(precedence(symbol, operatorStack.top())) {
                     operatorStack.push(symbol);
                 } else if(!precedence(symbol, operatorStack.top())){
                     postfixExpression.append(operatorStack.pop());
                     if(operatorStack.isEmpty()){
                         operatorStack.push(symbol);
                         continue;
                     }
                     if(!precedence(symbol, operatorStack.top())) {
                         while (!precedence(symbol, operatorStack.top())) {
                             postfixExpression.append(operatorStack.pop());
                             if(operatorStack.isEmpty()){
                                 operatorStack.push(symbol);
                                 break;
                             }
                         }
                     }
                 }

             } else if(symbol == '('){
                 operatorStack.push(symbol);
             } else if(Character.isLetterOrDigit(symbol)){
                 postfixExpression.append(symbol);
             } else{
                 if (symbol == ')') {
                     while (operatorStack.top() != '(' && !operatorStack.sameTopAndNext()) {
                         postfixExpression.append(operatorStack.pop());
                     }
                     topToken = operatorStack.pop();
                 }
             }

             //checks if the process is evaluating the last character in the inputted infix and
             // the stack of operators is not empty
             if(!operatorStack.isEmpty() && i == infix.length()-1){
                 while(!operatorStack.isEmpty()){
                     postfixExpression.append(operatorStack.pop());
                 }
             }
             System.out.printf("\n%-10s %10s %18s", symbol, postfixExpression, operatorStack.toString());
         }

         for(char character: postfixExpression.toString().toCharArray()){
             postfixExpression.insert(postfixExpression.toString().indexOf(character), " ");
         }
         System.out.println("\n-=========================================================-");
         System.out.println("Converted Postfix Expression: " + convertInfixToPostfix(infix));
     }

     public boolean checkIfValidInfix(String expression){
         boolean validInfix = true;
         int counter = 0;
         char[] infix = expression.toCharArray();

         for (char digit: infix) {
             if(digit == '(' || digit == ')'){
                 counter += 0;
             } else if(getICP(digit) > 0){
                 counter--;
             } else {
                 counter++;
             }

             if(counter > 1 || counter < 0){
                 validInfix = false;
             }
         }
         return validInfix;
     }

     public boolean checkIfValidPostfix(String expression){
         char[] postfix = expression.toCharArray();
         int counter = 0;
         boolean valid = true;

         for (char digit: postfix) {
             if(getICP(digit) > 0) {
                 counter -= 2;
             } else {
                 counter++;
             }

             if(counter < 0) valid = false; break;
         }

         if(getICP(postfix[expression.length()-1]) == 0) valid = false;
         return valid;
     }
 }
