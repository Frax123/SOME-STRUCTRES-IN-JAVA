//Zadanie wykonane przez Krzysztofa Szczurka i Rafala Radwanskiego
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

class BSTTree<Type extends Comparable<Type>>{
    BSTNode<Type> root;
    int treeLength;

    BSTTree(){
        this.root = null;
        treeLength = 0;
    }

    BSTTree(BSTNode<Type> _root){
        this.root = _root;
        treeLength = 1;
    }

    public void append(Type newValue){
        this.append(new BSTNode<Type>(newValue));
    }

    public void append(BSTNode<Type> nodeWithNewValue){
        BSTNode<Type> referenceIterator = this.root;
        if (this.root == null){
            this.root = nodeWithNewValue;
            treeLength = 1;
            return;
        }

        int variableToRememberComparisonResult;
        int lengthOfPath = 1;
        while (true){
            lengthOfPath++;
            variableToRememberComparisonResult = referenceIterator.value.compareTo(nodeWithNewValue.value);
            if (variableToRememberComparisonResult > 0){ //nowy Node mniejszy
                if (referenceIterator.leftChild != null) {
                    referenceIterator = referenceIterator.leftChild;
                } else {
                    referenceIterator.leftChild = nodeWithNewValue;
                    if (this.treeLength < lengthOfPath){
                        this.treeLength = lengthOfPath;
                    }
                    break;
                }
            } else if (variableToRememberComparisonResult == 0) {
                break;
            } else {
                if (referenceIterator.rightChild != null) {
                    referenceIterator = referenceIterator.rightChild;
                } else {
                    referenceIterator.rightChild = nodeWithNewValue;
                    if (this.treeLength < lengthOfPath){
                        this.treeLength = lengthOfPath;
                    }
                    break;
                }
            }
        }
    }

    public void displayLevelOrder(){
        //System.out.println(this.treeLength);
        for (int i = 1; i <= this.treeLength; i++){
            displayExaclyOneLevel(this.root, i);
        }
    }

    public BSTNode<Type> search(Type soughtValue){
        BSTNode<Type> referenceIterator = this.root;
        if (this.root == null){
            return null;
        }

        int variableToRememberComparisonResult;
        while (true){
            variableToRememberComparisonResult = referenceIterator.value.compareTo(soughtValue);
            if (variableToRememberComparisonResult > 0){ //nowy Node mniejszy
                if (referenceIterator.leftChild != null) {
                    referenceIterator = referenceIterator.leftChild;
                } else {
                    return null;
                }
            } else if (variableToRememberComparisonResult == 0) {
                return referenceIterator;
            } else {
                if (referenceIterator.rightChild != null) {
                    referenceIterator = referenceIterator.rightChild;
                } else {
                    return null;
                }
            }
        }
    }

    private void displayExaclyOneLevel(BSTNode _root, int level){
        if (_root == null) return ;
        if (level == 1){
            System.out.println(_root.value);
        } else if (level > 1) {
            displayExaclyOneLevel(_root.leftChild, level-1);
            displayExaclyOneLevel(_root.rightChild, level-1);
        }

    }
}

class BSTNode<Type extends Comparable<Type>>{
    Type value;
    BSTNode<Type> leftChild;
    BSTNode<Type> rightChild;

    BSTNode(Type value){
        this.value = value;
        leftChild = null;
        rightChild = null;
    }

    public Type getValue(){
        return this.value;
    }

    public void setValue(Type newValue){
        this.value = newValue;
    }

    public BSTNode<Type> getLeftChild(){
        return leftChild;
    }

    public BSTNode<Type> getRightChild(){
        return rightChild;
    }
}

class OrderedArray{
    String[] array;
    int numberOfElementsInArray;

    OrderedArray(int lengthOfArray){
        this.array = new String[lengthOfArray];
        this.numberOfElementsInArray = 0;
    }

    public void append(String newValue){
        if (numberOfElementsInArray == array.length){
            return ;
        } else {
            numberOfElementsInArray++;
        }

        if (numberOfElementsInArray == 1){
            array[0] = newValue;
            return ;
        }

        int iterator = 0;
        while (iterator < numberOfElementsInArray-1 && this.array[iterator].compareTo(newValue) < 0){
            iterator++;
        }

        if (this.array[iterator] != null && this.array[iterator].compareTo(newValue) == 0){
            numberOfElementsInArray--;
            return ;
        }

        if (iterator == numberOfElementsInArray-1){
            array[iterator] = newValue;
            return ;
        } else {
            String temp = array[iterator];
            String valueToAdd = newValue;
            while (iterator < numberOfElementsInArray) {
                array[iterator] = valueToAdd;
                iterator++;
                valueToAdd = temp;
                if (iterator < numberOfElementsInArray) {
                    temp = array[iterator];
                }
            }
        }

    }

