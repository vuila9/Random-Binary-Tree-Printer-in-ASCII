import java.io.PrintWriter;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An implementation of binary trees
 * @author Nguyen
 *
 * @param <Node>
 */
public class BinaryTree<Node extends BinaryTree.BTNode<Node>> {

    public static class BTNode<Node extends BTNode<Node>> {
        public Node left;
        public Node right;
        public Node parent;
    }

    /**
     * An extension of BTNode that you can actually instantiate.
     */
    protected static class EndNode extends BTNode<EndNode> {
        public EndNode() {
            this.parent = this.left = this.right = null;
        }
    }

    /**
     * Used to make a mini-factory
     */
    protected Node sampleNode;

    /**
     * The root of this tree
     */
    protected Node r;

    /**
     * This tree's "null" node
     */
    protected Node nil;

    /**
     * Create a new instance of this class
     * @param sampleNode - a sample of a node that can be used
     * to create a new node in newNode()
     */
    public BinaryTree(Node sampleNode) {
        this.sampleNode = sampleNode;
    }


    /**
     * Compute the size (number of nodes) of this tree
     * @warning uses recursion could cause a stack overflow
     * @return the number of nodes in this tree
     */
    public int size() {
        return size(r);
    }

    /**
     * @return the size of the subtree rooted at u
     */
    protected int size(Node u) {
        if (u == nil) return 0;
        return 1 + size(u.left) + size(u.right);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(sb, r);
        return sb.toString();
    }

    protected void toStringHelper(StringBuilder sb, Node u) {
        if (u == null) {
            return;
        }
        sb.append('(');
        toStringHelper(sb, u.left);
        toStringHelper(sb, u.right);
        sb.append(')');
    }


    /**
     * @ return an n-node BinaryTree that has the shape of a random
     * binary search tree.
     */
    public static BinaryTree<EndNode> randomBST(int n) {
        Random rand = new Random();
        EndNode sample = new EndNode();
        BinaryTree<EndNode> t = new BinaryTree<>(sample);
        t.r = randomBSTHelper(n, rand);
        return t;
    }

    protected static EndNode randomBSTHelper(int n, Random rand) {
        if (n == 0) {
            return null;
        }
        EndNode r = new EndNode();
        int ml = rand.nextInt(n);
        int mr = n - ml - 1;
        if (ml > 0) {
            r.left = randomBSTHelper(ml, rand);
            r.left.parent = r;
        }
        if (mr > 0) {
            r.right = randomBSTHelper(mr, rand);
            r.right.parent = r;
        }
        return r;
    }

    /**
     * @return null
     */
    public boolean isEmpty() {
        return r == nil;
    }


    /**
     * An algorithm to print a random binary tree with given amount of nodes in ASCII form
     * @author John Nguyen
     *
     * @param w - PrintWriter
     */
    public void prettyPrint(PrintWriter w) {
        // TODO: Your code goes here
        Node u = r, prev = nil, next;
        int horizon = 0, vertical = 0;
        int counter1 = 0, counter2 = 0;
        ArrayList<StringBuilder> alsb = new ArrayList<>();
        alsb.add(new StringBuilder());
        if (isEmpty()){
            return;
        }
        while (u != nil) {                      // 1
            if (prev == u.parent) {             // 2

                if (u.left != nil) {            // 3
                    if (alsb.get(counter1).length() < counter2){
                        int size = counter2 - alsb.get(counter1).length();
                        for (int i = 0; i < size; i++)
                            alsb.get(counter1).append(" ");
                    }
                    alsb.get(counter1).insert(counter2,"*");
                    counter1++;
                    if (counter1 > horizon) {
                        alsb.add(new StringBuilder());
                        ++horizon;
                    }

                    if (alsb.get(counter1).length() < counter2){
                        int size = counter2 - alsb.get(counter1).length();
                        for (int i = 0; i < size; i++)
                            alsb.get(counter1).append(" ");
                    }
                    alsb.get(counter1).insert(counter2,"|");
                    counter1++;
                    if (counter1 > horizon) {
                        alsb.add(new StringBuilder());
                        ++horizon;
                    }
                    next = u.left;              // 4
                }
                else if (u.right != nil) {      // 5

                    if (alsb.get(counter1).length() < counter2){
                        int size = counter2 - alsb.get(counter1).length();
                        for (int i = 0; i < size; i++)
                            alsb.get(counter1).append(" ");
                    }
                    alsb.get(counter1).insert(counter2,"*");

                    alsb.get(counter1).append("-");

                    counter2 += 2;

                    if (counter2 > vertical){
                        vertical = counter2;
                    }

                    next = u.right;             // 6
                }
                else {                          // 7
                    if (alsb.get(counter1).length() < counter2){
                        int size = counter2 - alsb.get(counter1).length();
                        for (int i = 0; i < size; i++)
                            alsb.get(counter1).append(" ");
                    }
                    alsb.get(counter1).insert(counter2,"*");

                    if (u.equals(u.parent.left)){
                        counter1 -= 2;
                    }
                    else if (u.equals(u.parent.right)){
                        while (alsb.get(counter1).charAt(counter2 - 1) != '*'){
                            --counter2;
                        }
                        counter2--;
                    }
                    next = u.parent;            // 8
                }
            }
            else if (prev == u.left) {          // 9
                if (u.right != nil) {           // 10
                    while (counter2 <= vertical){
                        alsb.get(counter1).append("-");
                        ++counter2;
                    }
                    counter2++;
                    if (counter2 > vertical)
                        vertical = counter2;
                    next = u.right;             // 11
                }
                else {                          // 12
                    if (u.parent != nil){

                        if (u.equals(u.parent.left)) {
                            counter1 -= 2;
                        } else if (u.equals(u.parent.right)) {
                            while (alsb.get(counter1).charAt(counter2 - 1) != '*') {
                                --counter2;
                            }
                            --counter2;
                        }
                    }
                    next = u.parent;            // 13
                }
            }
            else {                              // 14
                if (u.parent != nil){
                    if (u.equals(u.parent.left)){
                        counter1 -= 2;
                    }
                    else if (u.equals(u.parent.right)){
                        while (alsb.get(counter1).charAt(counter2 - 1) != '*'){
                            --counter2;
                        }
                        counter2--;
                    }
                }
                next = u.parent;                // 15
            }
            prev = u;                           // 16
            u = next;                           // 17
        }

        for (StringBuilder stringBuilder : alsb) {
            w.println(stringBuilder);
        }
    }

    public static void main(String[] args) {
        int numberOfNodes;

        Scanner sc= new Scanner(System.in);
        System.out.print("Enter a non-negative integer: ");
        numberOfNodes= sc.nextInt();
        
        BinaryTree<EndNode> bt = randomBST(numberOfNodes);

        System.out.println("Number of nodes: " + numberOfNodes);
        PrintWriter w = new PrintWriter(System.out);
        bt.prettyPrint(w);
        w.flush();
    }
}
