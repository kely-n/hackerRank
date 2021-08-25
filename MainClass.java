/**
 * Overview
 * This interview assignment asks you to implement a parser that will parse a contrived syntax
 * language (a made-up syntax) and return an object representation of the input string (a tree of
 * objects). The following illustration can help to explain:
 *
 * 
 *
 * Parser Rules
 * These are the rules of the parser. Be sure to refer to this when implementing the parser.
 * ● The shapes syntax language consists of shapes: squares and circles.
 * ● Each shape has a start symbol, a label, zero or more inner shapes, and the end symbol.
 * Examples: [1] or (HELLO) or (BOY[12]) or (APPLE(MAN)[65])
 * ● The square bracket symbol denotes the square shape - starts with the symbol “[“ and
 * ends with the symbol “]”. Example: [123]
 * ● The parenthesis symbol denotes the circle shape - starts with the symbol “(“ and ends
 * with the symbol “)”. Example: (CIRCLE)
 * ● A shape must have a label.
 * ● Squares must be labeled with numbers only.
 * ● Squares can only contain other squares.
 * ● Circles are labeled with uppercase letters only.
 * ● Circles can contain squares or other circles.
 * ● If the input is invalid, an exception is thrown.
 *
 *
 * Requirements
 * 1. Design data structures
 * a. Think object oriented design
 * b. Think of all the classes you need and their properties and their relationships.
 * c. Think of the different kinds of shapes and keep the rules in mind.
 * 2. Design parser
 * a. Think of what data structures to use.
 * b. Think of the parser logic.
 * c. Test your design to make sure it will support all the use cases.
 * 3. Implement parser
 * a. Implement the parser according to your design and the rules.
 * 4. Implement unit tests
 * a. Start with example tests.
 * b. Think of other tests and implement them.
 *
 * Example Tests
 * The following are some tests to help you test the validity of your parser. You are expected to
 * implement enough tests to ensure that your parser works. This is not enough tests to validate
 * your parser, you are expected to start with this and add more.
 * ● Valid inputs include:
 * ○ A square: [13]
 * ○ A circle: (DOG)
 * ○ A circle with inner square (DOG[15])
 * ● Invalid inputs include:
 * ○ Invalid input: $@#
 * ○ Malformed input: [13)
 * ○ Invalid inner shape: [72(HELLO)]
 * ○ Invalid label (lower case): [allow]
 */

import java.util.*;

abstract class Shape {

    protected String value;

    public abstract String getValue();
    public abstract void setValue(String value);

}

class Square extends Shape{

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Square{" +
                "value='" + value + '\'' +
                '}';
    }


    public Square(String value) {
        this.value = value;
    }
}

class Circle extends Shape{

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "value='" + value + '\'' +
                '}';
    }

    public Circle(String value, String children) {
        this.value= value;
    }

    public Circle(String value) {
        this.value= value;
    }

}

class Container extends Shape{

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Container{" +
                "value='" + value + '\'' +
                '}';
    }

    public Container(String value) {
        this.value = value;
    }
}

class Node {
    private Shape shape;
    private Node left;
    private Node right;

    public Node(Shape shape) {
        this.shape = shape;
    }

    public Node() {
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        if ((left.getShape() instanceof Circle) && (shape instanceof Square)){
            throw new InputMismatchException("() can not be child of []");
        }else {
            this.left = left;
        }
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        if ((right.getShape() instanceof Circle) && (shape instanceof Square)){
            throw new InputMismatchException("() can not be child of []");
        }else {
            this.right = right;
        }
    }

    @Override
    public String toString() {
        return shape.toString();
    }

}

class Tree{
    private Node root;

    public void initializeTree(String input){

        if (root == null){
            root = new Node(new Container((input)));
            root = setChildren(root, input);
        }

    }