    public Integer search(String soughtValue){ //-1 oznacza brak
        if (numberOfElementsInArray == 0) return -1;

        int comparisonValue;
        int middleIndex = (numberOfElementsInArray-1)/2;
        int lastIndex = (numberOfElementsInArray-1);
        int firstIndex = 0;
        String lastValue = "";
        boolean whileCondition = true;
        while(whileCondition) {
            comparisonValue = array[middleIndex].compareTo(soughtValue);
            lastValue = array[middleIndex];
            if (comparisonValue > 0){
                lastIndex = middleIndex;
                middleIndex = (firstIndex+middleIndex)/2;
            } else if (comparisonValue == 0){
                return middleIndex;
            } else {
                firstIndex = middleIndex;
                middleIndex = (middleIndex + lastIndex)/2;
               // System.out.println("HERE" + " " + middleIndex + " "+ lastIndex + " " + numberOfElementsInArray);
            }

            if (lastValue == array[middleIndex]){
                if (middleIndex == numberOfElementsInArray-2){
                    middleIndex = numberOfElementsInArray-1;
                } else {
                    whileCondition = false;
                }
            }
        }

        return -1;
    }


    public String getValue(int position){
        return this.array[position];
    }

    public void display(){
        for (int i = 0; i<numberOfElementsInArray; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println("");
    }

}

class HashTable{
    String[] array;
    int numberOfElementsInArray;

    HashTable(){
        this.array = new String[1];
        numberOfElementsInArray = 0;
    }

    public int hashFunction(Integer value){
        return value%array.length;
    }

    public int hashPrimFunction(int lastValue, Integer value){
        int valueOfHash = hashFunction(value);
        if (valueOfHash == 0){
            return lastValue+1;
        }
        return (lastValue + valueOfHash)%array.length;
    }

    public void append(String value){
        appendInDetails(this.array, value);
        numberOfElementsInArray++;
        if (numberOfElementsInArray > array.length/2){
            extend();
        }
    }

    private void appendInDetails(String[] arrayToAppend, String value){ //Funkcja powstala po to, zeby latwiej robic extend
        int valueOfHashFunction = hashFunction(ASD5.fromStringToInt(value));
        while (arrayToAppend[valueOfHashFunction] != null){
            if (arrayToAppend[valueOfHashFunction] == value){
                return;
            }
            valueOfHashFunction = hashPrimFunction(valueOfHashFunction, ASD5.fromStringToInt(value));
        }
        arrayToAppend[valueOfHashFunction] = value;
    }

    public void extend(){
        String[] newArray = new String[this.array.length*2];
        for (int i = 0; i<array.length; i++){
            if (array[i] != null){
                appendInDetails(newArray, array[i]);
            }
        }
        this.array = newArray;
    }

    public int search(String soughtValue){
        int valueOfHashFunction = hashFunction(ASD5.fromStringToInt(soughtValue));
        String firstValue = array[valueOfHashFunction];
        if (firstValue == soughtValue){
            return valueOfHashFunction;
        } else {
            valueOfHashFunction = hashPrimFunction(valueOfHashFunction, ASD5.fromStringToInt(soughtValue));
        }

        while (array[valueOfHashFunction] != null && array[valueOfHashFunction] != firstValue){
            if (array[valueOfHashFunction] == soughtValue){
                return valueOfHashFunction;
            }
            valueOfHashFunction = hashPrimFunction(valueOfHashFunction, ASD5.fromStringToInt(soughtValue));
        }

        return -1;
    }

    public void display(){
        for (int i = 0; i<array.length; i++){
            if (array[i] != null){
                System.out.println(array[i]);
            }
        }
    }
}

class Heap<Type extends Comparable<Type>>{
    Type[] array;
    int last;

    Heap(int size, Class<Type> classOfObjects){
        this.array = getArray(size, classOfObjects);
        this.last = 0;
    }

    private Type[] getArray(int size, Class<Type> exampleOfTypeObject){ // Ta funkcje znalazlem na internecie szukajac rozwiazania
        //tablicy generycznej, ale rozumiem co sie w niej dzieje
        return (Type[])java.lang.reflect.Array.newInstance(exampleOfTypeObject, size);
    }

