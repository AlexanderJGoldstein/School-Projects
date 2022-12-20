import java.util.*;

class Node {
  Node left;
  Node right;
  int data;

  Node() { this(0);  }

  Node(int data) {
    this.data = data;
    left = null;
    right = null;
  }
}

public class Main {

  public static void preOrder(Node root) {

    if (root == null)
      return;

    System.out.print(root.data + " ");
    preOrder(root.left);
    preOrder(root.right);

  }

  // Refer to Node definition above
  public static Node insert(Node root, int value) {
    if(root == null){
			root = new Node(value);
			return root;
		}
		if(root.data <= value)
			root.right = insert(root.right, value);
		if(root.data > value)
			root.left = insert(root.left, value);
		return root;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int t = scan.nextInt();
    Node root = null;
    while (t-- > 0) {
      int data = scan.nextInt();
      root = insert(root, data);
    }
    scan.close();
    preOrder(root);
  }
}