    public Node getNode (String input_equation){
        Node parent = new Node();
        //initialize a list that holds current child
        List<String> current_child = new ArrayList<>();
        //remove any spaces from input_equation
        input_equation = input_equation.replace(" ", "");
        input_equation = input_equation.substring(1, input_equation.length() - 1);
        //convert the input_equation string to an array of characters
        char[] characters = input_equation.toCharArray();
        //iterate the characters array, skipping spaces
        for (int i = 0 ; i<characters.length; i++){
            //pick a character to perform operation on
            char parsed_char = characters[i];
            //~~~~~~~if char is an opening bracket or opening square bracket, push it in the stack and iterate till you get a closing one for it
            if(parsed_char=='(' || parsed_char=='['){
                break;
            }
            else{
                //check if operand is one digit or not
                current_child.add(String.valueOf(parsed_char));
            }
        }

        String word = String.join("",current_child);
        Shape shape;
        if(isNumber(word)){
            shape = new Square(word);
        }
        else if(isUpper(word)){
            shape = new Circle(word);
        }
        else{
           throw new InputMismatchException("Input mismatch for : " + word);
        }
        parent.setShape(shape);

        if( input_equation.split(String.join("",current_child)).length > 1){
            parent = setChildren(parent, input_equation.split(String.join("",current_child))[1]);
        }

        return parent;
    }

    private boolean isUpper(String str){
        char[] characters = str.toCharArray();
        for (char character : characters){
            if(!Character.isUpperCase(character) || !Character.isAlphabetic(character))
                return false;
        }
        return true;
    }

    private boolean isNumber(String str){
        char[] characters = str.toCharArray();
        for (char character : characters){
            if(!Character.isDigit(character))
                return false;
        }
        return true;
    }

    public Node setChildren (Node node, String input_equation){
        //use stack as a temporary memory, think of it like a RAM to our method
        Stack<Character> temp_char_memory = new Stack<>();

        //initialize a list that holds current child
        List<String> current_child = new ArrayList<>();

        //remove any spaces from input_equation
        input_equation = input_equation.replace(" ", "");

        //convert the input_equation string to an array of characters
        char[] characters = input_equation.toCharArray();
        //iterate the characters array, skipping spaces

        for (int i = 0 ; i<characters.length; i++){
            //pick a character to perform operation on
            char parsed_char = characters[i];
            //~~~~~~~if char is an opening bracket or opening square bracket, push it in the stack and iterate till you get a closing one for it
            if(parsed_char=='(' || parsed_char=='['){
                if (parsed_char=='(' && (!isUpper(characters[i+1]+""))  ){
                    throw new InputMismatchException("Input mismatch");
                }else if(parsed_char=='[' && (!isNumber(characters[i+1]+""))){
                    throw new InputMismatchException("Input mismatch");
                }
                temp_char_memory.push(parsed_char);
                current_child.add(String.valueOf(parsed_char));
//                current_child.add("-");
            }
            //if you get to the closing bracket/square bracket, perform operation on elements in stack till you get to its opening
            else if(parsed_char==')' || parsed_char==']'){
                //check if the stack is empty, and performs operation repeatedly till empty or till an opening brace is reached
                if(temp_char_memory.isEmpty()){
                    //throw an input mismatch error
                }
                while(!temp_char_memory.isEmpty()){
                    //if you get to its opening bracket, remove the bracket and break out of while loop
                    if(temp_char_memory.peek()=='(' || temp_char_memory.peek()=='['){
                        current_child.add(String.valueOf(parsed_char));
                        temp_char_memory.pop();
                        //mark end of child
                        if(temp_char_memory.isEmpty()){
                            current_child.add("@");
                        }
                        break;
                    }
                    //pop character convert it to string and add it to out postfix list
                    else{
                        current_child.add(String.valueOf(temp_char_memory.pop()));
                    }
                }
            }
            else{
                current_child.add(String.valueOf(parsed_char));
            }
        }

        String[] childStrings = String.join("",current_child).split("@");
        for (int i = 0; i<childStrings.length; i++){
            String child = childStrings[i];
            if(i==2){
                //throw error
                throw new InputMismatchException("Input mismatch" );
            }else if (i==0){
                node.setLeft(getNode(child));
            }else{
                node.setRight(getNode(child));
            }
        }

        return node;
    }

    private void printRecursively(Node node){
        if(node!=null){
            System.out.println("parent: " +node);
            if(node.getLeft()!=null){
                System.out.println("left-child: "+node.getLeft());
            }
            if(node.getRight()!=null){
                System.out.println("right-child: "+node.getRight());
            }
            System.out.println();

            printRecursively(node.getLeft());
            printRecursively(node.getRight());
        }
    }

    public void printTree(){
            printRecursively(root);
    }
}

public class MainClass {
    public static void main(String ... args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter string value: ");
        System.out.println("you can paste the following: [12](BALL(INK[1[35]](CHARLIE)))");
        String val = sc.nextLine();

        Tree mainClass = new Tree();
        mainClass.initializeTree(val);
        mainClass.printTree();
    }
}