    public void append(Type newValue){
        if (this.array[0] == null){
            this.array[0] = newValue;
            last = 1;
        } else {
            this.array[this.last] = newValue;
            this.last++;
            UpHeap(this.last-1);
        }
    }

    public void UpHeap(int k){ //k pozycja(index) elementu w tablicy od ktorego zaczyna Upheap
        Type kValue = this.array[k];
        int iterator = (k-1)/2;
        while (k>0 && array[iterator].compareTo(kValue) > 0){
            array[k] = array[iterator];
            k = iterator;
            iterator = (k-1)/2;
        }

        array[k] = kValue;
    }

    public void display(){
        for (int i = 0; i < last; i++){
            System.out.println(array[i]);
        }
    }

    public int searchForValue(Type value){
        for (int i = 0; i < last; i++){
            if (array[i] == value){
                return i;
            }
        }

        return -1;
    }

}

class Node {
    String value;
    Node parent;
    Node left;
    Node right;

    public Node(String v, Node p, Node l, Node r) {
        value = v;
        parent = p;
        left = l;
        right = r;
    }
}

class SplayTree {
    Node root;

    public SplayTree(String rt) { root = new Node(rt, null, null, null); }

    private void right_rotation(Node x) {
        Node y = x.parent;
        y.left = x.right;
        if(x.right != null) x.right.parent = y;
        x.parent = y.parent;
        x.right = y;

        if(y == root) root = x;
        else if (y.parent.left == y) y.parent.left = x;
        else y.parent.right = x;

        y.parent = x;
    }

    private void left_rotation(Node x) {
        Node y = x.parent;
        y.right = x.left;
        if(x.left != null) x.left.parent = y;
        x.parent = y.parent;
        x.left = y;

        if(y == root) root = x;
        else if (y.parent.left == y) y.parent.left = x;
        else y.parent.right = x;

        y.parent = x;
    }

    private Node look_for(Node x) {
        Node temp = root;
        Node tempnext = root;
        Node biggest = null;
        while (tempnext != null) {
            temp = tempnext;
            if(temp.value.compareTo(x.value) == 0) return temp;
            if(temp.value.compareTo(x.value) > 0) tempnext = temp.left;
            else {
                biggest = temp;
                tempnext = temp.right;
            }
        }
        if(biggest == null) return temp;
        return biggest;
    }

    private Node splay(Node xp) {
        Node x = look_for(xp);
        while (x != root) {
            if(x.parent == root) {
                if(x.parent.left == x) right_rotation(x);                       // Left
                else left_rotation(x);                                          // Right
            } else if(x.parent.left == x) {
                if(x.parent.parent.left == x.parent) {
                    right_rotation(x.parent);                                   // Left - Left
                    right_rotation(x);
                }
                else {
                    right_rotation(x);
                    left_rotation(x);                                           // Right - Left
                }

            } else if(x.parent.right == x) {
                if(x.parent.parent.left == x.parent) {
                    left_rotation(x);
                    right_rotation(x);                                          // Left - Right
                }
                else {
                    left_rotation(x.parent);
                    left_rotation(x);                                           // Right - Right
                }
            }
        }
        return x;
    }

    public Node search(String s) {
        Node x = new Node(s, null, null, null);
        String xval = splay(x).value;
        return root;
    }

    public void insert(String s) {
        Node x = new Node(s, null, null, null);
        String xval = splay(x).value;
        if(x.value.compareTo(xval) > 0) {
            root.parent = x;
            x.left = root;
            if(root.right != null) {
                root.right.parent = x;
                x.right = root.right;
                root.right = null;
            }
            root = x;
        } else if(x.value.compareTo(xval) < 0) {
            root.parent = x;
            x.right = root;
            if(root.left != null) {
                root.left.parent = x;
                x.left = root.left;
                root.left = null;
            }
            root = x;
        }
    }

    public void delete(Node x) {
        splay(x);
        Node t2 = x.right;
        root = x.left;
        root.parent = null;
        t2.parent = null;
        splay(x);
        root.right = t2;
        t2.parent = root;
    }
}


public class ASD5 {
    static private Path filepath = Paths.get("tekst.txt");
    static public ArrayList<String> storeData() {
        try {
            Scanner inp = new Scanner(filepath);
            ArrayList<String> data = new ArrayList<>();
            while(inp.hasNext()) data.add(inp.next());
            return data;
        } catch (Exception e) {
            e.getCause();
            System.exit(99);
        }
        return null;
    }

