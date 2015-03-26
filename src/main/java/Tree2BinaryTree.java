
import java.util.*;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Tree2BinaryTree {

  private Graph nary;
  private BinaryTree.Node<Integer> rootNode;

  public Tree2BinaryTree() {
    super();
  }

  public Tree2BinaryTree loadFile(Scanner scan) throws IOException {
    Graph g = ListGraph.createGraph(scan, true, "List");
    nary = g;
    rootNode = new BinaryTree.Node<Integer>(0);
    return this;
  }


  public BinaryTree<Integer> output() {
    Graph a = convertToBinaryGraph(nary, rootNode.data);
    return convertToBinaryTree(a, rootNode.data);
  }


  private int findLeftmostChild(Graph g, int elem) {
    Iterator<Edge> itr = g.edgeIterator(elem);
    return itr.hasNext() ? itr.next().getDest() : -1;
  }

  private int getNearestSibling(Graph g, int[] nodes, int elem) {
    Iterator<Edge> itr;

    int parent = findParent(g, nodes, elem);
    if (parent == -1) {
      // invalid parent
      return -1;
    }

    itr = g.edgeIterator(parent);
    while (itr.hasNext()) {
      Edge edge = itr.next();
      if (edge.getDest() == elem) {
        // return the rightmost sibling, which is next, or null if none
        return itr.hasNext() ? itr.next().getDest() : -1;
      }
    }
    return -1; // no rightmost sibling
  }

  private int findParent(Graph g, int[] nodes, int elem) {
    if (nodes == null)
      return -1;
    for (int node : nodes) {
      if (g.isEdge(node, elem))
        return node;
    }
    return -1;
  }

  private Graph convertToBinaryGraph(Graph g, int elem) {
    Graph a = new ListGraph(g.getNumV(), true);
    int[] nodes;

    nodes = getNodes(g, elem);

    for (int node : nodes) {
      int leftmostChild = findLeftmostChild(g, node);
      if (leftmostChild != -1)
        a.insert(new Edge(node, leftmostChild));
    }

    for (int node : nodes) {
      int sibling = getNearestSibling(g, nodes, node);
      if (sibling != -1) {
        a.insert(new Edge(node, sibling));
      }
    }

    return a;
  }


  private BinaryTree<Integer> convertToBinaryTree(Graph g, int rootVal) {
    Graph binaryGraph;
    BinaryTree.Node root;
    BinaryTree bt;

    // setup binary graph
    binaryGraph = convertToBinaryGraph(g, rootVal);

    // convert the graph into a tree
    root = new BinaryTree.Node<Integer>(rootVal);
    constructTree(g, root);
    bt = new BinaryTree(root);
    return bt;
  }


  private void constructTree(Graph g,
                             BinaryTree.Node<Integer> currNode) {
    Iterator<Edge> itr = g.edgeIterator(currNode.data);

    if (itr.hasNext()) {
      Edge edge = itr.next();
      BinaryTree.Node<Integer> newNode = new BinaryTree.Node<Integer>(edge.getDest());
      currNode.setLeft(newNode);
      constructTree(g, newNode);
    }

    if (itr.hasNext()) {
      Edge edge = itr.next();
      BinaryTree.Node<Integer> newNode = new BinaryTree.Node<Integer>(edge.getDest());
      currNode.setRight(newNode);
      constructTree(g, newNode);
    }
  }


  public int[] getNodes(Graph g, int root) {
    return bfs(g, root);
  }

  private int[] bfs(Graph g, int root) {
    Queue<Integer> q = new LinkedBlockingQueue<Integer>();
    int[] target = new int[g.getNumV()];
    int i = 0;
    q.add(root);
    while (!q.isEmpty()) {
      int elem = q.remove();
      Iterator<Edge> itr = g.edgeIterator(elem);
      while (itr.hasNext()) {
        Edge curr = itr.next();
        target[i] = curr.getDest();
        i++;
        q.offer(curr.getDest());
      }
    }
    return target;
  }


  public static void main(String[] args) throws IOException {
    Scanner scan = new Scanner(System.in);
// Write your code below to convert the tree to a binary tree:

    BinaryTree<Integer> bt = new Tree2BinaryTree()
            .loadFile(scan)
            .output();

    System.out.println("Binary tree built:\n" + bt);
  }
}