    static ArrayList<String> data = storeData();
    static BSTTree<String> BST = new BSTTree<>();
    static Heap<String> heap = new Heap<>(data.size(), String.class);
    static OrderedArray array = new OrderedArray(data.size());
    static HashTable hashArray = new HashTable();
    static SplayTree splayTree = new SplayTree(data.get(0));


    public static Integer fromStringToInt(String s){ // Funkcja pomocniczna, nadaje stringowi wartosc liczbowa
        Integer result = 0;
        for (int i = 0; i < s.length(); i++){
            result += s.charAt(i);
        }

        return result;
    }

    static public void compareTimesOfEveryStructure(ArrayList<String> data){
        long timeOfBST = countTimeForBST(data);
        long timeOfHeap = countTimeForHeap(data);
        long timeOfOrderedArray = countTimeForOrderedArray(data);
        long timeOfHashArray = countTimeForHashTable(data);
        long timeOfSplayTree = countTimeForSplayTree(data);

        System.out.println("CZASY DZIALANIA PROGRAMOW:");
        System.out.println("BST: " + timeOfBST);
        System.out.println("Drzewo kopcowe: " + timeOfHeap);
        System.out.println("Tablica uporzadkowana: " + timeOfOrderedArray);
        System.out.println("Tablica hashujaca mieszana: " + timeOfHashArray);
        System.out.println("Splay: " + timeOfSplayTree);

    }

    static public long countTimeForBST(ArrayList<String> data){
        long start = System.nanoTime();
        int size = data.size();

        for (int i = 0; i < size; i++){
            BST.append(data.get(i));
        }

        long end = System.nanoTime();

        return end - start;
    }

    static public long countTimeForHeap(ArrayList<String> data){
        long start = System.nanoTime();
        int size = data.size();
        for (int i = 0; i < size; i++){
            heap.append(data.get(i));
        }
        long end = System.nanoTime();
        return end - start;
    }

    static public long countTimeForOrderedArray(ArrayList<String> data){
        long start = System.nanoTime();
        int size = data.size();
        for (int i = 0; i < size; i++){
            array.append(data.get(i));
        }
        long end = System.nanoTime();
        return end - start;
    }

    static public long countTimeForHashTable(ArrayList<String> data){
        long start = System.nanoTime();
        int size = data.size();
        for (int i = 0; i < size; i++){
            hashArray.append(data.get(i));
        }
        long end = System.nanoTime();
        return end - start;
    }

    static public long countTimeForSplayTree(ArrayList<String> data){
        long start = System.nanoTime();
        int size = data.size();
        for (int i = 1; i < size; i++){
            splayTree.insert(data.get(i));
        }
        long end = System.nanoTime();
        return end - start;
    }

    static public void compareSearchTime(String s) {
        long timeOfBST = searchTimeForBST(s);
        long timeOfHeap = searchTimeForHeap(s);
        long timeOfOrderedArray = searchTimeForOrderedArray(s);
        long timeOfHashArray = searchTimeForHashTable(s);
        long timeOfSplayTree = searchTimeForSplayTree(s);
        System.out.println("CZASY WYSZUKIWANIA PROGRAMOW:");
        System.out.println("BST: " + timeOfBST);
        System.out.println("Drzewo kopcowe: " + timeOfHeap);
        System.out.println("Tablica uporzadkowana: " + timeOfOrderedArray);
        System.out.println("Tablica hashujaca mieszana: " + timeOfHashArray);
        System.out.println("Splay: " + timeOfSplayTree);
    }

    static public long searchTimeForBST(String s) {
        long start = System.nanoTime();
        BST.search(s);
        long end = System.nanoTime();
        return end - start;
    }
    static public long searchTimeForHeap(String s) {
        long start = System.nanoTime();
        heap.searchForValue(s);
        long end = System.nanoTime();
        return end - start;
    }
    static public long searchTimeForOrderedArray(String s) {
        long start = System.nanoTime();
        array.search(s);
        long end = System.nanoTime();
        return end - start;
    }
    static public long searchTimeForHashTable(String s) {
        long start = System.nanoTime();
        hashArray.search(s);
        long end = System.nanoTime();
        return end - start;
    }
    static public long searchTimeForSplayTree(String s) {
        long start = System.nanoTime();
        splayTree.search(s);
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) throws IOException {
        compareTimesOfEveryStructure(data);
        compareSearchTime("Hamlet");
    }
}
